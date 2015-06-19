package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 5/31/2015.
 */
public interface IWalletDAO {

    /**
     * Creates a wallet
     * @param label the name of the wallet
     * @throws Exception network problems
     */
    public void createWallet( String label ) throws Exception;

    /**
     * Gets balance of wallet in bitcoins
     * @param label the name of the wallet
     * @return
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinBalance( String label ) throws Exception;

    /**
     * Gets bitcoin price
     * @return
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinPrice() throws Exception;

    /**
     * Sends bitcoin to another wallet address
     * @param apiKey the account API Key
     * @param amount of bitcoin to send
     * @param to The wallet address to send to
     * @param pin Account pin number
     * @throws Exception network problems
     */
    public void send( String apiKey, BigDecimal amount, String to, String pin ) throws Exception;

    /**
     * Saves the wallet information used to access the wallet
     * @param label the name of the wallet
     * @throws Exception network problems
     */
    public void save( String label) throws Exception;
}
