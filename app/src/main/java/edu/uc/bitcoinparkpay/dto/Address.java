package edu.uc.bitcoinparkpay.dto;

import android.accounts.NetworkErrorException;
import android.util.Log;

import edu.uc.bitcoinparkpay.dao.NetworkDAO;

/**
 * Created by Greg on 6/29/2015.
 */
public class Address {

    private String addressLabel;

    NetworkDAO networkDAO;

    public Address(String label) throws Exception{
        this.addressLabel = label;

        networkDAO = new NetworkDAO();

        if ( label == null || label.isEmpty()) {
            throw new Exception( "Error: No label given" );
        } else {
            try{
                //*******Pull api keys from database*******
                networkDAO.send( "https://block.io/api/v2/get_new_address/?api_key=3bb2-81fc-a60a-3e7c&label=" + label );
            } catch ( NetworkErrorException e ){
                Log.i("tag", "Error: No network connection.");
            }
        }
    }

    public String getAddressLabel() {
        return addressLabel;
    }

}
