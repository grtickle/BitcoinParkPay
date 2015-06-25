package edu.uc.bitcoinparkpay.dao;

import java.math.BigDecimal;

/**
 * Created by Greg on 5/31/2015.
 */
public interface IWalletDAO {

    /**
     * I would try to use more descriptive params for your methods. If I was using this
     * DAO it would be difficult to understand what value I should use for the param
     * without reading the doc.
     */

    /**
     * Bitcoin wallets should use a unique identifier for access,
     * why not return the identifier of the newly created wallet?
     * :)
     */

    /**
     * Creates a wallet
     * @param walletName the name of the wallet
     * @return wallet identifier
     * @throws Exception network problems
     */
    public String createWallet( String walletName ) throws Exception;

    /**
     * Bitcoin wallets are only used for transactions.
     * This method should return the balance of an actual bitcoin address!
     */

    /**
     * Gets balance of a bitcoin address
     * @param bitcoinAddress the bitcoin address
     * @return the balance associated with the bitcoin address
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinBalance( String bitcoinAddress ) throws Exception;

    /**
     * Gets bitcoin price
     * @return current market price
     * @throws Exception network problems
     */
    public BigDecimal getBitcoinPrice() throws Exception;

    /**
     * Sends bitcoin to another wallet address
     * @param walletPassword the wallet password
     * @param amount of bitcoin to send
     * @param recipientAddress the recipient bitcoin address
     * @param fromAddress bitcoin address to send from
     * @param fee transaction fee in satoshi
     * @throws Exception network problems
     */
    public void send( String walletPassword, BigDecimal amount, String recipientAddress, String fromAddress, BigDecimal fee ) throws Exception;

    /**
     * Saves the wallet information used to access the wallet
     * @param label the name of the wallet
     * @throws Exception network problems
     */
    public void save( String label) throws Exception;

    /**
     * Gets network fee in bitcoin
     * @return fee
     * @throws Exception network problems
     */
    public BigDecimal getNetworkFee () throws Exception;
}

