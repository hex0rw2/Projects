package com.example.root.audiorecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder;
    private String ofile = null;
    private Button start, stop, play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
        ofile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/myrec.3gp";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        //recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(ofile);
    }

    public void start(View v){
        try {
            recorder.prepare();
            recorder.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);

        Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
    }
    public void stop(View v){
        recorder.stop();
        recorder.release();
        recorder= null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(this, "Recording Stoped And Successfull", Toast.LENGTH_SHORT).show();
    }
    public void play(View v)throws IOException{
        MediaPlayer m=new MediaPlayer();
        m.setDataSource(ofile);
        m.prepare();
        m.start();
        Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
    }
}
