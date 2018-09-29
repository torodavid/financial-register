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

@Repository
public interface CashFlowRepository extends CrudRepository<CashFlow, Long> {

    Iterable<CashFlow> findAllCashFlowsByName(String name);
    Iterable<CashFlow> getAllCashFlowsByCategory(Category category);
    Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection);
    Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority);
    Iterable<CashFlow> findAllByUser(User user);

    @Modifying
    @Query("UPDATE CashFlow CF set CF.name = ?1, CF.description = ?2, CF.amount = ?3, CF.category = ?4, CF.flowDirection = ?5, CF.priority = ?6 where CF.id = ?7")
    void setCashFlowById(String name, String description, int amount, Category category, FlowDirection flowDirection, Priority priority, Long cashFlowId);

}
