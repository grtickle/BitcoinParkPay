package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 */
public class WalletDAOStub implements IWalletDAO{
/* It's just conceptual, so I understand, but wouldn't (myString != null && !myString.isEmpty())
 * be the best method to check for a null string? I might structure it to first find if it is not null,
 * then have it perform it's operation, and if it is, then throw the exception.
 */
    @Override
    public void createWallet(String label) throws Exception {
        if ( label !=null && !label.isEmpty()) {
            //call database
        } else {
            throw new Exception("Error: No label given");
        }
    }

    @Override
    public BigDecimal getBitcoinBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal("0.001");
        return balance;
    }


    @Override
    public BigDecimal getBitcoinPrice() throws Exception {
        BigDecimal price = new BigDecimal(250.00);
        return price;
    }

    @Override
    public void send(String apiKey, BigDecimal amount, String to, String pin) throws Exception {

        if ( apiKey !=null && !apiKey.isEmpty() || amount == null || to !=null && !to.isEmpty() || pin !=null && !pin.isEmpty()) {
            //do the action
        } else {
            throw new Exception("Error: missing parameter");
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
