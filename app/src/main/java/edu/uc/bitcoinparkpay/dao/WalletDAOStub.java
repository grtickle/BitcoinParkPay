package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 */
public class WalletDAOStub implements IWalletDAO{
    @Override
    public void createWallet(String label) throws Exception {

    }

    @Override
    public BigDecimal getAddressBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal("0.00001");
        return balance;
    }

    @Override
    public void send(String apiKey, BigDecimal amount, String to, String pin) throws Exception {

    }
}
