package com.romantupikov.cloudstorage.services;

import com.romantupikov.cloudstorage.model.Account;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final AccountService accountService;

    public AuthProvider(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        UserDetails loadedUser;

        Account account = accountService.getByUsername(username);

        if (account != null && account.getPassword().equals(authentication.getCredentials())) {
            loadedUser = new User(account.getUsername(), account.getPassword(), account.getRoles());
        } else {
            throw new InternalAuthenticationServiceException("Аккаунт не найден!");
        }

        return loadedUser;
    }
}
