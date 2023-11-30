package com.techelevator.tenmo.model;

public class account {
    private int account_id;
    private int user_id;
    private double balance;
    private String username = null;
    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public account(){ }

    public account(int user_id,int account_id, double balance){
        this.user_id = user_id;
        this.account_id = account_id;
        this.balance = balance;

    }


}
