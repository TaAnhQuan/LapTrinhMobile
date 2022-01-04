package com.example.musiccuoiky.controls;

import static com.example.musiccuoiky.activities.MainActivity.updateUI;
import static com.example.musiccuoiky.activities.PlaySongActivity.btnPlay;
import static com.example.musiccuoiky.service.MusicService.contentView;
import static com.example.musiccuoiky.service.MusicService.initMedia;
import static com.example.musiccuoiky.service.MusicService.mediaPlayer;
import static com.example.musiccuoiky.service.MusicService.newNotification;
import static com.example.musiccuoiky.service.MusicService.notification;
import static com.example.musiccuoiky.service.MusicService.pos;
import static com.example.musiccuoiky.service.MusicService.updateNotification;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.PlaySongActivity;
import com.example.musiccuoiky.adapters.AdapterSong;
import com.example.musiccuoiky.adapters.AdapterSongForAlbum;
import com.example.musiccuoiky.adapters.AdapterSongForArtist;
import com.example.musiccuoiky.adapters.AdapterSongForPlaylist;
import com.example.musiccuoiky.service.MusicService;


public class Control {
    public static void previous(Context context) {
        initMedia(context);
        if (pos > 0) {
            pos--;
        } else pos = MusicService.list.size() - 1;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            btnPlay.setImageResource(R.drawable.ic_pause_white);
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {

            }

            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            btnPlay.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {

            }
        }
        updateUI();
        if (notification != null) {
            updateNotification();
        }
        try {
            PlaySongActivity.updateUI();
            if (PlaySongActivity.FLAG_ALIVE == 1)
                PlaySongActivity.viewPager.setCurrentItem(pos, false);
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        setPos();
    }

    public static void start(Context context) {
        if (notification == null) {
            newNotification(context);
        }
        contentView.setImageViewResource(R.id.btnPlay, R.drawable.ic_pause_black);
        updateNotification();
        initMedia(context);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        btnPlay.setImageResource(R.drawable.ic_pause_white);
        try {
            btnPlay.setImageResource(R.drawable.ic_pause_white);
        } catch (NullPointerException e) {
        }
        mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
        mediaPlayer.start();
        updateUI();
        try {
            PlaySongActivity.updateUI();
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        setPos();
    }

    public static void play(Context context) {
        initMedia(context);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            contentView.setImageViewResource(R.id.btnPlay, R.drawable.ic_play_black);
            updateNotification();
            btnPlay.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {
            }
        } else {
            btnPlay.setImageResource(R.drawable.ic_pause_white);
            if (notification == null) {
                newNotification(context);
            }
            contentView.setImageViewResource(R.id.btnPlay, R.drawable.ic_pause_black);
            updateNotification();
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {
            }
            mediaPlayer.start();
        }
        setPos();
    }

    public static void next(Context context) {
        initMedia(context);
        if (pos < MusicService.list.size() - 1) {
            pos++;
        } else pos = 0;
        if (mediaPlayer.isPlaying() || PlaySongActivity.complete) {
            mediaPlayer.stop();
            mediaPlayer.release();
            btnPlay.setImageResource(R.drawable.ic_pause_white);
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            btnPlay.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.btnPlay.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {
            }
        }
        updateUI();
        if (notification != null) {
            updateNotification();
        }
        try {
            PlaySongActivity.updateUI();
            if (PlaySongActivity.FLAG_ALIVE == 1)
                PlaySongActivity.viewPager.setCurrentItem(pos, false);
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e) {
        }
        setPos();
    }

    public static void setPos() {
        try {
            //adapter song
            boolean ok = false;
            for (int i = 0; i < AdapterSong.list.size(); i++)
                if (AdapterSong.list.get(i).getId().compareTo(MusicService.list.get(MusicService.pos).getId()) == 0) {
                    AdapterSong.setCurrentPos(i);
                    ok = true;
                    break;
                }
            if (!ok) AdapterSong.setCurrentPos(-1);
        } catch (NullPointerException e) {
        }

        try {
            //adapter album
            boolean ok = false;
            for (int i = 0; i < AdapterSongForAlbum.list.size(); i++)
                if (AdapterSongForAlbum.list.get(i).getId().compareTo(MusicService.list.get(MusicService.pos).getId()) == 0) {
                    AdapterSongForAlbum.setCurrentPos(i);
                    ok = true;
                    break;
                }
            if (!ok) AdapterSongForAlbum.setCurrentPos(-1);
        } catch (NullPointerException e) {
        }

        try {
            //adapter artist
            boolean ok = false;
            for (int i = 0; i < AdapterSongForArtist.list.size(); i++)
                if (AdapterSongForArtist.list.get(i).getId().compareTo(MusicService.list.get(MusicService.pos).getId()) == 0) {
                    AdapterSongForArtist.setCurrentPos(i);
                    ok = true;
                    break;
                }
            if (!ok) AdapterSongForArtist.setCurrentPos(-1);
        } catch (NullPointerException e) {
        }

        try {
            //adapter playlist
            boolean ok = false;
            for (int i = 0; i < AdapterSongForPlaylist.list.size(); i++)
                if (AdapterSongForPlaylist.list.get(i).getId().compareTo(MusicService.list.get(MusicService.pos).getId()) == 0) {
                    AdapterSongForPlaylist.setCurrentPos(i);
                    ok = true;
                    break;
                }
            if (!ok) AdapterSongForPlaylist.setCurrentPos(-1);
        } catch (NullPointerException e) {
        }
    }

    public static void exit(Context context) {
        MusicService.instance.stopForeground(true);
        MusicService.mNotificationManager.cancel(1);
        System.exit(1);
    }
}
