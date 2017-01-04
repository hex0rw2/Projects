package com.example.root.callrecorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;

import java.io.IOException;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder;
    private String ofile = null;
    BRecorder  rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rec=new BRecorder();

        TelephonyManager telephonyManager =
                (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);



        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber)
            {
                if(state==TelephonyManager.CALL_STATE_RINGING || state==TelephonyManager.CALL_STATE_OFFHOOK){
                    rec.start();
                    Toast.makeText(getApplicationContext(),"Phone Is Currently in a call",
                            Toast.LENGTH_LONG).show();
                }
                if(state==TelephonyManager.CALL_STATE_IDLE) {

                    rec.stop();
                    Toast.makeText(getApplicationContext(), "phone is neither ringing nor in a call",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);




    }

}
