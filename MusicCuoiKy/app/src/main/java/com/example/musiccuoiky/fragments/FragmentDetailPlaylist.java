package com.example.musiccuoiky.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.adapters.AdapterSongForPlaylist;
import com.example.musiccuoiky.models.Song;

import java.util.ArrayList;
import java.util.List;

public class FragmentDetailPlaylist extends Fragment {

    private static FragmentDetailPlaylist instance;
    private TextView txtPlaylist, txtCount;
    private RecyclerView rcvSongForPlayList;
    private List<Song> list;
    private static AdapterSongForPlaylist adapterSongForPlaylist;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_playlist, container, false);
        instance = this;

        txtPlaylist = view.findViewById(R.id.txtPlaylist);
        txtCount = view.findViewById(R.id.txtCount);
        rcvSongForPlayList = view.findViewById(R.id.rcvSongForPlaylist);

        Bundle bundle = getArguments();
        txtPlaylist.setText(bundle.getString("txtPlaylist"));
        txtCount.setText(bundle.getInt("txtCount")+" bài hát");
        list = getListSongForPlaylist(bundle.getLong("playlistID"));

        //adapter part
        adapterSongForPlaylist = new AdapterSongForPlaylist(getContext(),list);
        rcvSongForPlayList.setAdapter(adapterSongForPlaylist);

        //recycle view part
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setAutoMeasureEnabled(true);
        rcvSongForPlayList.setLayoutManager(layoutManager);
        rcvSongForPlayList.setNestedScrollingEnabled(false);
        rcvSongForPlayList.setLayoutManager(layoutManager);

        return view;
    }

    private List<Song> getListSongForPlaylist(long playlistID) {
        List<Song> list = new ArrayList<>();
        Cursor c = makePlaylistSongCursor(instance.getContext(),playlistID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, name, title, album, albumID, artist, artistID, path, albumArt="";
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
            albumID = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
            artistID = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID));
            duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));
            Cursor cursor = instance.getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    new String[] {String.valueOf(albumID)},
                    null);
            if (cursor.moveToFirst()) {
                albumArt = cursor.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Song song = new Song(id, name, title, album, albumID, artist,artistID, path, albumArt, duration);
            list.add(song);


        }
        c.close();
        return list;
    }

    private static final Cursor makePlaylistSongCursor(final Context context, final Long playlistID) {
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
}