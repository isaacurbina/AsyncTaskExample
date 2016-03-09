package com.mobileappsco.training.asynctaskexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    String message;
    Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            message = intent.getExtras().getString("KEY1");
        } catch (Exception e) {

        }
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        TimerTask myTimerTask = new TimerTask() {
            @Override
            public void run() {
                toastHandler.sendEmptyMessage(0);
            }
        };
        Timer myTimer = new Timer();
        myTimer.schedule(myTimerTask, 0, 5000);
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(getApplicationContext(), "Message: "+message, Toast.LENGTH_SHORT).show();
        }
    };
}
