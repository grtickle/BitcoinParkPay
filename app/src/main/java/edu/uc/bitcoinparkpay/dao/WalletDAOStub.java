package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 */
public class WalletDAOStub implements IWalletDAO{

    @Override
    public void createWallet(String label) throws Exception {
        if ( label == null) {
            throw new Exception("Error: No label given");
        }
    }

    @Override
    public BigDecimal getBitcoinBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal("0.001");
        return balance;
    }

    @Override
    public BigDecimal getDollarBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal("25.00");
        return balance;
    }

    @Override
    public BigDecimal getBitcoinPrice() throws Exception {
        BigDecimal price = new BigDecimal("250.00");
        return price;
    }

    @Override
    public void send(String apiKey, BigDecimal amount, String to, String pin) throws Exception {

        if ( apiKey == null || amount == null || to == null || pin == null) {
            throw new Exception ("Error: missing parameter");
        }
    }

    @Override
    public void save(String label) throws Exception {

    }
}
