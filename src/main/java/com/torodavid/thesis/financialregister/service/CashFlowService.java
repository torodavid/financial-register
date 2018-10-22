package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dao.Role;
import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import com.torodavid.thesis.financialregister.dal.repository.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CashFlowService {

    @Autowired
    private CashFlowRepository cashFlowRepository;

    @Autowired
    private UserService userService;

    public CashFlow save(CashFlow cashFlow) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        cashFlow.setUser(user);
        cashFlow.setModificationDate(LocalDateTime.now());
        cashFlow.setCreationDate(LocalDateTime.now());
        cashFlow.setId(UUID.randomUUID().toString());
        return cashFlowRepository.save(cashFlow);
    }

    @Transactional
    public void update(CashFlow cashFlow) {
        cashFlowRepository.setCashFlowById(cashFlow.getName(), cashFlow.getDescription(), cashFlow.getAmount(), cashFlow.getCategory(), cashFlow.getFlowDirection(), cashFlow.getPriority(), cashFlow.getId(), LocalDateTime.now());
    }

    @Transactional
    public void generateCashFlowsToCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        List<Category> categories = Arrays.asList(Category.ENTERTAINMENT, Category.BILL, Category.TECH, Category.UNCATEGORIZED);
        Priority[] priorities = Priority.values();

        for (int i = 1; i < 36; i++) {
            String name1 = "Fizetés " + i;
            cashFlowRepository.save(new CashFlow(UUID.randomUUID().toString(), name1, "Fizetés leírása " + i, new Random().nextInt((250000 - 200000) + 1) + 200000, LocalDateTime.now().minusMonths(i), LocalDateTime.now().minusMonths(i), Priority.ONE, Category.SALARY, FlowDirection.IN, user));
            long limit = new Random().nextInt((50 - 30) + 30);
            for (int j = new Integer(1); limit-- > 0; j++) {
                String name = "Minta pénzmozgás" + i + "." + j;
                cashFlowRepository.save(new CashFlow(
                        UUID.randomUUID().toString(),
                        name,
                        "Minta pénzmozgás leírása" + i + "." + j,
                        new Random().nextInt((15000 - 5000) + 1) + 5000,
                        LocalDateTime.now().minusMonths(i).minusDays(j),
                        LocalDateTime.now().minusMonths(i).minusDays(j),
                        priorities[new Random().nextInt(priorities.length)],
                        categories.get(new Random().nextInt(categories.size())),
                        FlowDirection.OUT,
                        user
                ));
            }
        }

    }

    public Page<CashFlow> findPaginated(Pageable pageable, Optional<List<String>> ids) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CashFlow> list;

        List<CashFlow> cashFlows = new ArrayList<>();
        if (ids.isPresent()) {
            findAllCashFlowsByUsername().forEach(cashFlow -> { if(ids.get().contains(cashFlow.getId())) cashFlows.add(cashFlow); } );
        } else {
            findAllCashFlowsByUsername().forEach(cashFlows::add);
        }
        if (cashFlows.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cashFlows.size());
            list = cashFlows.subList(startItem, toIndex);
        }

        Page<CashFlow> cashFlowPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), cashFlows.size());

        return cashFlowPage;
    }

    public Optional<CashFlow> getCashFlowById(String id) {
        return cashFlowRepository.findById(id);
    }

    public Iterable<CashFlow> getAllCashFlows() {
        return cashFlowRepository.findAll();
    }

    public Iterable<CashFlow> findAllByUserAndModificationDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return cashFlowRepository.findAllByUserAndModificationDateBetweenOrderByModificationDate(user, startDate, endDate);
    }

    public Iterable<CashFlow> findAllByUserAndModificationDateBetweenPrioritized(User user, Priority priority, LocalDateTime startDate, LocalDateTime endDate) {
        return cashFlowRepository.findAllByUserAndPriorityAndModificationDateBetweenOrderByModificationDate(user, priority, startDate, endDate);
    }

    public Iterable<CashFlow> findAllCashFlowsByName(String name) {
        User currentUser = userService.getCurrentUser();
        Boolean hasRole = false;
        Iterator<Role> roleIterator = currentUser.getRoles().iterator();
        while (!hasRole && roleIterator.hasNext())
            hasRole = roleIterator.next().getName().equals("ROLE_ADMIN");
        if (hasRole)
            return cashFlowRepository.findAllCashFlowsByName(name);
        return cashFlowRepository.findAllCashFlowsByName(name, currentUser);
    }

    public Optional<CashFlow> findCashFlowByName(String name) {
        User currentUser = userService.getCurrentUser();
        Boolean hasRole = false;
        Iterator<Role> roleIterator = currentUser.getRoles().iterator();
        while (!hasRole && roleIterator.hasNext())
            hasRole = roleIterator.next().getName().equals("ROLE_ADMIN");
        if (hasRole)
            return cashFlowRepository.findTop1ByName(name);
        return cashFlowRepository.findTop1ByNameAndUser(name, currentUser);
    }

    public Iterable<CashFlow> getAllCashFlowsByCategory(Category category) {
        return cashFlowRepository.getAllCashFlowsByCategory(category);
    }

    public Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection) {
        return cashFlowRepository.getAllCashFlowsByFlowDirection(flowDirection);
    }

    public Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority) {
        return cashFlowRepository.getAllCashFlowsByPriority(priority);
    }

    public Iterable<CashFlow> findAllCashFlowsByIds(List<String> ids) {
        return cashFlowRepository.findAllById(ids);
    }

    public Iterable<CashFlow> findAllCashFlowsByUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return this.getAllCashFlows();
        }
        User user = userService.findByUsername(userDetails.getUsername());
        return cashFlowRepository.findAllByUser(user);
    }

    public void deleteCashFlowById(String id) {
        cashFlowRepository.deleteById(id);
    }

}
