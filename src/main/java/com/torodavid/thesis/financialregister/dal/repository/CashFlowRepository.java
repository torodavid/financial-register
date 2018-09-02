package com.torodavid.thesis.financialregister.dal.repository;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashFlowRepository extends CrudRepository<CashFlow, Long> {

    //Optional<CashFlow> getCashFlowByUserId(Long id);
    Iterable<CashFlow> findAllCashFlowsByName(String name);
    Iterable<CashFlow> getAllCashFlowsByCategory(Category category);
    Iterable<CashFlow> getAllCashFlowsByFlowDirection(FlowDirection flowDirection);
    Iterable<CashFlow> getAllCashFlowsByPriority(Priority priority);

}
