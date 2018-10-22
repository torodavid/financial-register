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

    @Autowired
    private UserService userService;

    public StatisticsWrapper getStatistics(StatisticsWrapper request) {
        TreeMap<LocalDate, Integer> chartData = new TreeMap<>((o1, o2) -> Long.compare(o1.toEpochDay(), o2.toEpochDay()));;
        int sum = 0;
        Iterable<CashFlow> cashFlows;
        if (request.getPrioritized()) {
            cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenPrioritized(userService.getCurrentUser(), request.getPriority(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
        } else {
            cashFlows = cashFlowService.findAllByUserAndModificationDateBetween(userService.getCurrentUser(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
        }
        for (CashFlow cashFlow : cashFlows) {
            sum += cashFlow.getFlowDirection() == FlowDirection.IN ? cashFlow.getAmount() : -1*cashFlow.getAmount();
            chartData.put(cashFlow.getModificationDate().toLocalDate(), sum);
        }
        StatisticsWrapper statisticsWrapper = new StatisticsWrapper();
        statisticsWrapper.setStartDate(request.getStartDate());
        statisticsWrapper.setEndDate(request.getEndDate());
        statisticsWrapper.setChartData(chartData);
        statisticsWrapper.setBorderColor("rgba(255,99,132,1)");
        statisticsWrapper.setBgColor("rgba(54, 162, 235, 0.2)");
        return statisticsWrapper;
    }

}
