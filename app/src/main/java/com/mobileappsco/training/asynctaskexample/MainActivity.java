package com.mobileappsco.training.asynctaskexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    TextView textView1;
    ProgressBar progressBar1;
    int counter = 0;
    ArrayList<MyAsyncTask> mytasks = new ArrayList<MyAsyncTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        textView1 = (TextView) findViewById(R.id.textView1);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setMax(100);
        Intent i= new Intent(this, MyService.class);
        i.putExtra("KEY1", "Value to be used by the service");
        startService(i);
    }

    @Override
    public void onClick(View v) {
        MyAsyncTask mytask = new MyAsyncTask();
        mytasks.add(counter, mytask);
        counter++;
        //mytask.execute(counter);
        mytask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, counter);
        textView1.setText(textView1.getText()+"\n"+"STARTED "+counter);
        // use this to start and trigger a service
        Intent i= new Intent(this, MyService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        startService(i);
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Integer... x) {
            Log.d("MYTAG", "doInBackground("+x[0].toString()+")");
            String response = "FINISHED "+x[0];
            try {
                for (int i = 0; i <= 100; i+=1) {
                    //response += String.valueOf(i);
                    Thread.sleep(1);
                    publishProgress(i);
                    //Log.d("MYTAG", "publishProgress("+i+")");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            Log.d("MYTAG", "onPreExecute()"+counter);
            /*progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Loading...");*/
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("MYTAG", "onPostExecute("+result+")");
            // execution of result of Long time consuming operation
            //progressDialog.dismiss();
            textView1.setText(textView1.getText()+"\n"+result);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress[0]);
            //textView1.setText(progress[0].toString());
            progressBar1.setProgress(progress[0]);
        }

    }


}
