package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Exception.DaoException;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.dao.accountDao;
import com.techelevator.tenmo.dao.jdbcAccountDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/tenmo")
@PreAuthorize("isAuthenticated()")
public class TenmoAccountController {
    private accountDao accountDao;
    @Autowired
    private UserDao userDao;

    public TenmoAccountController(accountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userDao.listUsersForTransfer();
    }
    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
    public account get(@PathVariable int id){
        account account = accountDao.findAccountByAccountId(id);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/account/transfer", method = RequestMethod.PUT)
    public boolean transfer(@RequestBody @Valid Transfer transfer){
        try {
        Boolean successful = accountDao.transfer(transfer.getFromUserId(),
                transfer.getToUserId(),transfer.getTransferAmount());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transfer parameters.");
        }
        return true;
    }

}
