package edu.uc.bitcoinparkpay.dao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Greg on 5/31/2015.
 */


/**
 * Low level networking stuff, can
 * be used with several DAOs.
 * @author jonesbr
 * from https://github.uc.edu/jonesbr/15ssadvanced
 */
public class NetworkDAO {

    // Careful with deprecated because with some new library version could stop working
    public String fetch(String uri) throws Exception {

        // Http Client marries together a request and response.
        HttpClient httpClient = new DefaultHttpClient();

        // what URI do we want to receive?
        HttpGet httpGet = new HttpGet(uri);

        // handle the response
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        // access the URI and get back the return data.
        String returnData = null;

        returnData = httpClient.execute(httpGet, responseHandler);

        // return data
        return returnData;
    }

    public void send( String uri ) throws Exception {
        // Http Client marries together a request and response.
        HttpClient httpClient = new DefaultHttpClient();

        // what URI do we want to receive?
        HttpPost httpPost = new HttpPost(uri);

        // access the URI
        httpClient.execute(httpPost);
    }
}
