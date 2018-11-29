package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dao.Role;
import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import com.torodavid.thesis.financialregister.dal.repository.CashFlowRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CashFlowServiceImplIntegrationTest {

    private static final String FIRST = "first";

    @TestConfiguration
    static class CashFlowServiceImplTestContextConfiguration {

        @Bean
        public CashFlowService employeeService() {
            return new CashFlowServiceImpl();
        }
    }

    @Autowired
    private CashFlowService cashFlowService;

    @MockBean
    private UserService userService;

    @MockBean
    private CashFlowRepository cashFlowRepository;

    @Before
    public void setUp() {
        CashFlow cashFlow1 = new CashFlow();
        CashFlow cashFlow2 = new CashFlow();
        cashFlow1.setName(FIRST);
        cashFlow1.setName("second");
        cashFlow1.setName(FIRST);
        cashFlow1.setPriority(Priority.ONE);
        cashFlow1.setPriority(Priority.ONE);
        cashFlow1.setPriority(Priority.THREE);

        Mockito.when(cashFlowRepository.findTop1ByName(cashFlow1.getName()))
                .thenReturn(Optional.of(cashFlow1));

        Mockito.when(cashFlowRepository.getAllCashFlowsByPriority(Priority.ONE))
                .thenReturn(Arrays.asList(cashFlow1, cashFlow2));

        User admin = new User();
        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roles.add(adminRole);
        admin.setRoles(roles);
        given(userService.getCurrentUser()).willReturn(admin);
    }

    @Test
    public void whenValidName_thenCashFlowShouldBeFound() {
        CashFlow cashFlow = cashFlowService.findCashFlowByName(FIRST).get();

        assertThat(cashFlow.getName())
                .isEqualTo(FIRST);
    }

}
