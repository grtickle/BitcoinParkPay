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
        // I'm a huge fan of domain driven design but - if you have to make it, make sure
        // this slow over-https mobile request is not hanging whole app UI
        addressDAO.createAddress(label);
    }

    public String getAddressLabel() {
        return addressLabel;
    }

}
