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

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CashFlowRepository extends CrudRepository<CashFlow, String> {

    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name like %?1% and user_id = ?2")
    Iterable<CashFlow> findAllCashFlowsByName(String username, User user);
    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name like %?1%")
    Iterable<CashFlow> findAllCashFlowsByName(String username);
    Iterable<CashFlow> getAllCashFlowsByCategory(Category category);
    Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection);
    Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority);
    Iterable<CashFlow> findAllByUser(User user);
    Optional<CashFlow> findByName(String name);
    @Query("select cashFlow from CashFlow cashFlow where cashFlow.name = ?1 and user_id = ?2")
    Optional<CashFlow> findByName(String name, User user);

    @Modifying
    @Query("UPDATE CashFlow CF set CF.name = ?1, CF.description = ?2, CF.amount = ?3, CF.category = ?4, CF.flowDirection = ?5, CF.priority = ?6, CF.modificationDate = ?8 where CF.id = ?7")
    void setCashFlowById(String name, String description, int amount, Category category, FlowDirection flowDirection, Priority priority, String cashFlowId, LocalDateTime date);

}
