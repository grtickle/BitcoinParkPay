package edu.uc.bitcoinparkpay.dao;

import android.accounts.NetworkErrorException;
import android.util.Log;

import org.apache.http.client.HttpResponseException;

import java.math.BigDecimal;

/**
 * Created by Greg on 6/4/2015.
 * This class interacts with the bitcoin wallet hosted on block.io. These methods use API calls to
 * get wallet information such as addresses, balance, and network fee estimates. The send method
 * tells block.io to send bitcoin from one address to another. The block.io wallet is accessed by
 * an API key that is sent in the URI.
 */
public class AddressDAO implements IAddressDAO {

    NetworkDAO networkDAO;

    @Override
    public void createAddress( String label ) throws Exception {
        if ( label == null ) {
            throw new Exception( "Error: No label given" );
        } else {
            try{
                //*******Pull api keys from database*******
                String apiKey = "d33a-68b8-59d4-ed27";
                networkDAO.send( "https://block.io/api/v2/get_new_address/?api_key=" + apiKey + "&label=" + label );
            } catch ( NetworkErrorException e ){
                Log.i( "ERROR:", "No network connection." );
            }
        }
    }


    @Override
    public String getAddress ( String label ) throws Exception {
        networkDAO = new NetworkDAO();

        //URI that returns address
        //*******Pull api keys from database*******
        String apiKey = "d33a-68b8-59d4-ed27";
        String uriAddress = "https://block.io/api/v2/get_address_by_label/?api_key=" + apiKey + "&label=" + label;

        String data = "";
        try{
            //Fetch address data from block.io
            data = networkDAO.fetch( uriAddress );
        } catch ( NetworkErrorException e ) {
            Log.i( "ERROR:", "No network connection." );
        }

        //Parse data and store balance in BigDecimal
        String lines[] = data.split("\\r?\\n");
        String address = lines[5].substring(13, 52);
        return address;
    }

    @Override
    public BigDecimal getBitcoinBalance( String label ) throws Exception {
        networkDAO = new NetworkDAO();

        //URI that returns balance
        //*******Pull api keys from database*******
        String apiKey = "d33a-68b8-59d4-ed27";
        String uriAddress = "https://block.io/api/v2/get_address_balance/?api_key=" + apiKey + "&labels=" + label;

        String data = "";
        try{
            //Fetch address data from block.io
            data = networkDAO.fetch( uriAddress );
        } catch ( NetworkErrorException e ) {
            Log.i( "ERROR: ", "No network connection." );
        }

        //Parse data and store balance in BigDecimal
        String lines[] = data.split("\\r?\\n");
        BigDecimal balance = new BigDecimal( lines[11].substring(31, 41));

        return balance;
    }


    @Override
    public BigDecimal getBitcoinPrice() throws Exception {
        networkDAO = new NetworkDAO();

        //URI that returns price
        //*******Pull api keys from database*******
        String apiKey = "3bb2-81fc-a60a-3e7c";
        String uriAddress = "https://block.io/api/v2/get_current_price/?api_key=" + apiKey + "&price_base=USD";

        String data = "";
        try{
            //Fetch address data from block.io
            data = networkDAO.fetch( uriAddress );
        } catch ( NetworkErrorException e ) {
            Log.i( "ERROR: ", "No network connection." );
        }

        //Parse data and store balance in BigDecimal
        BigDecimal price;
        String lines[] = data.split("\\r?\\n");
        try {
            price = new BigDecimal( lines[18].substring(19, 25));
            return price;
        } catch ( IndexOutOfBoundsException e) {
            Log.e("ERROR: ", "getBitcoinPrice()" + e);
            return price = new BigDecimal(0.0);
        }
    }

    @Override
    public void send( double amount, String fromLabel, String to, String pin ) throws Exception {
        networkDAO = new NetworkDAO();

        //*******Get "to" address from camera QR code scan*******
        //*******Pull api keys from database*******
        String apiKey = "d33a-68b8-59d4-ed27";
        String uriAddress = "https://block.io/api/v2/withdraw_from_labels/?api_key=" + apiKey + "&from_labels=" + fromLabel +
                "&to_addresses=" + to + "&amounts=" + amount + "&pin=" + pin;
        // "to" is a very poorly named variable. You should name this something meaningful.
        try{
            networkDAO.send(uriAddress);
        } catch ( NetworkErrorException e){
            Log.i( "ERROR: ", "No network connection." );
        }
    }

    @Override
    public void save(String label) throws Exception {

    }

    @Override
    public double getNetworkFee( double amount, String to ) throws Exception {
        networkDAO = new NetworkDAO();

        //URI that returns estimated network fee
        //*******Pull api keys from database*******
        String apiKey = "d33a-68b8-59d4-ed27";
        String uriAddress = "https://block.io/api/v2/get_network_fee_estimate/?api_key=" + apiKey + "&amounts="
                + amount + "&to_addresses=" + to;

        String data = "";
        double fee = 0.00001;
        try{
            //Fetch address data from block.io
            data = networkDAO.fetch(uriAddress);

            //Parse data and store balance in BigDecimal
            String lines[] = data.split("\\r?\\n");
            try {
                fee = Double.parseDouble(lines[18].substring(19, 25));
                return fee;
            } catch ( IndexOutOfBoundsException e) {
                Log.e("ERROR: ", "getBitcoinPrice()" + e);

            }
        } catch ( HttpResponseException e ) {
            Log.i("ERROR: ", "Not enough funds; AddressDAO");
            throw new NetworkErrorException("Insufficient funds.");
        }
        return fee;
    }
}
