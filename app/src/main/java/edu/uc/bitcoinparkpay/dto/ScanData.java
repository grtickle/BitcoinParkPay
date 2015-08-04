package edu.uc.bitcoinparkpay.dto;

import java.io.Serializable;

/**
 * Created by Greg on 8/3/2015.
 */
public class ScanData implements Serializable{
    private String address;
    private double amount;

    public ScanData(String address, double amount) {
        this.address = address;
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
