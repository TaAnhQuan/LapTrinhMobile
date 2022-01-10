package com.example.musiccuoiky.fragments;

import static com.example.musiccuoiky.MusicPlayer.updatePlaylist;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.musiccuoiky.R;
import com.example.musiccuoiky.adapters.AdapterSongForPlaylist;
import com.example.musiccuoiky.models.Playlist;
import com.example.musiccuoiky.models.Song;

import java.util.ArrayList;
import java.util.List;


public class FragmentDetailPlaylist extends Fragment {
    public static FragmentDetailPlaylist instance;
    public static TextView txtPlaylist, txtCount;
    public static RecyclerView rcvSongForPlaylist;
    public static AdapterSongForPlaylist adapterSongForPlaylist;
    public static List<Song> list;
    public static Button btnAddSong;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_playlist, container, false);
        instance = this;
        txtPlaylist = view.findViewById(R.id.txtPlaylist);
        txtCount = view.findViewById(R.id.txtCount);
        rcvSongForPlaylist = view.findViewById(R.id.rcvSongForPlaylist);
        btnAddSong = view.findViewById(R.id.btnAddSong);

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSong();
            }
        });


        Bundle bundle = getArguments();
        txtPlaylist.setText(bundle.getString("txtPlaylist"));
        txtCount.setText(bundle.getInt("tv_Count") + " bài hát");
        list = getListSongForPlaylist(bundle.getLong("playlistID"));

        adapterSongForPlaylist = new AdapterSongForPlaylist(getContext(), list);
        rcvSongForPlaylist.setAdapter(adapterSongForPlaylist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        rcvSongForPlaylist.setLayoutManager(layoutManager);
        rcvSongForPlaylist.setNestedScrollingEnabled(false);
        rcvSongForPlaylist.setLayoutManager(layoutManager);
        return view;
    }

    @SuppressLint("Range")
    public static List<Song> getListSongForPlaylist(long playlistID) {
        List<Song> list = new ArrayList<>();
        Cursor c = makePlaylistSongCursor(instance.getContext(), playlistID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, name, title, album, albumId, artist, artistId, path, albumArt = "";
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
            albumId = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
            artistId = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID));
            duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));
            Cursor cursor = instance.getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID + "=?",
                    new String[]{String.valueOf(albumId)},
                    null);
            if (cursor.moveToFirst()) {
                albumArt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Song song = new Song(id, name, title, album, albumId, artist, artistId, path, albumArt, duration, false);
            list.add(song);

        }
        c.close();
        return list;
    }

    public static final Cursor makePlaylistSongCursor(final Context context, final Long playlistID) {
        final StringBuilder mSelection = new StringBuilder();
        mSelection.append(MediaStore.Audio.AudioColumns.IS_MUSIC + "=1");
        mSelection.append(" AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''");
        return context.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID),
                new String[]{
                        MediaStore.Audio.Playlists.Members.AUDIO_ID,
                        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                        MediaStore.Audio.AudioColumns.TITLE,
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.ALBUM_ID,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ARTIST_ID,
                        MediaStore.Audio.AudioColumns.DURATION,
                        MediaStore.Audio.AudioColumns.DATA
                }, mSelection.toString(), null,
                MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);
    }

    private Dialog addSong() {
        return new MaterialDialog.Builder(this.getContext())
                .title("Chọn bản nhạc")
                .items(FragmentSong.list)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //list = getListSongForPlaylist(which);
                        Toast.makeText(getContext(), "song selected", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }
}