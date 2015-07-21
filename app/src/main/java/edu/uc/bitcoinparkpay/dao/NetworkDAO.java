package edu.uc.bitcoinparkpay.dao;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public String fetch(String uri) throws Exception {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            String response = null;

            response = readIt(inputStream);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            urlConnection.disconnect();
        }
    }

    public void send(String uri) throws Exception {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            String response = null;

            response = readIt(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            urlConnection.disconnect();
        }
    }

    /*
     * Reads an InputStream and converts it to a String.
     * @param stream the data stream.
     * @return
     * @throws IOException
     */
    public String readIt(InputStream stream) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }
}
