package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dto.StatisticsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    CashFlowService cashFlowService;

    public StatisticsWrapper csudalatosStatisztikaiKisnyulakParameterekbol(List<String> ids) {
        Iterable<CashFlow> allCashFlowsByIds = cashFlowService.findAllCashFlowsByIds(ids);


        return new StatisticsWrapper();
    }

}
