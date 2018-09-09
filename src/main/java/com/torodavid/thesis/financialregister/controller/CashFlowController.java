package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
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
    public String processCreationForm(CashFlow cashFlow) {
        Long id = cashFlowService.save(cashFlow).getId();
        return "redirect:/cashFlow/" + id;
    }

    @GetMapping("/modify/{cashFlowId}")
    public String modifyCashFlow(Map<String, Object> model, @PathVariable("cashFlowId") Long id) {
        CashFlow kisnyul = cashFlowService.getCashFlowById(id).get();
        model.put("cashFlow", kisnyul);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/modify/{cashFlowId}")
    public String processModifyCashFlowForm(CashFlow cashFlow) {
        cashFlowService.save(cashFlow).getId();
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/delete/{cashFlowId}")
    public String deleteCashFlow(@PathVariable("cashFlowId") Long id) {
        cashFlowService.deleteCashFlowById(id);
        return "redirect:/cashFlow/list";
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

    @GetMapping("/list")
    public ModelAndView showAllCashFlows() {
        Iterable<CashFlow> allCashFlowsById = cashFlowService.getAllCashFlows();
        ModelAndView mav = new ModelAndView("cashFlow/details");
        mav.addObject("cashFlows", StreamSupport.stream(allCashFlowsById.spliterator(), false).collect(Collectors.toList()));
        return mav;
    }

}
