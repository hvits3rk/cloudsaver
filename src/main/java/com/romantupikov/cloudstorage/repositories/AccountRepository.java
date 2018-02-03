package com.romantupikov.cloudstorage.repositories;


import com.romantupikov.cloudstorage.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByUsername(String username);
}
