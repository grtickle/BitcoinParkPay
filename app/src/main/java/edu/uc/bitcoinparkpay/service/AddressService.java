package edu.uc.bitcoinparkpay.service;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.IAddressDAO;

/**
 * Created by Greg on 6/20/2015.
 */
public class AddressService implements IAddressService {

    private IAddressDAO addressDAO = new AddressDAO();

    @Override
    public double getDollarBalance( String label ) throws Exception {

        try{
            //Multiply the bitcoin balance by the bitcoin price to get balance in USD
            double balance = addressDAO.getBitcoinBalance( label ).doubleValue() * (addressDAO.getBitcoinPrice().doubleValue());

            return balance;
        }catch (Exception e){
            throw new NetworkErrorException("No network connection");
        }

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
            Log.e("ERROR: ", "Not enough funds; AddressService");
            throw new IOException("Insufficient funds.");
        }
    }
}
