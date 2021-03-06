package edu.uc.bitcoinparkpay.service;

/**
 * Created by Greg on 6/18/2015.
 */
public interface IAddressService {

    /**
     *
     * @return the account balance in USD.
     */
    public double getDollarBalance( String label ) throws Exception;

    /**
     Sends bitcoin to another address; checks for balance before calling AddressDAO.send
     * @param amount of bitcoin to send
     * @param fromLabel The address to send from; refer to address by label
     * @param to The address to send to
     * @param pin Account pin number
     * @throws Exception network problems
     */
    public void makePayment(double amount, String fromLabel, String to, String pin) throws Exception;
}
