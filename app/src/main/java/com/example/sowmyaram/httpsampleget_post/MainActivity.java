package com.example.sowmyaram.httpsampleget_post;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView tv, tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView2);
        Button b = (Button) findViewById(R.id.button3);
        Button b1 = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating thread to handle http request
                Thread t = new Thread() {

                    public void run() {

                        postt();
                    }
                };
                t.start();

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating thread to handle http request
                Thread t = new Thread() {

                    public void run() {

                        makeGetRequest();
                    }
                };
                t.start();

            }
        });

    }


    //for HTTP POST
    void postt() {
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost(
                "http://testgprs.edisonbro.in/post.php");

        // Building post parameters
        // key and value pair
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("data", "Edisonbro123"));
        nameValuePair.add(new BasicNameValuePair("data",
                "Hi, trying Android HTTP post!"));

        // Url Encoding the POST parameters
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        // Making HTTP Request
        try {
            final HttpResponse response = httpClient.execute(httpPost);
            String responseBody = null;

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseBody = EntityUtils.toString(entity);

                JSONObject j = new JSONObject(responseBody);
                responseBody = j.getString("data");

                Log.d("TAG", "Http post Response: " + responseBody);
            }
            Log.d("TAG", responseBody);

            final String finalResponseBody = responseBody;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(finalResponseBody);
                }
            });
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();
        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //for HTTP GET method
    private void makeGetRequest() {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://testgprs.edisonbro.in/get.php?data=sowmya1234");


        HttpResponse response;
        try {
            response = client.execute(request);
            String responseBody = null;
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                responseBody = EntityUtils.toString(entity);
                Log.d("TAG", "Http get json Response: " + responseBody);

                //creating JSON Object
                JSONObject j = new JSONObject(responseBody);
                responseBody = j.getString("data");
                Log.d("TAG", "Http get Response: " + responseBody);
            }
            //Log.d("TAG", responseBody);


            final String finalResponseBody = responseBody;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    tv1.setText(finalResponseBody);

                }
            });

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}





