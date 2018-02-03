package com.romantupikov.cloudstorage.model.converters;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.form.AccountForm;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AccountFormToAccount implements Converter<AccountForm, Account> {

    @Override
    public Account convert(AccountForm source) {
        Account account = new Account();
        if (source.getId() != null && !StringUtils.isEmpty(source.getId())) {
            account.setId(new ObjectId(source.getId()));
        }
        account.setUsername(source.getUsername());
        account.setPassword(source.getPassword());
        account.setFiles(source.getFiles());
        account.setRoles(source.getGrantedAuthorities());

        return account;
    }
}
