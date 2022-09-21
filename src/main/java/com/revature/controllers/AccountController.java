package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Authorized
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE) 
    // Added. Inserts new account to user profile
    public ResponseEntity<Account> insertAccount(@RequestBody Account account, @RequestHeader("Current-User") String userId) {
    	User user = userService.findById(Integer.parseInt(userId));
    	Account newAccount = accountService.insertAccount(account, user);
    	return ResponseEntity.status(201).body(newAccount);
    }
    
    @Authorized
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //Added 
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @RequestHeader("Current-User") String userId){
    	User user = userService.findById(Integer.parseInt(userId));
    	Account newAccount = accountService.updateAccount(account, user);
    	return ResponseEntity.status(200).body(newAccount);
    }
    
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") int accountId) {
        Account account = accountService.findByAccountId(accountId);
        System.out.println("I was called here in the Controller Layer");
      
        if(account==null) {
            return ResponseEntity.notFound().build();
        } else {
        	  return ResponseEntity.status(200).body(account);
        }
      
    }
    
    //added and will get all accounts based on userId 
    @Authorized
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Account>> getAllAccount(@PathVariable("id") int userId) {
        List<Account> accounts = userService.getAllAccounts(userId);
        return ResponseEntity.status(200).body(accounts);
    }

    @Authorized
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account, @RequestHeader("Current-User") String userId) {
        return ResponseEntity.ok(accountService.upsertAccount(account, userId));
    }

    @Authorized
    @GetMapping("/{id}/transaction")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("id") int accountId) {
        return ResponseEntity.ok(accountService.getAllTransactions(accountId));
    }

    @Authorized
    @PostMapping(value = "/{id}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> addTransaction(@PathVariable("id") int accountId, @RequestBody Transaction transaction) {
        return new ResponseEntity<>(accountService.upsertTransaction(accountId, transaction), HttpStatus.CREATED);
    }

}
