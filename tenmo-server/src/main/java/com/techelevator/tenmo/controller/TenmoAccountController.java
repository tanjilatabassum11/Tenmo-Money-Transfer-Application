package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.accountDao;
import com.techelevator.tenmo.dao.jdbcAccountDao;
import com.techelevator.tenmo.model.account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/account")
@PreAuthorize("isAuthenticated()")
public class TenmoAccountController {

    private accountDao accountDao;

    public TenmoAccountController(accountDao accountDao) {
        this.accountDao = accountDao;
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public account get(@PathVariable int id){
        account account = accountDao.findAccountByAccountId(id);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }

}
