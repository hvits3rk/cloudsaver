package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    private final AccountRepository accountRepository;

    public LoginController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/login")
    public String loginPage(Account account) {

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginForm(Account account) {
        Optional<Account> userOptional = accountRepository.findByUsername(account.getUsername());

        if (userOptional.isPresent()) {
            System.out.println(account);
        }

        return "redirect:/upload";
    }
}
