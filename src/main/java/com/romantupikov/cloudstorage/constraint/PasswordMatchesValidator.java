package com.romantupikov.cloudstorage.constraint;

import com.romantupikov.cloudstorage.model.form.AccountForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        AccountForm accountForm = (AccountForm) obj;

        boolean valid = accountForm.getPassword().equals(accountForm.getPasswordCheck());

        if (!valid) {
            context.buildConstraintViolationWithTemplate("Пароли не совпадают")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
