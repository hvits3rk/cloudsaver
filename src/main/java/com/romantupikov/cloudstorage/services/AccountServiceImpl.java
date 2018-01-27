package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
}
