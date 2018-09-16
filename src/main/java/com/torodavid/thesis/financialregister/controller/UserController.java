package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dto.UserDto;
import com.torodavid.thesis.financialregister.service.SecurityService;
import com.torodavid.thesis.financialregister.service.UserService;
import com.torodavid.thesis.financialregister.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(UserDto accountDto) {
        userService.register(accountDto);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUserAccount(UserDto accountDto) {

         return "home";
    }

    @GetMapping("/list")
    public ModelAndView showAllCashFlows() {
        Iterable<UserDto> allCashFlowsById = userService.getAllUsers();
        ModelAndView mav = new ModelAndView("user/userDetails");
        mav.addObject("users", StreamSupport.stream(allCashFlowsById.spliterator(), false).collect(Collectors.toList()));
        return mav;
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}
