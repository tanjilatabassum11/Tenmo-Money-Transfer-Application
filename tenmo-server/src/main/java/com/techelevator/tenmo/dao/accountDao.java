package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.account;

public interface accountDao {

      account findAccountByAccountId(int accountId);
      boolean create(int user_id);
      boolean transfer(int fromUserId, int toUserId, double transferAmount);
}
