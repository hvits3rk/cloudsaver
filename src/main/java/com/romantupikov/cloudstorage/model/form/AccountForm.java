package com.romantupikov.cloudstorage.model.form;

import com.romantupikov.cloudstorage.constraint.PasswordMatches;
import com.romantupikov.cloudstorage.constraint.UniqueUsername;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@PasswordMatches
@UniqueUsername
public class AccountForm {

    private String id;

    @Email
    private String username;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String passwordCheck;

    private Set<String> files = new HashSet<>();
    private Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public Set<String> getFiles() {
        return files;
    }

    public void setFiles(Set<String> files) {
        this.files = files;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }
}
