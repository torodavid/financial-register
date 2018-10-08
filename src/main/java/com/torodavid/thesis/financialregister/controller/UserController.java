package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dto.UserDto;
import com.torodavid.thesis.financialregister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
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

    @GetMapping("/registration")
    public ModelAndView showRegistrationForm(Model model, UserDto accountDto) {
        model.addAttribute("userDto", accountDto);
        return new ModelAndView("user/registration");
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@Valid UserDto accountDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            userService.register(accountDto);
            return new ModelAndView("success");
        }
        if (!accountDto.getPassword().equals(accountDto.getMatchingPassword())) {
            result.rejectValue("matchingPassword", "error.matchingPassword", "Nem egyezik a két jelszó!");
        }
        return showRegistrationForm(model, accountDto);
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
    public ModelAndView modifyUser(UserDto userDto, @PathVariable("username") String username, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView modelAndView = new ModelAndView();
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(username)) {

            UserDto userDto2 = userService.getUserDtoByUsername(username);
            model.addAttribute("userDto", userDto2);

            modelAndView.setViewName("user/profile");
        } else {
            modelAndView.setViewName("error/access-denied");
        }
        return modelAndView;
    }

    @PostMapping("/profile/{username}")
    public ModelAndView processModifyUserForm(@Valid UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(userDto.getUsername())) {
                userService.update(userDto);
                return new ModelAndView("success");
            } else {
                return new ModelAndView("error/access-denied");
            }
        }
        return modifyUser(userDto, userDto.getUsername(), model);
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
