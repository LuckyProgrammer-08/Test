package com.appdevin.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //TODO: Declare varibles
    TextView mTimer;
    SeekBar mTimeSeeker;
    Button mButton;
    EggTimer mEggTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimer = findViewById(R.id.textView);
        mButton = findViewById(R.id.button);
        mTimeSeeker = findViewById(R.id.seekBar);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();

        mTimer.setText("0.00");

        mTimeSeeker.setMin(0);
        mTimeSeeker.setMax(600);
        mTimeSeeker.setProgress(0);

        mEggTimer = new EggTimer(mTimer);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mTimeSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {



                mEggTimer.setValue(i);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEggTimer.runTimer(mediaPlayer);


            }
        });
    }
}
