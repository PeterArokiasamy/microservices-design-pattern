package com.arokia.accounts.repositories;

import com.arokia.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    /* Whenever we create or delete using custom methods in repository, below 2 annotation is mandatory
        @Modifying - will tell Spring data JPA Framework that this method is going to modify the data.
        @Transaction - so execute this method as a Transaction. If any error at runtime, partial data will be roll back
     */

    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
