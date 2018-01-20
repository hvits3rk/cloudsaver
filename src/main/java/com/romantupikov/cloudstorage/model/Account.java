package com.romantupikov.cloudstorage.model;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class Account {
    @Id
    private String id;

    private String username;

    private String password;

    private Set<GrantedAuthority> roles = new HashSet<>();

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

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

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
