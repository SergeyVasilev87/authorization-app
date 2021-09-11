package com.github.SergeyVasilev87.first.controller;

import com.github.SergeyVasilev87.first.entity.User;
import com.github.SergeyVasilev87.first.service.UserDetailServiceImpl;
import com.github.SergeyVasilev87.first.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ValidationService validationService;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/hello")
    public String getHelloPage() {
        return "hello";
    }

    @GetMapping("/login")
    public String getDarkLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new User()); //добавляем пустую форму User для заполнения данными из html-страницы
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("userForm") User user, BindingResult bindingResult) {

        if (!validationService.passwordSimilarityCheck(user)) {
            String err = "Password mismatch. Please try again.";
            ObjectError error = new ObjectError("globalError", err);
            bindingResult.addError(error);
            return "registration";
        }
        userDetailService.saveuser(user);
        return "redirect:/login";
    }
}
