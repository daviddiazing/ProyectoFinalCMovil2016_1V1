package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.os.Handler;


import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import com.ebookfrenzy.proyfinalcmovil2016_1v1.BoundService.MyLocalBinder;


public class SplashScreen extends AppCompatActivity {

	private ProgressBar mProgress;
	private int mProgressStatus = 0;
	private Handler mHandler = new Handler();

    BoundService myService;//gps
    boolean isBound = false;//gps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setContentView(R.layout.splash_screen);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = new Intent(this, BoundService.class);//levantar servicio gps
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);//levantar servicio gps

        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                    mProgressStatus ++;
                    android.os.SystemClock.sleep(40); // Thread.sleep() doesn't guarantee 1000 msec sleep, it can be interrupted before
                }

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }//fin de run
        }).start();

 
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

    //conexión para el gps
    private ServiceConnection myConnection = new ServiceConnection()
    {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };
}
