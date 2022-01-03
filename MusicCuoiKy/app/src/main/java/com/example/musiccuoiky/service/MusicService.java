package com.example.musiccuoiky.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.musiccuoiky.models.Song;

import java.util.List;

public class MusicService extends Service {
    public static int pos = 0;
    public static List<Song> list;
    public static MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void initMedia(Context context) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static void newNotification(Context context) {

    }

    private void createNotificationChannel() {

    }

    public static void updateNotification() {

    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicService.mediaPlayer = mediaPlayer;
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        MusicService.pos = pos;
    }

}