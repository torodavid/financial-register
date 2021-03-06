package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.model.CashFlow;
import com.torodavid.thesis.financialregister.dal.dto.StatisticsWrapper;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.TreeMap;

@Service
public class StatisticsService {

    public static final String YELLOW = "rgba(230, 230, 0, 0.4)";
    public static final String BLUE = "rgba(54, 162, 235,1)";
    public static final String PURPLE = "rgba(102, 0, 102, 0.2)";
    public static final String GREEN = "rgba(0, 153, 0, 0.4)";
    public static final String RED = "rgba(204, 0, 0, 0.3)";

    @Autowired
    CashFlowService cashFlowService;

    @Autowired
    private UserService userService;

    public StatisticsWrapper getStatistics(StatisticsWrapper request) {
        TreeMap<LocalDate, Integer> chartData = new TreeMap<>((o1, o2) -> Long.compare(o1.toEpochDay(), o2.toEpochDay()));
        int sum = 0;
        Iterable<CashFlow> cashFlows;
        StatisticsWrapper statisticsWrapper = new StatisticsWrapper();
        if (request.getPrioritized()) {
            if (request.getDirectionConsidered()) {
                if (request.getCategoryConsidered()) {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenPrioritizedAndDirectionConsideredAndCategorized(userService.getCurrentUser(), request.getPriority(), request.getFlowDirection(), request.getCategory(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                } else {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenPrioritizedAndDirectionConsidered(userService.getCurrentUser(), request.getPriority(), request.getFlowDirection(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                }
                statisticsWrapper.setBorderColor(BLUE);
                statisticsWrapper.setBgColor(YELLOW);
            } else {
                if (request.getCategoryConsidered()) {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenPrioritizedAndCategorized(userService.getCurrentUser(), request.getPriority(), request.getCategory(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                } else {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenPrioritized(userService.getCurrentUser(), request.getPriority(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                }
                statisticsWrapper.setBorderColor(BLUE);
                statisticsWrapper.setBgColor(PURPLE);
            }

        } else {
            if (request.getDirectionConsidered()) {
                if (request.getCategoryConsidered()) {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenDirectionConsideredAndCategorized(userService.getCurrentUser(), request.getFlowDirection(), request.getCategory(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                } else {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenDirectionConsidered(userService.getCurrentUser(), request.getFlowDirection(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                }
                statisticsWrapper.setBorderColor(YELLOW);
                statisticsWrapper.setBgColor(GREEN);
            } else {
                if (request.getCategoryConsidered()) {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetweenCategorized(userService.getCurrentUser(), request.getCategory(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                } else {
                    cashFlows = cashFlowService.findAllByUserAndModificationDateBetween(userService.getCurrentUser(), request.getStartDate().atStartOfDay(), request.getEndDate().atStartOfDay());
                }
                statisticsWrapper.setBorderColor(YELLOW);
                statisticsWrapper.setBgColor(RED);
            }
        }
        for (CashFlow cashFlow : cashFlows) {
            sum += cashFlow.getFlowDirection() == FlowDirection.IN ? cashFlow.getAmount() : -1*cashFlow.getAmount();
            chartData.put(cashFlow.getModificationDate().toLocalDate(), sum);
        }

        statisticsWrapper.setStartDate(request.getStartDate());
        statisticsWrapper.setEndDate(request.getEndDate());
        statisticsWrapper.setChartData(chartData);

        return statisticsWrapper;
    }

}
