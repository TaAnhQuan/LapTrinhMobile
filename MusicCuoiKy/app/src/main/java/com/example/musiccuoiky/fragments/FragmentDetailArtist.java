package com.example.musiccuoiky.fragments;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterAlbum;
import com.example.musiccuoiky.adapters.AdapterSongForArtist;
import com.example.musiccuoiky.models.Album;
import com.example.musiccuoiky.models.Song;

import java.util.ArrayList;
import java.util.List;

public class FragmentDetailArtist extends Fragment {
    public static ImageView imvArtist;
    public static TextView txtArtist;
    public static RecyclerView rcvAlbumForArtist, rcvSongForArtist;
    public static AdapterSongForArtist adapterSongForArtist;
    public static AdapterAlbum adapterAlbum;
    public static List<Song> listSong;
    public static List<Album> listAlbum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_artist, container, false);
        imvArtist = view.findViewById(R.id.imvArtist);
        txtArtist = view.findViewById(R.id.txtArtist);
        rcvAlbumForArtist = view.findViewById(R.id.rcvAlbumForArtist);
        rcvSongForArtist = view.findViewById(R.id.rcvSongForArtist);
        RecyclerView.LayoutManager layoutManagerAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerSong = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvAlbumForArtist.setLayoutManager(layoutManagerAlbum);
        rcvSongForArtist.setLayoutManager(layoutManagerSong);
        Bundle bundle = getArguments();
        imvArtist.setImageDrawable(Drawable.createFromPath(bundle.getString("image_Artist")));
        txtArtist.setText(bundle.getString("txtArtist"));
        listSong = getListSong(bundle.getString("artistID"));
        listAlbum = getListAlbum(bundle.getString("artistID"));
        adapterAlbum = new AdapterAlbum(getContext(), listAlbum);
        adapterSongForArtist = new AdapterSongForArtist(getContext(), listSong);
        rcvAlbumForArtist.setAdapter(adapterAlbum);
        rcvSongForArtist.setAdapter(adapterSongForArtist);
        layoutManagerAlbum.setAutoMeasureEnabled(true);
        layoutManagerSong.setAutoMeasureEnabled(true);
        rcvSongForArtist.setNestedScrollingEnabled(false);
        rcvAlbumForArtist.setNestedScrollingEnabled(false);
        return view;
    }

    public List<Song> getListSong(String artistID) {
        List<Song> list = new ArrayList<>();
        for (int i = 0; i < MainActivity.listArtist.size(); i++) {
            for (int j = 0; j < MainActivity.listSong.size(); j++)
                if (MainActivity.listArtist.get(i).getId().compareTo(artistID) == 0
                        && MainActivity.listArtist.get(i).getId()
                        .compareTo(MainActivity.listSong.get(j).getArtistId()) == 0) {
                    list.add(MainActivity.listSong.get(j));
                }
        }
        return list;
    }

    public List<Album> getListAlbum(String artistID) {
        List<Album> list = new ArrayList<>();
        String[] m_data = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        };
        Cursor c = getContext().getContentResolver()
                .query(MediaStore.Audio.Artists.Albums.getContentUri("external", Long.parseLong(artistID)),
                        m_data, null, null, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, album, albumArt, artist, numberOfSongs;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
            albumArt = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST));
            numberOfSongs = " (" + c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)) + ")";
            Album albums = new Album(id, album, albumArt, artist, numberOfSongs);
            list.add(albums);
        }
        return list;
    }
}