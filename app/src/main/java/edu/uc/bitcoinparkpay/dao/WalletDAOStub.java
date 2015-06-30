package edu.uc.bitcoinparkpay.dao;

import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import 	android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Greg on 6/4/2015.
 */
public class WalletDAOStub implements IWalletDAO{

    @Override
    public void createWallet(String label) throws Exception {
        if ( label == null) {
            throw new Exception("Error: No label given");
        }
    }

    @Override
    public BigDecimal getAddressBalance(String label) throws Exception {
        BigDecimal balance = new BigDecimal("0.00001");
        return balance;
    }

    @Override
    public void send(String apiKey, BigDecimal amount, String to, String pin) throws Exception {

        if ( apiKey == null || amount == null || to == null || pin == null) {
            throw new Exception ("Error: missing parameter");
        }

    }

    @Override
    public void save(String label) throws Exception {
        //save wallet names and the transaction log
        //if (label == null) {
        //throw new exception("Error: Wallet name not found")
        // Maybe give option to createWallet of this name if the name isn't found
        //}
        //else if(label != null){
        // save wallet name
            // return label
        //  save transaction log
        //}
    }
}
