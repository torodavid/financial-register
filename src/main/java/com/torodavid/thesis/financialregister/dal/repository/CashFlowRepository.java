package com.torodavid.thesis.financialregister.dal.repository;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dao.User;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CashFlowRepository extends CrudRepository<CashFlow, String> {

    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name like %?1% and user_id = ?2")
    Iterable<CashFlow> findAllCashFlowsByName(String name, User user);

    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name like %?1%")
    Iterable<CashFlow> findAllCashFlowsByName(String name);

    Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection);

    Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority);

    Iterable<CashFlow> findAllByUser(User user);

    Optional<CashFlow> findTop1ByName(String name);

    Optional<CashFlow> findTop1ByNameAndUser(String name, User user);

    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name = ?1 and user_id = ?2")
    Optional<CashFlow> findByName(String name, User user);

    Iterable<CashFlow> findAllByUserAndModificationDateBetweenOrderByModificationDate(User user, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndPriorityAndCategoryAndModificationDateBetweenOrderByModificationDate(User user, Priority priority, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndCategoryAndModificationDateBetweenOrderByModificationDate(User user, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndPriorityAndModificationDateBetweenOrderByModificationDate(User user, Priority priority, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndFlowDirectionAndModificationDateBetweenOrderByModificationDate(User user, FlowDirection flowDirection, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndFlowDirectionAndCategoryAndModificationDateBetweenOrderByModificationDate(User user, FlowDirection flowDirection, Category category, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndPriorityAndFlowDirectionAndModificationDateBetweenOrderByModificationDate(User user, Priority priority, FlowDirection flowDirection, LocalDateTime startDate, LocalDateTime endDate);

    Iterable<CashFlow> findAllByUserAndPriorityAndFlowDirectionAndCategoryAndModificationDateBetweenOrderByModificationDate(User user, Priority priority, FlowDirection flowDirection, Category category, LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("UPDATE CashFlow CF set CF.name = ?1, CF.description = ?2, CF.amount = ?3, CF.category = ?4, CF.flowDirection = ?5, CF.priority = ?6, CF.modificationDate = ?8 where CF.id = ?7")
    void setCashFlowById(String name, String description, int amount, Category category, FlowDirection flowDirection, Priority priority, String cashFlowId, LocalDateTime date);

}
