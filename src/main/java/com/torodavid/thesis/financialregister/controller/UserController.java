package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dto.UserDto;
import com.torodavid.thesis.financialregister.service.SecurityService;
import com.torodavid.thesis.financialregister.service.UserService;
import com.torodavid.thesis.financialregister.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private static int currentPage = 1;
    private static int pageSize = 10;

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

    @GetMapping("/profile/{username}")
    public String modifyUser(Map<String, Object> model, @PathVariable("username") String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(username)) {

            UserDto userDto = userService.getUserDtoByUsername(username);
            model.put("userDto", userDto);
            return "user/profile";
        } else {
            return "redirect:/users/access-denied";
        }
    }

    @PostMapping("/profile/{username}")
    public String processModifyUserForm(UserDto userDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(userDto.getUsername())) {
            userService.update(userDto);
            return "redirect:/home";
        } else {
            return "redirect:/users/access-denied";
        }
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String id) {
        userService.deleteById(id);
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<UserDto> userDtoPage = userService.findPaginated(PageRequest.of(currentPage - 1, pageSize), Optional.empty());

        model.addAttribute("userDtoPage", userDtoPage);

        int totalPages = userDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "user/userDetails";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}
