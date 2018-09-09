package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.User;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
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
        return "registration";
    }

    @PostMapping("/registration")
    //public String registerUserAccount(@ModelAttribute("user") UserDto accountDto, BindingResult result, WebRequest request, Errors errors) {
    public ModelAndView registerUserAccount(UserDto accountDto) {
        //TODO NEM MEGY EZ A FOSSSSSSSSSSSSSSSSSSSS
        User registered = new User();
        //if (!result.hasErrors()) {
        registered.setPassword(accountDto.getPassword());
        registered.setUsername(accountDto.getUsername());
        registered.setEmail(accountDto.getEmail());
        userService.save(registered);

        return new ModelAndView();
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
}