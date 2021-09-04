package com.github.SergeyVasilev87.first.controller;

import com.github.SergeyVasilev87.first.entity.Role;
import com.github.SergeyVasilev87.first.entity.Status;
import com.github.SergeyVasilev87.first.entity.User;
import com.github.SergeyVasilev87.first.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {
    @Autowired
    UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

//    @GetMapping("/login")
//    public String getLoginPage() {
//        return "login";
//    }

    @GetMapping("/hello")
    public String getHelloPage() {
        return "hello";
    }

//    @GetMapping("/mylogin")
//    public String getMyLoginPage() {
//        return "mylogin";
//    }

    @GetMapping("/darklogin")
    public String getDarkLoginPage() {
        return "darklogin";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new User()); //добавляем пустую форму User для заполнения данными из html-страницы
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("userForm") User user) {
        userDetailService.saveuser(user);
        return "redirect:/darklogin";
    }
}
