package com.torodavid.thesis.financialregister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @GetMapping("/view")
    public ModelAndView statisticsView() {
        return new ModelAndView("statistics/view");
    }

}
