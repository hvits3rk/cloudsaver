package com.romantupikov.cloudstorage.repositories;


import com.romantupikov.cloudstorage.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {

    Optional<Account> findByUsername(String s);
}
