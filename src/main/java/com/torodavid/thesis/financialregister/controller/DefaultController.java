package com.torodavid.thesis.financialregister.controller;

import com.torodavid.thesis.financialregister.dal.dao.One;
import com.torodavid.thesis.financialregister.dal.repository.OneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Controller
public class DefaultController {

    @Autowired
    private OneRepository oneRepository;

    @GetMapping("/")
    public String home1() {
        return "/index";
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

    @GetMapping("/user")
    public String user() {
        return "user";
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
    public @ResponseBody boolean generateTest() {
        Stream.iterate(1, i -> i++).limit(10).forEach(i ->
        {
            One one = new One();
            one.setName("kisnyul" + new Random().nextInt());
            oneRepository.save(one);

        });
        return true;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<One> getAllOnes() {
        return oneRepository.findAll();
    }

}
