package com.romantupikov.cloudstorage.constraint;

import com.romantupikov.cloudstorage.model.Account;
import com.romantupikov.cloudstorage.model.form.AccountForm;
import com.romantupikov.cloudstorage.repositories.AccountRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Object> {

    private final AccountRepository accountRepository;

    public UniqueUsernameValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AccountForm accountForm = (AccountForm) obj;

        Optional<Account> accountOptional = accountRepository.findByUsername(accountForm.getUsername());

        boolean valid = !accountOptional.isPresent();

        if (!valid) {
            context.buildConstraintViolationWithTemplate("Аккаунт с таким именем уже существует.")
                    .addPropertyNode("username")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
