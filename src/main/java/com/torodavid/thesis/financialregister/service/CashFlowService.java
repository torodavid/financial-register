package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import com.torodavid.thesis.financialregister.dal.repository.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CashFlowService {

    @Autowired
    private CashFlowRepository cashFlowRepository;

    public CashFlow save(CashFlow cashFlow) {
        return cashFlowRepository.save(cashFlow);
    }

    public Optional<CashFlow> getCashFlowById(Long id) {
        return cashFlowRepository.findById(id);
    }

    /*public Optional<CashFlow> getCashFlowByUserId(Long id) {
        return cashFlowRepository.getCashFlowByUserId(id);
    }*/

    public Iterable<CashFlow> getAllCashFlows() {
        return cashFlowRepository.findAll();
    }

    public Iterable<CashFlow> findAllCashFlowsByName(String name) {
        return cashFlowRepository.findAllCashFlowsByName(name);
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

    public Iterable<CashFlow> findAllCashFlowsByIds(List<Long> ids) {
        return cashFlowRepository.findAllById(ids);
    }

}
