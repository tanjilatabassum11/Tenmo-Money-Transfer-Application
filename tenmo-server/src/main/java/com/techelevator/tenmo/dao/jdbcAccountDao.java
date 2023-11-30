package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.Exception.DaoException;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.account;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;

@Component
public class jdbcAccountDao implements accountDao{

    public jdbcAccountDao (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(int user_id) {


        //step 1
        boolean successful = false;

        //step 2
        String sql = "\n" +
                "INSERT INTO account(user_id,balance)\n" +
                "VALUES(?,'1000') RETURNING account_id;";

        //step 3
        try {
           int createAccountId = jdbcTemplate.queryForObject(sql, int.class, user_id);




        }catch (Exception ex){
            System.out.println("Something went wrong");
            return  false;
        }



        return true;


    }

    @Override
    public account findAccountByAccountId(int accountId) {
        account account = new account();
        String sql = "SELECT account.user_id, username, account_id, balance FROM account" +
                " JOIN tenmo_user ON account.user_id = tenmo_user.user_id" +
                " WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()){
            account = mapRowToAccount(results);
            return account;
        }
        return null;
    }

    public account findAccountByUserId(int id){
        account account = new account();
        String sql = "SELECT account.user_id, tenmo_user.username, account_id, balance FROM account" +
                " JOIN tenmo_user ON tenmo_user.user_id = account.user_id" +
                " WHERE account.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            account = mapRowToAccount(results);
            return account;
        }
        return null;
    }

    @Override
    public boolean transfer(int fromUserId, int toUserId, double transferAmount){
        String sqlFrom = "UPDATE account SET balance = (SELECT balance FROM account WHERE user_id = ?) - ? WHERE user_id = ?;";
        String sqlTo = "UPDATE account SET balance = (SELECT balance FROM account WHERE user_id = ?) + ? WHERE user_id = ?;";
        account userAccount = findAccountByUserId(fromUserId);
        if (userAccount.getBalance() < transferAmount){
            throw new DaoException("Balance was less than transfer amount.");
        }
        if (fromUserId == toUserId) {
            throw new DaoException("Cannot send money to yourself.");
        }
        try{
            int numberOfRows1 = jdbcTemplate.update(sqlFrom, fromUserId, transferAmount, fromUserId);
            int numberOfRows2 = jdbcTemplate.update(sqlTo, toUserId, transferAmount, toUserId);
            if (numberOfRows1 == 0 && numberOfRows2 == 0) {
                throw new DaoException("No account with that user id was found, try again");
            } else {
                return true;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data Integrity Violation", e);
        }
    }


    private account mapRowToAccount(SqlRowSet rs) {
        account account = new account();
        account.setAccount_id(rs.getInt("account_id"));
        account.setUser_id(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setUsername(rs.getString("username"));
        return account;
    }

}
