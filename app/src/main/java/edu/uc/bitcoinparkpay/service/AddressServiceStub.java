package edu.uc.bitcoinparkpay.service;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/18/2015.
 */
public class AddressServiceStub implements IAddressService {

    @Override
    public BigDecimal getDollarBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal(25.00);
        return balance;
    }

    @Override
    public void makePayment(double amount, String fromLabel, String to, String pin) throws Exception {

    }
}
