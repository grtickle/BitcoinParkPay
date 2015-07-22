package edu.uc.bitcoinparkpay.service;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.math.BigDecimal;

import edu.uc.bitcoinparkpay.dao.AddressDAO;

/**
 * Created by Greg on 6/20/2015.
 */
public class AddressService implements IAddressService {

    AddressDAO addressDAO = new AddressDAO();;

    @Override
    public BigDecimal getDollarBalance( String label ) throws Exception {
        //Multiply the bitcoin balance by the bitcoin price to get balance in USD
        BigDecimal balance = addressDAO.getBitcoinBalance( label ).multiply(addressDAO.getBitcoinPrice());

        return balance;
    }

    @Override
    public void makePayment( double amount, String fromLabel, String to, String pin ) throws Exception {
        BigDecimal amountBD = new BigDecimal(amount);

        //Set flag to determine if sufficient funds; if 0 or 1, send; otherwise not enough funds
        int flag;
        try{
            //Get network fee; if not sufficient funds, will throw exception that will catch and
            //set flag to -1
            BigDecimal amountFee = new BigDecimal(addressDAO.getNetworkFee( amount, to ));

            //Compare the address balance + network fee with the amount to be sent
            flag = addressDAO.getBitcoinBalance( fromLabel).add(amountFee).compareTo( amountBD );
        } catch ( NetworkErrorException e){
            flag = -1;
        }


        //Check for sufficient funds in balance
        if ( flag == 0 || flag == 1 ){
            //Send funds
            addressDAO.send( amount, fromLabel, to, pin );
        } else {
            Log.i("ERROR: ", "Not enough funds; AddressService");
            throw new Exception("Insufficient funds.");
        }
    }
}
