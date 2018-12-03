package com.torodavid.thesis.financialregister.repository;

import com.torodavid.thesis.financialregister.dal.model.CashFlow;
import com.torodavid.thesis.financialregister.dal.model.User;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import com.torodavid.thesis.financialregister.dal.dao.CashFlowRepository;
import com.torodavid.thesis.financialregister.dal.dao.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CashFlowRepositoryIntegrationTest {

    @Autowired
    CashFlowRepository cashFlowRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final String FIRST = "first";
    private static CashFlow cashFlow1 = new CashFlow();
    private static CashFlow cashFlow2 = new CashFlow();
    private static CashFlow cashFlow3 = new CashFlow();

    @Before
    public void setUp() {

        User user1 = new User();
        User user2 = new User();
        user1.setUsername("testUser1");
        user2.setUsername("testUser2");
        user1.setId("10");
        user2.setId("20");
        entityManager.persist(user1);
        entityManager.persist(user2);
        cashFlow1.setUser(user1);
        cashFlow2.setUser(user2);
        cashFlow3.setUser(user2);
        cashFlow1.setName(FIRST);
        cashFlow2.setName("second");
        cashFlow3.setName(FIRST);
        cashFlow1.setPriority(Priority.ONE);
        cashFlow2.setPriority(Priority.ONE);
        cashFlow3.setPriority(Priority.THREE);
        cashFlow1.setId("1");
        cashFlow2.setId("2");
        cashFlow3.setId("3");
        entityManager.persist(cashFlow1);
        entityManager.persist(cashFlow2);
        entityManager.persist(cashFlow3);
        entityManager.flush();
    }

    @Test
    public void whenFindByTop1Name_thenReturnCashFlow() {
        CashFlow found = cashFlowRepository.findTop1ByName(cashFlow1.getName()).get();

        assertThat(found.getName())
                .isEqualTo(cashFlow1.getName());
    }

    @Test
    public void whenFindByPriority_thenReturnProperCashFlows() {
        Iterable<CashFlow> allCashFlowsByPriority = cashFlowRepository.getAllCashFlowsByPriority(Priority.ONE);

        assertThat(allCashFlowsByPriority)
                .hasSize(2)
                .contains(cashFlow1, cashFlow2)
                .doesNotContain(cashFlow3);
    }

    @Test
    public void testFindAllCashFlowsByName() {
        Iterable<CashFlow> allCashFlowsByName = cashFlowRepository.findAllCashFlowsByName(FIRST);
        assertThat(allCashFlowsByName)
                .isNotNull()
                .hasSize(2)
                .contains(cashFlow1, cashFlow3);
    }

    // TODO irni meg parat

}
