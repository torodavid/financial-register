package com.torodavid.thesis.financialregister.service;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.dal.dto.StatisticsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    CashFlowService cashFlowService;

    public StatisticsWrapper csudalatosStatisztikaiKisnyulakParameterekbol(Long... ids) {
        Iterable<CashFlow> allCashFlowsByIds = cashFlowService.getAllCashFlowsByIds(ids);


        return new StatisticsWrapper();
    }

}
