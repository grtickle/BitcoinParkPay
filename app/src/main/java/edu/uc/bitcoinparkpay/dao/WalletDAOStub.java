package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 */
public class WalletDAOStub implements IWalletDAO{

    @Override
    public String createWallet( String walletName ) throws Exception {
        if ( walletName == null) {
            throw new Exception("Error: No walletName given");
        }

        return "b5f34ea7-4e2f-493b-bf6f-75c9ea2dea1f";
    }

    @Override
    public BigDecimal getBitcoinBalance( String bitcoinAddress ) throws Exception {
        BigDecimal balance = new BigDecimal("0.001");
        return balance;
    }


    @Override
    public BigDecimal getBitcoinPrice() throws Exception {
        BigDecimal price = new BigDecimal(250.00);
        return price;
    }

    @Override
    public void send( String walletPassword, BigDecimal amount, String recipientAddress, String fromAddress, BigDecimal fee ) throws Exception {
        if ( walletPassword == null || amount == null || recipientAddress == null || fromAddress == null || fee == null) {
            throw new Exception ("Error: missing parameter");
        }
    }

    @Override
    public void save(String label) throws Exception {

    }

    @Override
    public BigDecimal getNetworkFee() throws Exception {
        BigDecimal fee = new BigDecimal( 0.0001);
        return fee;
    }
}
