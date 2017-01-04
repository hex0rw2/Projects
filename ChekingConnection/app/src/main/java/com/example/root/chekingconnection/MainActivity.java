package com.example.root.chekingconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ConStatus.getInstance(this).isOnline()) {

            Toast.makeText(this,"You are online!!!!",Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_SHORT).show();
            Log.v("Home", "############################You are not online!!!!");
        }

        Thread thread1 = new Thread() {
            public void run() {
                for(int i=1; i<=10;i++){
                    System.out.println();
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                new Downloader().downloadFromConstructedUrl("http:xxxxx",
                        new File("./references/word1.txt"),
                        new File("./references/words1.txt"));
            }
        };

// Start the downloads.
        thread1.start();
        thread2.start();

// Wait for them both to finish
        thread1.join();
        thread2.join();
    }
}
