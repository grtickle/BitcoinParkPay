package edu.uc.bitcoinparkpay;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.DBHelper;
import edu.uc.bitcoinparkpay.dto.Address;
import edu.uc.bitcoinparkpay.service.AddressService;

public class MainActivity extends ActionBarActivity {
    //No more DBHelper
    private DBHelper mydb;
    private AddressService addressService;
    private Address address;
    private AddressDAO addressDAO;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        address = new Address();
        addressDAO = new AddressDAO();

        //Initialize database
        //No more DBHelper
        mydb = new DBHelper(this);

        //Initialize an address; this makes a network call, so the initializeAddress
        //method runs in another thread.
        //Also, this class updates the TextView to show the address balance in the
        //onPostExecute method
        LoaderSyncTask loader = new LoaderSyncTask();
        loader.execute();


        //addressService = new AddressService(this);
    }

    public void btnTakePhotoOnClicked(){
    Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    public void btnMakePaymentOnClicked(){
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }

    public void setBalanceAmount(){

        TextView txt = (TextView) findViewById(R.id.txtViewBalance);
        String balance = "";
        try {
            balance = "" + address.getBitcoinBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txt.setText(balance);
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



    public void initializeAddress() throws Exception{
        //This app will use only one address for now to provide basic functionality.
        //In future sprints, multiple users could be added

        //if there is an address, load address into DTO.
        //Calling upon non-existent function
        Cursor cursor = mydb.getData(1);

        if (cursor.getCount() >= 1) {
            // if we are here, we have exactly one result.
            cursor.moveToFirst();

            // populate the address object.
            address.setId(cursor.getInt(cursor.getColumnIndex("id")));
            address.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            address.setBitcoinBalance(addressDAO.getBitcoinBalance("MAIN").doubleValue());
        } else {
            try{
                //If there is not an address on file, create one

                //Create address
                addressDAO.createAddress("MAIN");

                //Store address data in database
                //Calling upon non-existent function
                mydb.insertInfo("MAIN", "d33a-68b8-59d4-ed27");

                //Set address values
                address.setAddressLabel("MAIN");
                address.setBitcoinBalance(0.0);

            } catch ( NetworkErrorException e) {
                Log.e("ERROR: ", "Error in initializeAddress");
                e.printStackTrace();
            }
        }
    }

    class LoaderSyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             // setup progress dialog
             progressBar = new ProgressDialog(MainActivity.this);
             progressBar.setCancelable(true);
             progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
             progressBar.setProgress(1);
             progressBar.setMax(100);
             progressBar.setMessage(getString(R.string.initializing_data));
             progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",  new DialogInterface.OnClickListener() {

            @Override public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            dialog.dismiss();
            cancel(true);
            }
            });
             progressBar.show();
             **/
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //progressBar.dismiss();

            //Set balance label
            setBalanceAmount();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }
    }
}
