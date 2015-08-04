package edu.uc.bitcoinparkpay;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.DBHelper;
import edu.uc.bitcoinparkpay.dto.Address;
import edu.uc.bitcoinparkpay.dto.ScanData;
import edu.uc.bitcoinparkpay.service.AddressService;

/**
 * Created by Greg on 8/1/2015.
 */
public class ConfirmationActivity extends ActionBarActivity {

    private ScanData scanData;
    private Address address;
    private DBHelper dbHelper;
    private AddressDAO addressDAO;
    private double bitcoinPrice;
    private static final String PIN = "10293847";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        address = new Address();
        dbHelper =  new DBHelper(this);
        addressDAO = new AddressDAO();
        try {
            bitcoinPrice = addressDAO.getBitcoinPrice().doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Load ScanData object
        scanData = (ScanData) getIntent().getSerializableExtra("ScanData");

        loadAddress();
        loadLabels();
    }

    public void loadAddress(){
        //if there is an address, load address into DTO.
        Cursor cursor = dbHelper.getData(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, 1);
        cursor.moveToFirst();

        // populate the address object.
        address.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_ID)));
        address.setAddress(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_ADDRESS)));
        address.setAddressLabel(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_LABEL)));
        //address.setBitcoinBalance(cursor.getDouble(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_BALANCE)));
        //address.setDollarBalance(cursor.getDouble(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_DOLLAR_BALANCE)));

        cursor.close();
    }

    public void loadLabels(){

        //Address
        TextView txtAddress = (TextView) findViewById(R.id.txtAddress);
        String address = "";
        try {
            address = scanData.getAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtAddress.setText(address);

        //Amount
        TextView txtAmount = (TextView) findViewById(R.id.txtAmount);
        double amount = 0.0;
        try {
            amount = scanData.getAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }

       // txtAmount.setText("" + amount);
        //Format amount to $ + dollars + . + cents
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        txtAmount.setText("" + formatter.format(amount));
    }

    public void GoToNotificationOnClicked(View v) throws Exception {

        //Make Payment
        AddressService addressService = new AddressService();
        //Convert amount from dollars to bitcoin
        double amountBitcoin = (scanData.getAmount())/(bitcoinPrice);
        DecimalFormat df = new DecimalFormat("#.00000000");
        df.format(amountBitcoin);
        addressService.makePayment(amountBitcoin,address.getAddressLabel(),scanData.getAddress(),PIN);

        //Update database with new balance
        dbHelper.updateBalance(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, 1, addressDAO.getBitcoinBalance(address.getAddressLabel()).doubleValue(),
                addressService.getDollarBalance(address.getAddressLabel()));

        //Go to notification activity
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public void ReturnToHomeOnClicked(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
