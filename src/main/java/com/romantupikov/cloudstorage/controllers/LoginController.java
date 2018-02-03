package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.form.AccountForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(AccountForm accountForm) {

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginForm(AccountForm accountForm) {

        return "redirect:/upload";
    }
}
