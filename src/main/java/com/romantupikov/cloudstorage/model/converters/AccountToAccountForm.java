package com.romantupikov.cloudstorage.model.converters;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.form.AccountForm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountForm implements Converter<Account, AccountForm> {

    @Override
    public AccountForm convert(Account source) {
        AccountForm accountForm = new AccountForm();
        accountForm.setId(source.getId().toHexString());
        accountForm.setPassword(source.getPassword());
        accountForm.setPasswordCheck(source.getPassword());
        accountForm.setUsername(source.getUsername());
        accountForm.setFiles(source.getFiles());
        accountForm.setGrantedAuthorities(source.getRoles());

        return accountForm;
    }

}
