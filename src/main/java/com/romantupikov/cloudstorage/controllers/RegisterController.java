package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.services.AccountService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String registerPage(Account account) {

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(Account account) {

        System.out.println(account);
        account.getRoles().add(new SimpleGrantedAuthority("USER"));

        accountService.save(account);

        return "redirect:/";
    }
}
