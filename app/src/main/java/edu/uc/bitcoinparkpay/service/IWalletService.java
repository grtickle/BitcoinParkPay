package edu.uc.bitcoinparkpay.service;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/18/2015.
 */
public interface IWalletService {

    /**
     *
     * @return the account balance in USD.
     */
    public BigDecimal getDollarBalance();
}
