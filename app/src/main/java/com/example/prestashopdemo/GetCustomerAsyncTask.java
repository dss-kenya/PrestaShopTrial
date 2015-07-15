package com.example.prestashopdemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by user on 14/07/2015.
 * http://stackoverflow.com/questions/21881678/parse-xml-in-android-from-prestashop-webservice
 * http://stackoverflow.com/questions/28724731/how-to-integrate-prestashop-with-android#
 */
public class GetCustomerAsyncTask extends AsyncTask<Void,Void,String>{
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    private HttpURLConnection urlConnection;
    private final String TAG = this.getClass().getSimpleName();
    private IResponseListener mListener;

    public GetCustomerAsyncTask(IResponseListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String response = performWebserviceCall();
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(mListener != null) {
            mListener.onResponseReceived(result);
        }
    }

    /**
     * Makes the webservice call and returns the response if there else null
     * It is a GET operation at the moment
     * Can be modified to accomodate a POST also
     * @return
     */
    private String performWebserviceCall() {
        try {
            final String username  = "YTANJPUUB5SJTD3E6XKYWQDA9CZT";
            final String password = "";// leave it empty

            // commenting these urls, might be needed sometime later on
            //String urlStr = "http://grood.in/api/customers?ws_key=YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U";
            //String urlStr = "http://YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U@grood.in/api/customers/";
            //String urlStr = http://grood.in/api/customers?ws_key=YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U
            //String urlStr = "http://grood.in/api/customers/";

            String urlStr = "http://grood.in/api/customers?ws_key=YTANJPUUB5SJTD3E6XKYWQDA9CZTEF4U";
            String postParameters = null;

            URL urlToRequest = new URL(urlStr);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type",
                    "text/xml;charset=utf-8");

            // Authorization commented since it is part of the url now
            //String authToBytes = username + ":" + password;
            //byte[] authBytes = org.apache.commons.codec.binary.Base64.encodeBase64(authToBytes.getBytes());
            //String authBytesString =  new String(authBytes);
            //urlConnection.setRequestProperty("Authorization","Basic " + authBytesString);

            // set the parameters as per the schema
            // get schema from
            // https://grood.in/api/customers/?schema=synopsis
            // commenting these since it is a get operation not a post
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

            // read output (only for GET)
            if (postParameters != null) {
                return null;
            } else {
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    InputStream in =
                            new BufferedInputStream(urlConnection.getInputStream());
                    String response = getResponseText(in);
                    return response;
                }
            }
        }catch (ProtocolException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            // close the connection
            // whatever errors might have occurred
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /**
     * Gets the response as a string from InputStream
     * @param is
     * @return
     */
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
        return sb.toString();
    }

    /**
     * Forms the query string if there are parameters to post
     * @param parameters
     * @return
     */
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
}
