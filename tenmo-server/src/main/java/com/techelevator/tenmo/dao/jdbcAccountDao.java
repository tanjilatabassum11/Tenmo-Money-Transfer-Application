package com.techelevator.tenmo.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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


}
