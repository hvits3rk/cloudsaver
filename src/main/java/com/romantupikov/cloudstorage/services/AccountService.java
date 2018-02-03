package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.form.AccountForm;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> listAll();

    Account getById(String id);

    Account getByUsername(String username);

    Account saveOrUpdate(Account account);

    void delete(String id);

    Account saveOrUpdateAccountForm(AccountForm accountForm);
}
