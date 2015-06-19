package edu.uc.bitcoinparkpay.service;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/18/2015.
 */
public class WalletServiceStub implements IWalletService{

    @Override
    public BigDecimal getDollarBalance() {
        BigDecimal balance = new BigDecimal(25.00);
        return balance;
    }
}
