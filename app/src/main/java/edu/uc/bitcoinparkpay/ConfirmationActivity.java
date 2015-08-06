package edu.uc.bitcoinparkpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.DBHelper;
import edu.uc.bitcoinparkpay.dao.IAddressDAO;
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
    private IAddressDAO addressDAO;
    private double bitcoinPrice;
    private ProgressDialog progressBar;
    private static final String PIN = "10293847";
    private Boolean isFunds  = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        address = new Address();
        dbHelper =  new DBHelper(this);
        addressDAO = new AddressDAO();


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
        NetworkSyncTask networkSyncTask = new NetworkSyncTask();
        networkSyncTask.execute();

        if (isFunds == true){
            //Go to notification activity
            Intent intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
        }

    }

    public void ReturnToHomeOnClicked(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    class NetworkSyncTask extends AsyncTask<Void, Integer, Void> {

        //This flag says whether or not the doInBackground method throws an exception
        private Boolean flag = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

             // setup progress dialog
             progressBar = new ProgressDialog(ConfirmationActivity.this);
             progressBar.setCancelable(true);
             progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
             progressBar.setProgress(1);
             progressBar.setMax(100);
             progressBar.setMessage(getString(R.string.Progress_Bar_Message));
             progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",  new DialogInterface.OnClickListener() {

            @Override public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            dialog.dismiss();
            cancel(true);
            }
            });
             progressBar.show();

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {

                //Make Payment
                AddressService addressService = new AddressService();
                //Convert amount from dollars to bitcoin
                //double amountBitcoin = (scanData.getAmount())/(bitcoinPrice);
                double amountBitcoin = (scanData.getAmount())/(280.00);
                DecimalFormat df = new DecimalFormat("#.00000000");
                amountBitcoin = Double.valueOf(df.format(amountBitcoin));

                try{
                    addressService.makePayment(amountBitcoin,address.getAddressLabel(),scanData.getAddress(),PIN);

                    isFunds = true;

                } catch (IOException e) {
                    flag = true;
                }

                //Update database with new balance
                dbHelper.updateBalance(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, 1, addressDAO.getBitcoinBalance(address.getAddressLabel()).doubleValue(),
                        addressService.getDollarBalance(address.getAddressLabel()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            //Toast that says if funds are available
            if (flag == true) {
                Context context = getApplicationContext();
                CharSequence text = "Insufficient funds in account address.";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            progressBar.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }
    }
}
