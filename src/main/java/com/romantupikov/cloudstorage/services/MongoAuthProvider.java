package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.repositories.AccountRepository;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MongoAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final AccountRepository accountRepository;

    public MongoAuthProvider(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;

        Optional<Account> accountOptional = accountRepository.findByUsername(username);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            loadedUser = new User(account.getUsername(), account.getPassword(), account.getRoles());
        } else {
            throw new InternalAuthenticationServiceException("Аккаунт не найден!");
        }

        return loadedUser;
    }
}
