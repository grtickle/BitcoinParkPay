package edu.uc.bitcoinparkpay.test1;

import android.util.Log;

import junit.framework.TestCase;

import edu.uc.bitcoinparkpay.dao.AddressDAO;
import edu.uc.bitcoinparkpay.dao.NetworkDAO;
import edu.uc.bitcoinparkpay.service.AddressService;

/**
 * Created by Greg on 5/31/2015.
 */
public class NetworkDAOTest extends TestCase{
    NetworkDAO networkDAO;
    AddressDAO addressDAO;
    AddressService addressService;


    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
        networkDAO = new NetworkDAO();
        addressService = new AddressService();
        addressDAO = new AddressDAO();
    }

    /**
     * Test scenarios:
     * 	Method testFecth:
     * 	Should return a balance string:
     */
/**
    public void testFetch() throws Exception{
        String apiCall = networkDAO.fetch("https://block.io/api/v2/get_balance/?api_key=020c-adac-3f68-f9dc");
        assertNotNull(apiCall);
        Log.i("tag", apiCall);
    }
**/
    /**
     * Test scenarios:
     * 	Method testSend:
     * 	Creates a new wallet:
     */
/**
    public void testSend() throws Exception{
        try{
            networkDAO.send("https://block.io/api/v2/get_new_address/?api_key=020c-adac-3f68-f9dc&label=Test");
        } catch ( NetworkErrorException e) {
            Log.i("tag", "test");
        }
    }
**/
    /**
     * Test scenarios:
     * 	Method testGetAddressBalance:
     * 	Should return a data string:
     */

/**
    public void testGetAddress() throws Exception{
        addressDAO = new AddressDAO();
        String address = addressDAO.getAddress("default");
        assertNotNull(address);
        Log.i("BALANCE: ", address);
    }
**/

/**
    public void testGetNetworkFee() throws Exception{
        addressDAO = new AddressDAO();

        double amount = .1;
        BigDecimal fee = new BigDecimal(String.valueOf(addressDAO.getNetworkFee(amount, "2N8o4HyRanaGSPmeve5qJwEuJK2fBo4CvCu")));
        assertNotNull(fee);
        Log.i("BALANCE: ", fee.toString());
    }
**/

/**
    public void testGetPrice() throws Exception{
        addressDAO = new AddressDAO();
        BigDecimal price = new BigDecimal(0.0);
        price = addressDAO.getBitcoinPrice();
        assertNotNull(price);
        Log.i("PRICE: ", price.toString());
    }
**/

/**
    public void testGetDollarBalance() throws Exception{
        addressService = new AddressService();
        BigDecimal balance = new BigDecimal(0.0);
        balance = addressService.getDollarBalance("test");
        assertNotNull(balance);
        Log.i("BALANCE: ", balance.toString());
    }
 **/

/**
public void testNetwork() throws Exception{
    String label = "test";
    String apiKey = "d33a-68b8-59d4-ed27";
    String uriAddress = "https://block.io/api/v2/get_address_balance/?api_key=" + apiKey + "&labels=" + label;

    String data = "";
    try{
        //Fetch address data from block.io
        data = networkDAO.fetch( uriAddress );
    } catch ( NetworkErrorException e ) {
        Log.i("tag", "Error: No network connection.");
    }
}
**/


    public void testMakePayment() throws Exception{

        double amount = .1;
        //BigDecimal amount = new BigDecimal(number);
        try{
            addressService.makePayment(amount, "test", "2MsssGRohNs4WgofjsxfCA2WzW6GrtYoLie", "10293847");
        } catch ( Exception e) {
            Log.i("MAKE PAYMENT: ", "FAILED");
        }
    }


}
