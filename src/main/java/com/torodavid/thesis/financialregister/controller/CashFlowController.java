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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = "/cashFlow")
public class CashFlowController {

    private static int currentPage = 1;
    private static int pageSize = 10;

    private static final String CASH_FLOW_CREATE_OR_UPDATE = "cashFlow/createOrUpdateCashFlow";

    @Autowired
    private CashFlowService cashFlowService;

    @GetMapping("")
    public String processFindForm(CashFlow cashFlow, BindingResult result, Map<String, Object> model) {

        Iterable<CashFlow> cashFlowsByName = cashFlowService.findAllCashFlowsByName(cashFlow.getName());

        if (!cashFlowsByName.iterator().hasNext()) {
            //result.rejectValue("kisnyul", "notFound", "not found");
            model.put("redirected", true);
            return CASH_FLOW_CREATE_OR_UPDATE;
        } else {
            List<Long> cashFlowIds = StreamSupport.stream(cashFlowsByName.spliterator(), true).map(cf -> cf.getId()).collect(Collectors.toList());
            return "redirect:/cashFlow/" + cashFlowIds.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .trim();
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Map<String, Object> model) {
        CashFlow cashFlow = new CashFlow();
        model.put("cashFlow", cashFlow);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/new")
    public String processCreationForm(CashFlow cashFlow) {
        Long id = cashFlowService.save(cashFlow).getId();
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/modify/{cashFlowId}")
    public String modifyCashFlow(Map<String, Object> model, @PathVariable("cashFlowId") Long id) {
        CashFlow kisnyul = cashFlowService.getCashFlowById(id).get();
        model.put("cashFlow", kisnyul);
        return CASH_FLOW_CREATE_OR_UPDATE;
    }

    @PostMapping("/modify/{cashFlowId}")
    public String processModifyCashFlowForm(CashFlow cashFlow) {
        cashFlowService.update(cashFlow);
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/delete/{cashFlowId}")
    public String deleteCashFlow(@PathVariable("cashFlowId") Long id) {
        cashFlowService.deleteCashFlowById(id);
        return "redirect:/cashFlow/list";
    }

    @GetMapping("/{cashFlowIds}")
    public String showCashFlows(@PathVariable("cashFlowIds") List<Long> cashFlowIds, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
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
    public String initFindForm(Map<String, Object> model) {
        model.put("cashFlow", new CashFlow());
        return "cashFlow/findCashFlow";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
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
