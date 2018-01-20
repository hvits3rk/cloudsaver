package com.romantupikov.cloudstorage.repositories;


import com.romantupikov.cloudstorage.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByUsername(String s);
}
