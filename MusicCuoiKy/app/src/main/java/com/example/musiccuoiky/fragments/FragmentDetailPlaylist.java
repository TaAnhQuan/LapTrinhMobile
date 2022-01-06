package com.example.musiccuoiky.fragments;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    public static FragmentDetailPlaylist instance;
    public static TextView txtPlaylist, txtCount;
    public static RecyclerView rcvSongForPlaylist;
    public static AdapterSongForPlaylist adapterSongForPlaylist;
    public static List<Song> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_playlist,container,false);
        instance = this;
        txtPlaylist = view.findViewById(R.id.txtPlaylist);
        txtCount = view.findViewById(R.id.txtCount);
        Bundle bundle = getArguments();
        txtPlaylist.setText(bundle.getString("txtPlaylist"));
        txtCount.setText(bundle.getInt("tv_Count")+" bài hát");
        rcvSongForPlaylist = view.findViewById(R.id.rcvSongForPlaylist);
        list = getListSongForPlaylist(bundle.getLong("playlistID"));
        adapterSongForPlaylist = new AdapterSongForPlaylist(getContext(),list);
        rcvSongForPlaylist.setAdapter(adapterSongForPlaylist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setAutoMeasureEnabled(true);
        rcvSongForPlaylist.setLayoutManager(layoutManager);
        rcvSongForPlaylist.setNestedScrollingEnabled(false);
        rcvSongForPlaylist.setLayoutManager(layoutManager);
        return view;
    }

    public static List<Song> getListSongForPlaylist(long playlistID){
        List<Song> list = new ArrayList<>();
        Cursor c = makePlaylistSongCursor(instance.getContext(),playlistID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, name, title, album, album_id, artist, artist_id, path, album_art="";
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
            album_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
            artist_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID));
            duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));
            Cursor cursor = instance.getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    new String[] {String.valueOf(album_id)},
                    null);
            if (cursor.moveToFirst()) {
                album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Song song = new Song(id, name, title, album, album_id, artist,artist_id, path, album_art, duration);
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
}