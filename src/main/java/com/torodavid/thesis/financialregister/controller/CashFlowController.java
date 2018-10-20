package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.CashFlow;
import com.torodavid.thesis.financialregister.service.CashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/cashFlow")
public class CashFlowController {

    private static int currentPage = 1;
    private static int pageSize = 20;

    private static final String CASH_FLOW_CREATE_OR_UPDATE = "cashFlow/createOrUpdateCashFlow";

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping("")
    public String findCashFlow(CashFlow cashFlow, BindingResult result, Map<String, Object> model) {
        Optional<CashFlow> cashFlowByName = cashFlowService.findCashFlowByName(cashFlow.getName());
        if (cashFlowByName.isPresent()) {
            return "redirect:/cashFlow/" + cashFlowByName.get().getId();
        } else {
            return initFindForm(model, true);
        }
    }

    @GetMapping("/new")
    public String initCreationForm(CashFlow cashFlow, Model model) {
        model.addAttribute("cashFlow", cashFlow);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid CashFlow cashFlow, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            cashFlowService.save(cashFlow).getId();
            return "redirect:/cashFlow/list";
        }
        return initCreationForm(cashFlow, model);
    }

    @GetMapping("/view/{cashFlowId}")
    public String viewCashFlow(Map<String, Object> model, @PathVariable("cashFlowId") String id) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id).get();
        model.put("cashFlow", cashFlow);
        return "cashFlow/view";
    }

    @GetMapping("/modify/{cashFlowId}")
    public String modifyCashFlow(Map<String, Object> model, @PathVariable("cashFlowId") String id) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id).get();
        model.put("cashFlow", cashFlow);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/modify/{cashFlowId}")
    public String processModifyCashFlowForm(CashFlow cashFlow) {
        cashFlowService.update(cashFlow);
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/delete/{cashFlowId}")
    public String deleteCashFlow(@PathVariable("cashFlowId") String id) {
        cashFlowService.deleteCashFlowById(id);
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/{cashFlowIds}")
    public String showCashFlows(@PathVariable("cashFlowIds") List<String> cashFlowIds, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        currentPage = 1;
        size.ifPresent(s -> pageSize = s);

        Page<CashFlow> cashFlowPage = cashFlowService.findPaginated(PageRequest.of(currentPage - 1, pageSize), Optional.ofNullable(cashFlowIds));
        model.addAttribute("cashFlowPage", cashFlowPage);

        int totalPages = cashFlowPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cashFlow/details";
    }

    @GetMapping("/find")
    public String initFindForm(Map<String, Object> model, Boolean redirected) {
        model.put("cashFlow", new CashFlow());
        model.put("redirected", redirected);
        return "cashFlow/findCashFlow";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listCashFlows(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<CashFlow> cashFlowPage = cashFlowService.findPaginated(PageRequest.of(currentPage - 1, pageSize), Optional.empty());

        model.addAttribute("cashFlowPage", cashFlowPage);

        int totalPages = cashFlowPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cashFlow/details";
    }

    @GetMapping("/generate")
    public @ResponseBody
    Boolean generate() {
        cashFlowService.generateCashFlowsToCurrentUser();
        return true;
    }

}
