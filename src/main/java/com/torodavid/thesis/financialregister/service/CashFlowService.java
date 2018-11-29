package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CashFlowService {
    CashFlow save(CashFlow cashFlow);

    @Transactional
    void update(CashFlow cashFlow);

    @Transactional
    void generateCashFlowsToCurrentUser();

    Page<CashFlow> findPaginated(Pageable pageable, Optional<List<String>> ids);

    Optional<CashFlow> getCashFlowById(String id);

    Iterable<CashFlow> getAllCashFlows();

    Iterable<CashFlow> findAllByUserAndModificationDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenCategorized(User user, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenPrioritized(User user, Priority priority, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenPrioritizedAndCategorized(User user, Priority priority, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenDirectionConsidered(User user, FlowDirection flowDirection, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenDirectionConsideredAndCategorized(User user, FlowDirection flowDirection, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenPrioritizedAndDirectionConsidered(User user, Priority priority, FlowDirection flowDirection, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenPrioritizedAndDirectionConsideredAndCategorized(User user, Priority priority, FlowDirection flowDirection, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllCashFlowsByName(String name);

    Optional<CashFlow> findCashFlowByName(String name);

    Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection);

    Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority);

    Iterable<CashFlow> findAllCashFlowsByIds(List<String> ids);

    Iterable<CashFlow> findAllCashFlowsByUsername();

    void deleteCashFlowById(String id);
}
