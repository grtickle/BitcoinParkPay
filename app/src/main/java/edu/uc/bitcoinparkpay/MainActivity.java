package edu.uc.bitcoinparkpay;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.NumberFormat;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.DBHelper;
import edu.uc.bitcoinparkpay.dto.Address;
import edu.uc.bitcoinparkpay.dto.Key;
import edu.uc.bitcoinparkpay.dto.ScanData;
import edu.uc.bitcoinparkpay.service.AddressService;

public class MainActivity extends ActionBarActivity {
    private DBHelper mydb;
    private AddressService addressService;
    private Address address;
    private AddressDAO addressDAO;
    private ProgressDialog progressBar;
    private Key key;
    private Boolean isNetwork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address = new Address();
        addressDAO = new AddressDAO();
        addressService = new AddressService();
        key = new Key();

        //Initialize database
        mydb = new DBHelper(this);


        //Initialize an address; this makes a network call, so the initializeAddress
        //method runs in another thread.
        //Also, this class updates the TextView to show the address balance in the
        //onPostExecute method
        LoaderSyncTask loader = new LoaderSyncTask();
        loader.execute();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnTakePhotoOnClicked(View v){

        if (isNetwork == true){
            //This method calls the ZXing barcode scanner when the button is clicked; the result is
            //handled in the onActivityResult method below.
            IntentIntegrator.initiateScan(this);
        }else {
            Context context = getApplicationContext();
            CharSequence text = "Network connection not available.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            finish();
            startActivity(getIntent());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {

            //Get data from scan and separate address from amount and put into ScanData object to
            //be passed into the next activity
            String[] dataArray = scanResult.getContents().split(",");
            ScanData scanData = new ScanData(dataArray[0], Double.parseDouble(dataArray[1]));

            //Intent to go to the confirmation page after the picture is taken
            Intent intentConfirmation = new Intent(this, ConfirmationActivity.class);
            intentConfirmation.putExtra("ScanData", scanData);
            startActivity(intentConfirmation);
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Invalid scan; please try again.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void initializeKeys() {
        //if there are keys, load keys into DTO.
        Cursor cursor = mydb.getData(DBHelper.InfoEntry.TABLE_NAME_KEYS, 1);

        if (cursor.getCount() >= 1) {
            // if we are here, we have exactly one result.
            cursor.moveToFirst();

            // populate the address object.
            key.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_KEYS_ID)));
            key.setApiKey(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_KEYS)));
            key.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_KEYS_DESCRIPTION)));

            cursor.close();
        } else {
            //If there are no keys on file, create them

            //Set key values
            key.setId(1);
            key.setApiKey("d33a-68b8-59d4-ed27");
            key.setDescription("TESTNET");

            //Store key data in database
            ContentValues values = new ContentValues();
            values.put(DBHelper.InfoEntry.COLUMN_NAME_KEYS_ID, key.getId());
            values.put(DBHelper.InfoEntry.COLUMN_NAME_KEYS, key.getApiKey());
            values.put(DBHelper.InfoEntry.COLUMN_NAME_KEYS_DESCRIPTION, key.getDescription());
            mydb.insertInfo(DBHelper.InfoEntry.TABLE_NAME_KEYS, values);

            cursor.close();
        }
    }

    public void setBalanceAmount(){

        TextView txtViewBalance = (TextView) findViewById(R.id.txtViewBalance);
        String balance = "";
        try {
            balance = "" + address.getBitcoinBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtViewBalance.setText(balance);

        TextView txtDollarBalance = (TextView) findViewById(R.id.txtDollarBalance);

        //Format balance to $ + dollars + . + cents
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        txtDollarBalance.setText("" + formatter.format(address.getDollarBalance()));
    }

    public void initializeAddress() throws Exception{
        //This app will use only one address for now to provide basic functionality.
        //In future sprints, multiple users could be added

        //if there is an address, load address into DTO.
        Cursor cursor = mydb.getData(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, 1);

        if (cursor.getCount() >= 1) {
            // if we are here, we have exactly one result.
            cursor.moveToFirst();

            try{
                // populate the address object.
                address.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_ID)));
                address.setAddress(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_ADDRESS)));
                address.setAddressLabel(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_LABEL)));
                address.setBitcoinBalance(addressDAO.getBitcoinBalance(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_LABEL))).doubleValue());
                address.setDollarBalance(addressService.getDollarBalance(cursor.getString(cursor.getColumnIndex(DBHelper.InfoEntry.COLUMN_NAME_LABEL))));

                isNetwork = true;

                //Update database with new balance
                mydb.updateBalance(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, 1, address.getBitcoinBalance(), address.getDollarBalance());

            }catch (NetworkErrorException e){
                e.printStackTrace();
                isNetwork = false;
            }


            cursor.close();
        } else {
            try{
                //If there is not an address on file, create one

                //Create address
                addressDAO.createAddress("MAIN");
                //Get long address from block.io
                String addressLongForm = addressDAO.getAddress("MAIN");

                //Store address data in database
                ContentValues values = new ContentValues();
                values.put(DBHelper.InfoEntry.COLUMN_NAME_ADDRESS, addressLongForm);
                values.put(DBHelper.InfoEntry.COLUMN_NAME_LABEL,"MAIN");
                values.put(DBHelper.InfoEntry.COLUMN_NAME_BALANCE, 0.0);
                values.put(DBHelper.InfoEntry.COLUMN_NAME_DOLLAR_BALANCE, 0.00);
                mydb.insertInfo(DBHelper.InfoEntry.TABLE_NAME_ADDRESSES, values);

                //Set address values
                address.setAddressLabel("MAIN");
                address.setAddress(addressLongForm);
                address.setBitcoinBalance(0.0);
                address.setDollarBalance(0.00);

                isNetwork = true;

                cursor.close();

            } catch ( NetworkErrorException e) {
                Log.e("ERROR: ", "Error in initializeAddress");
                e.printStackTrace();

                isNetwork = false;

                cursor.close();
            }
        }
    }

    class LoaderSyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

             // setup progress dialog
             progressBar = new ProgressDialog(MainActivity.this);
             progressBar.setCancelable(true);
             progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
             progressBar.setProgress(1);
             progressBar.setMax(100);
             progressBar.setMessage(getString(R.string.Loading_Data_Message));
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
                initializeAddress();

                //Insert API keys into database and into Key DTO
                initializeKeys();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            //Toast that says if network is available
            if (isNetwork == false) {
                Context context = getApplicationContext();
                CharSequence text = "Network connection not available.";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            //Set balance label
            setBalanceAmount();

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
