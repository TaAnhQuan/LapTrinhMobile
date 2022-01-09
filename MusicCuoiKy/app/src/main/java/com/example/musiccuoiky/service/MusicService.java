package com.example.musiccuoiky.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.SwitchButtonListener;
import com.example.musiccuoiky.controls.Control;
import com.example.musiccuoiky.defines.Define;
import com.example.musiccuoiky.models.Song;

import java.util.List;


public class MusicService extends Service {
    public static MusicService instance;
    public static int pos = 0;
    public static List<Song> list;
    public static MediaPlayer mediaPlayer;
    public static NotificationManager manager;
    public static NotificationManagerCompat mNotificationManager;
    public static NotificationCompat.Builder builder;
    public static Notification notification;
    public static RemoteViews contentView;
    public static final String CHANNEL_ID = "1234";

    public void onCreate() {
        super.onCreate();
        instance = this;
        mNotificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();
    }

    public static void initMedia(Context context) {
        try {
            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
        } catch (IndexOutOfBoundsException e) {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "start service", Toast.LENGTH_SHORT).show();
        try {
            if (intent.getAction().compareTo(Define.actPrevious) == 0) {
//                Toast.makeText(this,"previous",Toast.LENGTH_SHORT).show();
                Control.previous(this);
            } else if (intent.getAction().compareTo(Define.actPlay) == 0) {
//                Toast.makeText(this,"play",Toast.LENGTH_SHORT).show();
                Control.play(this);
            } else if (intent.getAction().compareTo(Define.actNext) == 0) {
//                Toast.makeText(this,"next",Toast.LENGTH_SHORT).show();
                Control.next(this);
            } else if (intent.getAction().compareTo(Define.actStart) == 0) {
//                Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
                Control.start(this);
            } else if (intent.getAction().compareTo(Define.actExit) == 0) {
//                Toast.makeText(this,"exit",Toast.LENGTH_SHORT).show();
                Control.exit(this);
            }
        } catch (NullPointerException e) {
        }
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void newNotification(Context context) {
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("My Music");
        builder.setContentText("thông báo abcd");
        builder.setSmallIcon(R.drawable.ic_music_default);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_music_default));
        contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        builder.setCustomContentView(contentView);
        Intent iPrevious = new Intent(Define.actPrevious);
        Intent iNext = new Intent(Define.actNext);
        Intent iPlay = new Intent(Define.actPlay);
        Intent iExit = new Intent(Define.actExit);
        iPrevious.setClass(context, SwitchButtonListener.class);
        iNext.setClass(context, SwitchButtonListener.class);
        iPlay.setClass(context, SwitchButtonListener.class);
        iExit.setClass(context, SwitchButtonListener.class);
        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, iPrevious, 0);
        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, iNext, 0);
        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, iPlay, 0);
        PendingIntent pExit = PendingIntent.getBroadcast(context, 0, iExit, 0);
        contentView.setOnClickPendingIntent(R.id.btnPrevious, pPrevious);
        contentView.setOnClickPendingIntent(R.id.btnNext, pNext);
        contentView.setOnClickPendingIntent(R.id.btnPlay, pPlay);
        contentView.setOnClickPendingIntent(R.id.btnExit, pExit);
        notification = builder.build();
        MusicService.instance.startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Music";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            manager.createNotificationChannel(mChannel);
        }
    }

    public static void updateNotification() {
        contentView.setTextViewText(R.id.txtSong, list.get(pos).getName());
        contentView.setTextViewText(R.id.txtArtist, list.get(pos).getArtist());
        Uri uri = null;
        try {
            uri = Uri.parse(list.get(pos).getAlbumArt());
        } catch (NullPointerException e) {
        }
        if (uri != null)
            contentView.setImageViewUri(R.id.imvSong, uri);
        else contentView.setImageViewResource(R.id.imvSong, R.drawable.ic_music_default);
        mNotificationManager.notify(1, notification);
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