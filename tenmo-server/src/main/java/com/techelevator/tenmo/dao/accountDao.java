package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.account;

import java.util.List;

public interface accountDao {

      account findAccountByAccountId(int accountId);
      boolean create(int user_id);
      boolean transfer(int fromUserId, int toUserId, double transferAmount);

      Transfer createTransfer(Transfer transfer);
      List<Transfer> getTransfersByUserId(int id);

      Transfer getTransferByTransferId(int id);

      public account findAccountByUserId(int id);
}
