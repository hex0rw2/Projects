package com.example.root.callrecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AnswerCallBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {

        if(arg1.getAction().equals("android.intent.action.PHONE_STATE")){

            String state = arg1.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Log.d(TAG, "Inside Extra state off hook");
                String number = arg1.getStringExtra(TelephonyManager.EXTRA_PHONE_NUMBER);
                Log.e(TAG, "outgoing number : " + number);
            }

            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Log.e(TAG, "Inside EXTRA_STATE_RINGING");
                String number = arg1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.e(TAG, "incoming number : " + number);
            }
            else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Log.d(TAG, "Inside EXTRA_STATE_IDLE");
            }
        }
    }
}