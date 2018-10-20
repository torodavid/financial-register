package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dto.StatisticsWrapper;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class StatisticsService {

    @Autowired
    CashFlowService cashFlowService;

    public Map<LocalDate, Integer> getStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        int sum = 0;
        TreeMap<LocalDate, Integer> ret = new TreeMap<>((o1, o2) -> Long.compare(o1.toEpochDay(), o2.toEpochDay()));
        Iterable<CashFlow> cashFlows = cashFlowService.findAllByModificationDateBetween(startDate, endDate);
        for (CashFlow cashFlow : cashFlows) {
            sum += cashFlow.getFlowDirection() == FlowDirection.IN ? cashFlow.getAmount() : -1*cashFlow.getAmount();
            ret.put(cashFlow.getModificationDate().toLocalDate(), sum);
        }

        return ret;
    }

}
