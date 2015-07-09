package edu.uc.bitcoinparkpay.service;

import java.math.BigDecimal;

import edu.uc.bitcoinparkpay.dao.AddressDAO;

/**
 * Created by Greg on 6/20/2015.
 */
public class AddressService implements IAddressService {

    AddressDAO addressDAO;

    @Override
    public BigDecimal getDollarBalance( String label ) throws Exception {
        addressDAO = new AddressDAO();

        //Multiply the bitcoin balance by the bitcoin price to get balance in USD
        BigDecimal balance = addressDAO.getBitcoinBalance( label ).multiply(addressDAO.getBitcoinPrice());

        return balance;
    }

    @Override
    public void makePayment( double amount, String fromLabel, String to, String pin ) throws Exception {
        addressDAO = new AddressDAO();
        BigDecimal amountBD = new BigDecimal(amount);
        //Compare the address balance + network fee with the amount to be sent
        int flag = addressDAO.getBitcoinBalance( fromLabel ).add(addressDAO.getNetworkFee( amountBD, to )).compareTo( amountBD );

        //Check for sufficient funds in balance
        if ( flag == 0 || flag == 1 ){
            //Send funds
            addressDAO.send( amount, fromLabel, to, pin );
        } else {
            throw new Exception("Insufficient funds.");
        }
    }
}
