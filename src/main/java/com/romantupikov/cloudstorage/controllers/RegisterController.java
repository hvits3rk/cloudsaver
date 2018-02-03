package com.romantupikov.cloudstorage.controllers;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.form.AccountForm;
import com.romantupikov.cloudstorage.services.AccountService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String registerPage(AccountForm accountForm) {

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid AccountForm accountForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        accountForm.getGrantedAuthorities().add(new SimpleGrantedAuthority("USER"));
        accountService.saveOrUpdateAccountForm(accountForm);

        return "redirect:/";
    }
}
