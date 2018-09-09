package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.service.CashFlowGenerator;
import com.torodavid.thesis.financialregister.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

    @Autowired
    private CashFlowService cashFlowService;

    @Autowired
    private CashFlowGenerator cashFlowGenerator;

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping(value = "/mvTeszt")
    public ModelAndView proba() {

        ModelAndView mv = new ModelAndView();

        //Map<String, String> content = new HashMap<>();
        //content.put("dummy", "dsdddddddddddd");
        mv.addObject("dummy", "KISNYUL");

        mv.setViewName("proba");


        return mv;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping(path="/generateTest")
    public String generateTest() {
        cashFlowGenerator.generateCashFlows(30);
        return "redirect:/all";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<CashFlow> getAllOnes() {
        return cashFlowService.getAllCashFlows();
    }

}
