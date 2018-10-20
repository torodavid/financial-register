package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dto.DateWrapper;
import com.torodavid.thesis.financialregister.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/view")
    public ModelAndView statisticsView() {
        return new ModelAndView("statistics/view");
    }

    @PostMapping(value = "/view")
    public @ResponseBody Map<LocalDate, Integer> monthlyStat(@RequestBody DateWrapper dateWrapper) {
        return statisticsService.getStatistics(dateWrapper.getStartDate().atStartOfDay(), dateWrapper.getEndDate().atStartOfDay());
    }

}
