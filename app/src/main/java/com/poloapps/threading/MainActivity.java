package com.poloapps.threading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "tag123";
    private Button buttonStartThread;
    private Button buttonStopThread;
    private Handler mainHandler = new Handler();
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStartThread = findViewById(R.id.button_start);
        buttonStopThread = findViewById(R.id.button_stop);
    }

    public void startThread(View view){ // call by start button
        ExampleThread thread = new ExampleThread(10);
        thread.start(); // this will auto start in a background thread
        stopThread = false;
       //ExampleRunnable runnable = new ExampleRunnable(10);
        //new Thread(runnable).start();  //runnable.run() to execute directly on main thread
    }
    public void stopThread(View view){ // call by stop button
        stopThread = true;
    }

  class ExampleThread extends Thread{
        int seconds;
        ExampleThread(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {

            for (int i = 0; i < seconds; i++){
                if (stopThread) return;
                Log.d(TAG, "startThread: " + i);
                if(i == 5){
                  /*
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStopThread.setText("new STOP");
                        }
                    });

                     buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("56");
                        }
                    });*/
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class ExampleRunnable implements Runnable{
        int seconds;

        ExampleRunnable(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for (int i = 0; i < seconds; i++){
                if (stopThread) return;
                if(i == 5){
                     Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("newTExt");
                        }
                    });
                    buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("56");
                        }
                    });
                    
                     runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("runOnUIThread");
                        }
                    });
                }
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
