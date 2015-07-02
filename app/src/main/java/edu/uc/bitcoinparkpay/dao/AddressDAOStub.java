package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 */
public class AddressDAOStub implements IAddressDAO {

    @Override
    public void createAddress(String label) throws Exception {
        if ( label == null) {
            throw new Exception("Error: No label given");
        }
    }


    @Override
    public String getAddress ( String label ) throws Exception {
        String address = "90e9cf87vc6v909s9sd98v7vb8ds9s";
        return address;
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

        if ( apiKey == null || amount == null || to == null || pin == null) {
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
