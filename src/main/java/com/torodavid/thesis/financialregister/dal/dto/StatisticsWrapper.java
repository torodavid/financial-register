package com.torodavid.thesis.financialregister.dal.dto;

import com.torodavid.thesis.financialregister.dal.enums.Category;
import com.torodavid.thesis.financialregister.dal.enums.FlowDirection;
import com.torodavid.thesis.financialregister.dal.enums.Priority;

import java.time.LocalDate;
import java.util.Map;

public class StatisticsWrapper {

    private LocalDate startDate;
    private LocalDate endDate;
    private Priority priority;
    private FlowDirection flowDirection;
    private Category category;
    private Map<LocalDate, Integer> chartData;
    private String bgColor;
    private String borderColor;
    private Boolean prioritized;
    private Boolean directionConsidered;
    private Boolean categoryConsidered;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public FlowDirection getFlowDirection() {
        return flowDirection;
    }

    public void setFlowDirection(FlowDirection flowDirection) {
        this.flowDirection = flowDirection;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Map<LocalDate, Integer> getChartData() {
        return chartData;
    }

    public void setChartData(Map<LocalDate, Integer> chartData) {
        this.chartData = chartData;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public Boolean getPrioritized() {
        return prioritized;
    }

    public void setPrioritized(Boolean prioritized) {
        this.prioritized = prioritized;
    }

    public Boolean getDirectionConsidered() {
        return directionConsidered;
    }

    public void setDirectionConsidered(Boolean directionConsidered) {
        this.directionConsidered = directionConsidered;
    }

    public Boolean getCategoryConsidered() {
        return categoryConsidered;
    }

    public void setCategoryConsidered(Boolean categoryConsidered) {
        this.categoryConsidered = categoryConsidered;
    }
    
}
