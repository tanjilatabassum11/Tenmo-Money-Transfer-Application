package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.account;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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


    private account mapRowToAccount(SqlRowSet rs) {
        account account = new account();
        account.setAccount_id(rs.getInt("account_id"));
        account.setUser_id(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setUsername(rs.getString("username"));
        return account;
    }

}
