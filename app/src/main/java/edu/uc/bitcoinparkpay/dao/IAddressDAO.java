package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 5/31/2015.
 */
public interface IAddressDAO {

    /**
     * Creates a an address
     * @param label the name of the address
     * @throws Exception network problems
     */
    public void createAddress(String label) throws Exception;

    /**
     * Gets the bitcoin address from the wallet
     * @param label the name of the address
     * @return
     * @throws Exception
     */
    public String getAddress (String label) throws Exception;

    /**
     * Gets balance of address in bitcoins
     * @param label the label for the address
     * @return
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinBalance(String label) throws Exception;

    /**
     * Gets bitcoin price
     * @return
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinPrice() throws Exception;

    /**
     * Sends bitcoin to another wallet address
     * @param amount of bitcoin to send
     * @param fromLabel The address to send from; refer to address by label
     * @param to The wallet address to send to
     * @param pin Account pin number
     * @throws Exception network problems
     */
    public void send(double amount, String fromLabel, String to, String pin) throws Exception;

    /**
     * Saves the wallet information used to access the wallet
     * @param label the name of the address
     * @throws Exception network problems
     */
    public void save(String label) throws Exception;

    /**
     * Gets network fee in bitcoin
     * @param amount The bitcoin being sent
     * @param to The address being sent to
     * @return fee
     * @throws Exception network problems
     */
    public double getNetworkFee (double amount, String to) throws Exception;
}

