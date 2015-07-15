package com.example.prestashopdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements IResponseListener{
    private TextView mTxtDetails;
    private ProgressBar mProgressBar;
    private IResponseListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtDetails = (TextView)findViewById(R.id.txtDetails);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mListener = this;
    }

    public void getList(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        new GetCustomerAsyncTask(mListener).execute();
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

    @Override
    public void onResponseReceived(Object object) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if(object != null) {
            if(object instanceof  String) {
                // we receive the response and set the text
                // for you to see
                String response = (String)object;
                mTxtDetails.setText(response);
            }
        }
    }
}
