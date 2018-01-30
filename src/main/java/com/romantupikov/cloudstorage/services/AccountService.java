package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;

import java.util.Optional;

public interface AccountService {

    void save(Account account);

    Optional<Account> findByUsername(String username);

    Optional<Account> getCurrentUser();
}
