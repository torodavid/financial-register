package com.torodavid.thesis.financialregister.dal.dto;

import java.time.LocalDate;

public class StatisticsWrapper {

    private LocalDate when;
    private Integer sum;

    public LocalDate getWhen() {
        return when;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
