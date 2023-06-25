package com.example.myapplication;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class setting extends Fragment {

    Button playBtton;
    SeekBar start,end;
    TextView startText,endText;
    MediaPlayer song;
    ImageView imageView;
    Animation animation;
    int SongTotaltime;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //To hide status bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.setting);


        //使用View绑定页面
        View view = inflater.inflate(R.layout.setting, container, false);

        //Id implementation
        playBtton = view.findViewById(R.id.play);
        startText = view.findViewById(R.id.TextStart);
        endText = view.findViewById(R.id.TextEnd);
        imageView = view.findViewById(R.id.img);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);

        //song Added
        song = MediaPlayer.create(getContext(), R.raw.song);
        song.setLooping(true);
        song.seekTo(0);
        song.setVolume(0.5f,0.5f);
        SongTotaltime = song.getDuration();

        //Control Seek bar track line / play line
        start = view.findViewById(R.id.PlayLine);
        start.setMax(SongTotaltime);
        start.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    song.seekTo(i);
                    start.setProgress(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Id implementation
        playBtton = view.findViewById(R.id.play);
        playBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayButton(v);
            }
        });

        //Volume control
        end = view.findViewById(R.id.volume);
        end.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float volume = i/100f;
                song.setVolume(volume,volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //up date song time line
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(song != null){
                    try{
                        Message message = new Message();
                        message.what = song.getCurrentPosition();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    }catch (InterruptedException ignored){

                    }
                }
            }
        }).start();

//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler =new Handler(){
        @SuppressLint("SetText18n")
        @Override
        public void handleMessage(Message message){
            int  SeekBarPosition = message.what;

            //Update song seek bar
            start.setProgress(SeekBarPosition);

            //Update labels
            String Time = createTimeText(SeekBarPosition);
            startText.setText(Time);

            //Time calculation
            String remainingTime = createTimeText(SongTotaltime - SeekBarPosition);
            endText.setText("-"+remainingTime);
        }
    };

    public String createTimeText(int time){
        String timeText;
        int min = time / 1000 / 60;
        int sec = time /1000 % 60;
        timeText = min + ":";
        if (sec < 10) timeText += "0";
        timeText +=sec;
        return timeText;
    }

    public void PlayButton(View view){
        if(!song.isPlaying()){
            //Stopped
            song.start();
            //Rotation start
            imageView.startAnimation(animation);
            playBtton.setBackgroundResource(R.drawable.baseline_pause_24);
        }else{
            //played
            song.pause();
            imageView.clearAnimation();
            playBtton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        }
    }

}