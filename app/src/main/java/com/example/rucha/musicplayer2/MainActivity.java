package com.example.rucha.musicplayer2;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    Button play,pause;
    SeekBar seekBar;
    TextView tv;
    MediaPlayer mp;
    android.os.Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.play);
        pause=(Button)findViewById(R.id.pause);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        tv=(TextView)findViewById(R.id.text);
        mp=MediaPlayer.create(MainActivity.this,R.raw.song1);
        seekBar.setMax(mp.getDuration());
        mHandler=new android.os.Handler();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                ThreadClass th=new ThreadClass();
                th.start();
                // mHandler.post(th);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp!=null) {
                    mp.pause();
                }
                else{
                    Toast.makeText(MainActivity.this,"Play a song first!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mp.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
                mp.start();
            }
        });


    }
    Runnable th=new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mp.getCurrentPosition());
            mHandler.postDelayed(th,1000);
        }
    };
    class ThreadClass extends Thread{
        @Override
        public void run() {
            mHandler.post(th);
        }
    }


}

