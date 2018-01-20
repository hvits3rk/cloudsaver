package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final AccountRepository accountRepository;

    public RegisterController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/register")
    public String registerPage(Account account) {

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Account account) {

        System.out.println(account);
        account.getRoles().add(new SimpleGrantedAuthority("USER"));

        accountRepository.save(account);

        return "redirect:/";
    }
}
