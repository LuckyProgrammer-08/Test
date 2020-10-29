package com.appdevin.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

public class EggTimer {

    int mProgress = 0;
    TextView mTextView;

    public EggTimer(TextView textView) {

        mTextView = textView;
    }

    public void setValue(int progress){
        mProgress = progress;

        int minutes = progress / 60;
        int seconds = mProgress % 60;

        mTextView.setText(minutes + ":" + seconds);
    }

    public void runTimer(final MediaPlayer sound){

        new CountDownTimer(mProgress*1000,1000){
            @Override
            public void onTick(long l) {

                setValue((int) l/1000);

                PrimeThread p = new PrimeThread();
                p.setName("T1");
                p.start();


            }

            @Override
            public void onFinish() {
                sound.start();

                new CountDownTimer(4000, 1000){
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        sound.stop();
                    }
                }.start();


            }
        }.start();


    }




}


class PrimeThread extends Thread{


    @Override
    public void run() {
        try {
            Thread.sleep(4000);
            Log.i(currentThread().getName(), "run: Start sleeping go 4 seconds");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
