package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = "/cashFlow")
public class CashFlowController {

    private static final String CASH_FLOW_CREATE_OR_UPDATE = "cashFlow/createOrUpdateCashFlow";

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping("")
    public String processFindForm(CashFlow cashFlow, BindingResult result, Map<String, Object> model) {

        Iterable<CashFlow> cashFlowsByName = cashFlowService.findAllCashFlowsByName(cashFlow.getName());

        if (!cashFlowsByName.iterator().hasNext()) {
            //result.rejectValue("kisnyul", "notFound", "not found");
            return CASH_FLOW_CREATE_OR_UPDATE;
        } else {
            List<Long> cashFlowIds = StreamSupport.stream(cashFlowsByName.spliterator(), true).map(cf -> cf.getId()).collect(Collectors.toList());
            return "redirect:/cashFlow/" + cashFlowIds.toString().replace("[", "")
                    .replace("]", "")
                    .trim();
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Map<String, Object> model) {
        CashFlow kisnyul = new CashFlow();
        model.put("cashFlow", kisnyul);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/new")
    public String processCreationForm(CashFlow cashFlow, BindingResult result) {

        Long id = cashFlowService.save(cashFlow).getId();
        return "redirect:/cashFlow/" + id;
    }

    @GetMapping("/{cashFlowIds}")
    public ModelAndView showCashFlows(@PathVariable("cashFlowIds") List<Long> cashFlowIds) {
        Iterable<CashFlow> allCashFlowsById = cashFlowService.findAllCashFlowsByIds(cashFlowIds);
        ModelAndView mav = new ModelAndView("cashFlow/details");
        mav.addObject("cashFlows", StreamSupport.stream(allCashFlowsById.spliterator(), false).collect(Collectors.toList()));
        return mav;
    }

    @GetMapping("/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("cashFlow", new CashFlow());
        return "cashFlow/findCashFlow";
    }


}
