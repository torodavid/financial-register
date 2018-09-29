package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class CashFlowGenerator {

    @Autowired
    private CashFlowService cashFlowService;

    public void generateCashFlows(int n) {
        Stream.iterate(1, i -> i++).limit(n).forEach(i ->
        {
            CashFlow cashFlow = new CashFlow();
            cashFlow.setName("Kisnyul" + new Random().nextInt());
            cashFlow.setDescription("Description" + new Random().nextInt());
            cashFlow.setCategory(Category.ENTERTAINMENT);
            cashFlow.setFlowDirection(FlowDirection.IN);
            cashFlow.setPriority(Priority.TWO);
            cashFlow.setCreationDate(LocalDateTime.now());
            cashFlow.setModificationDate(LocalDateTime.now());
            cashFlowService.save(cashFlow);
        });
    }

}
