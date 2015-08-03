package edu.uc.bitcoinparkpay.dto;

/**
 * Created by Greg on 6/29/2015.
 */
public class Address {

    // ID is unique only to this device.
    private int id;
    private String addressLabel;
    private String address;
    private double bitcoinBalance;
    private double dollarBalance;


    public int getId() {
        return id;
    }

    public void setId(int id) {this.id = id;}

    public String getAddressLabel() {
        return addressLabel;
    }

    public void setAddressLabel(String addressLabel) {
        this.addressLabel = addressLabel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBitcoinBalance() {
        return bitcoinBalance;
    }

    public void setBitcoinBalance(double bitcoinBalance) {
        this.bitcoinBalance = bitcoinBalance;
    }

    public double getDollarBalance() { return dollarBalance; }

    public void setDollarBalance(double dollarBalance) { this.dollarBalance = dollarBalance; }
}
