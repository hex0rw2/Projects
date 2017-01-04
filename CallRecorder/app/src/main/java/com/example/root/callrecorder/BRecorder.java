package com.example.root.callrecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class BRecorder extends AppCompatActivity {

    int count=0;
    MediaRecorder recorder=null;
    private String ofile=null;

    public BRecorder(){

    }

    private void prepare(){
        recorder = new MediaRecorder();
        ofile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myrec"+count+++".3gp";
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(ofile);
    }
    public void start(){
        try {
            prepare();
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void stop(){

          try {
              recorder.stop();
              recorder.release();
              recorder= null;
              Toast.makeText(this, "Recording Stoped And Successfull", Toast.LENGTH_SHORT).show();
          }
          catch (Exception e){

              e.printStackTrace();
          }

    }
    public void play()throws IOException{
        MediaPlayer m=new MediaPlayer();
        m.setDataSource(ofile);
        m.prepare();
        m.start();
        Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
    }

}
