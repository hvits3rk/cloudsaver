package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final HttpServletRequest httpServletRequest;

    public AccountServiceImpl(AccountRepository accountRepository, HttpServletRequest httpServletRequest) {
        this.accountRepository = accountRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> getCurrentUser() {
        return accountRepository.findByUsername(httpServletRequest.getRemoteUser());
    }
}
