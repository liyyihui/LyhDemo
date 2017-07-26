package com.example.mymis.lyhview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    CircularProgressView mpr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpr = (CircularProgressView) findViewById(R.id.tt2);
      mpr.setmProgresssMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
               for(int i = 0;i<100;i++){
                   try {
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   final int finalI = i;
                   mpr.post(new Runnable() {
                       @Override
                       public void run() {
                           mpr.setmProgresss(finalI);
                       }
                   });
               }
            }
        }).start();


    }
}
