package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.converters.AccountFormToAccount;
import com.romantupikov.cloudstorage.model.form.AccountForm;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountFormToAccount accountFormToAccount;

    public AccountServiceImpl(AccountRepository accountRepository, AccountFormToAccount accountFormToAccount) {
        this.accountRepository = accountRepository;
        this.accountFormToAccount = accountFormToAccount;
    }

    @Override
    public List<Account> listAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account getById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account saveOrUpdate(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account saveOrUpdateAccountForm(AccountForm accountForm) {
        Account savedAccount = accountFormToAccount.convert(accountForm);

        accountRepository.save(savedAccount);

        return savedAccount;
    }
}
