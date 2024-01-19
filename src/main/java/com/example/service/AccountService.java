package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
       this.accountRepository = accountRepository;
    }

    public Account findByUsername(String username){
         return accountRepository.findByUsername(username);
    }

    public Account save(Account account){
        return  accountRepository.save(account);

    }

    public Optional<Account> findById(Integer userId){
        return accountRepository.findById(userId);
    }
}

