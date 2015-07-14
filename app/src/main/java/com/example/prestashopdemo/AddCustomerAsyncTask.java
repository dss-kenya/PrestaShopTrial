package com.example.prestashopdemo;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 14/07/2015.
 */
public class AddCustomerAsyncTask extends AsyncTask<Void,Void,Void>{
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    private HttpURLConnection urlConnection;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //connectionTypeOne();
        connectionTypeTwo();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return parametersAsQueryString.toString();
    }

    private String getResponseText(InputStream is) {
        String line = "";
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    private String connectionTypeOne() {
        try {
            // YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U@

            final String username  = "YTANJPUUB5SJTD3E6XKYWQDA9CZT";
            final String password = "";// leave it empty

            //String urlStr = "http://grood.in/api/customers/?ws_key=YTANJPUUB5SJTD3E6XKYWQDA9CZT";
            //String urlStr = "http://grood.in/api/customers?ws_key=YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U";

            String urlStr = "http://YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U@grood.in/api/customers/";
            String postParameters = "";

            /*Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });*/

            URL urlToRequest = new URL(urlStr);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type",
                    "text/xml;charset=utf-8");
            //urlConnection.setRequestProperty("Accept", "application/XML");


            String authToBytes = username + ":" + password;
            //....

            //byte[] authBytes = org.apache.commons.codec.binary.Base64.encodeBase64(authToBytes.getBytes());
            //String authBytesString =  new String(authBytes);
            //then your code

            //urlConnection.setRequestProperty("ws_key",username);

            // set the parameters as per the schema
            // get schema from
            // https://grood.in/api/customers/?schema=synopsis
            /*Map<String,String> map = new HashMap<>();
            map.put("passwd","2a3b068284585f4bd212d301af7cc0b7");
            map.put("lastname","xyz");
            map.put("firstname","abc");
            map.put("email","abc@xyz.com");
            //map.put("ws_key",username);
            postParameters = createQueryStringForParameters(map);
            urlConnection.setFixedLengthStreamingMode(
                    postParameters.getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(postParameters);
            out.close();*/

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                // throw some exception

                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                Log.e("dhara", getResponseText(in));
            }

            // read output (only for GET)
            /*if (postParameters != null) {
                return null;
            } else {
                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                Log.e("dhara", getResponseText(in));
            }*/
        }catch (ProtocolException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    String connectionTypeTwo(){
        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        // The default value is zero, that means the timeout is not used.
        int timeoutConnection = 30000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 30000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        // set the params in the get method
        int counter = 0;
        /*if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                counter ++;
                if(counter == 1) {
                    url = url + QUESTION + entry.getKey() + EQUAL + entry.getValue();
                }else {
                    url = url + AMPERSAND + entry.getKey() + EQUAL + entry.getValue();
                }
            }
        }*/


        HttpGet httpGet = new HttpGet("http://YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U@grood.in/api/customers/");
        httpGet.setHeader("Authorization","Basic " +
                Base64.encode(new String("YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U").getBytes(),Base64.DEFAULT));
        try {
            HttpResponse response = httpclient.execute(httpGet);
            String strResponse = EntityUtils.toString(response.getEntity());
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return strResponse;
            }
            return strResponse;
        }catch(ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
