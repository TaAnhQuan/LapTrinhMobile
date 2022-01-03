package com.example.musiccuoiky.activities;

import static com.example.musiccuoiky.service.MusicService.getPos;
import static com.example.musiccuoiky.service.MusicService.mediaPlayer;
import static com.example.musiccuoiky.service.MusicService.setMediaPlayer;
import static com.example.musiccuoiky.service.MusicService.list;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.SwitchButtonListener;
import com.example.musiccuoiky.adapters.AdapterViewPagerPlaySong;
import com.example.musiccuoiky.defines.Define;
import com.example.musiccuoiky.service.MusicService;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

public class PlaySongActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{
    public static PlaySongActivity instance;
    public static TextView txtBegin, txtEnd;
    public static ImageButton btnPrevious, btnPlay, btnNext;
    public static SeekBar seekBar;
    public static int duration;
    public static boolean complete = false;
    public static ViewPager viewPager;
    public static AdapterViewPagerPlaySong adapter;
    public static int FLAG_ALIVE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FLAG_ALIVE = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        instance = this;
        viewPager = findViewById(R.id.viewPager);
        adapter = new AdapterViewPagerPlaySong(this,list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getPos(),true);
        viewPager.setOnPageChangeListener(new CircularViewPagerHandler(viewPager));
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        txtBegin = findViewById(R.id.txtBegin);
        txtEnd = findViewById(R.id.txtEnd);
        seekBar  = findViewById(R.id.seekBar);
        btnPrevious.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        if (mediaPlayer==null)
            setMediaPlayer(MediaPlayer.create(this, Uri.parse(list.get(getPos()).getPath())));
        updateUI();
        boolean isPlaying = false;
        try{
            if (mediaPlayer.isPlaying()){
                isPlaying = true;
            }
        } catch (NullPointerException e){
        }
        if (isPlaying) btnPlay.setImageResource(R.drawable.ic_pause_white);
        else btnPlay.setImageResource(R.drawable.ic_play_white);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FLAG_ALIVE = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Field mScroller;
            Interpolator sInterpolator = new AccelerateInterpolator();
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), sInterpolator);
            // scroller.setFixedDuration(5000);
            mScroller.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public class FixedSpeedScroller extends Scroller {

        private int mDuration = 0;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }

    public static void updateUI(){
        complete =false;
        duration = list.get(getPos()).getDuration();
        seekBar.setMax(duration);
        setTimeTotal();
        updateTimeSong();
    }

    public static void setTimeTotal(){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        duration = list.get(getPos()).getDuration();
        txtEnd.setText(format.format(duration));
    }

    public static void updateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                txtBegin.setText(format.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        complete = true;
                        Intent intent = new Intent();
                        intent.setAction(Define.actNext);
                        intent.setClass(instance, SwitchButtonListener.class);
                        instance.sendBroadcast(intent);
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(PlaySongActivity.this, MusicService.class);
        switch (view.getId()) {
            case R.id.btnPrevious:
                intent.setAction(Define.actPrevious);
                startService(intent);
                break;
            case R.id.btnPlay:
                intent.setAction(Define.actPlay);
                startService(intent);
                break;
            case R.id.btnNext:
                intent.setAction(Define.actNext);
                startService(intent);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.seekTo(seekBar.getProgress());
    }

    public class CircularViewPagerHandler implements ViewPager.OnPageChangeListener {
        private ViewPager   mViewPager;
        private int         mCurrentPosition;
        private int         mScrollState;

        public CircularViewPagerHandler(final ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onPageSelected(final int position) {
            mCurrentPosition = position;
            Log.d("cPOS",getPos()+"");
            Log.d("POS",position+"");
            if (getPos()<position && getPos()!=list.size()-1){
                Intent intent = new Intent();
                intent.setAction(Define.actNext);
                sendBroadcast(intent);
            } else if (getPos()>position && getPos()!=0){
                Intent intent = new Intent();
                intent.setAction(Define.actPrevious);
                sendBroadcast(intent);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            handleScrollState(state);
            mScrollState = state;
        }

        private void handleScrollState(final int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                setNextItemIfNeeded();
            }
        }

        private void setNextItemIfNeeded() {
            if (!isScrollStateSettling()) {
                handleSetNextItem();
            }
        }

        private boolean isScrollStateSettling() {
            return mScrollState == ViewPager.SCROLL_STATE_SETTLING;
        }

        private void handleSetNextItem() {
            final int lastPosition = mViewPager.getAdapter().getCount() - 1;
            if(mCurrentPosition == 0) {
                Intent intent = new Intent();
                intent.setAction(Define.actPrevious);
                sendBroadcast(intent);
                //mViewPager.setCurrentItem(lastPosition, false);
            } else if(mCurrentPosition == lastPosition) {
                Intent intent = new Intent();
                intent.setAction(Define.actNext);
                sendBroadcast(intent);
                //mViewPager.setCurrentItem(0, false);
            }
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        }
    }
}
