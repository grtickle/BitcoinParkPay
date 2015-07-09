package edu.uc.bitcoinparkpay.dto;

import edu.uc.bitcoinparkpay.dao.AddressDAO;

/**
 * Created by Greg on 6/29/2015.
 */
public class Address {


    private String addressLabel;

    AddressDAO addressDAO;

    public Address(String label) throws Exception{
        this.addressLabel = label;

        //Create new wallet
        addressDAO = new AddressDAO();
        addressDAO.createAddress(label);
    }

    public String getAddressLabel() {
        return addressLabel;
    }

}
