package edu.uc.bitcoinparkpay.service;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/18/2015.
 */
public interface IAddressService {

    /**
     *
     * @return the account balance in USD.
     */
    public BigDecimal getDollarBalance( String label ) throws Exception;

    /**
     Sends bitcoin to another address; checks for balance before calling AddressDAO.send
     * @param apiKey the account API Key
     * @param amount of bitcoin to send
     * @param to The address to send to
     * @param pin Account pin number
     * @throws Exception network problems
     */
    public void makePayment(String apiKey, BigDecimal amount, String to, String pin) throws Exception;
}
