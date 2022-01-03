package com.example.musiccuoiky.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.adapters.AdapterAlbum;
import com.example.musiccuoiky.adapters.AdapterSongForArtist;
import com.example.musiccuoiky.models.Album;
import com.example.musiccuoiky.models.Song;

import java.util.List;


public class FragmentDetailArtist extends Fragment {

    private ImageView imvArtist;
    private TextView txtArtist;
    private RecyclerView rcvAlbumForArtist, rcvSongForArtist;
    private List<Song> listSong;
    private List<Album> listAlbum;
    private static AdapterSongForArtist adapterSongForArtist;
    private static AdapterAlbum adapterAlbum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_artist, container, false);

        imvArtist = view.findViewById(R.id.imvArtist);
        txtArtist = view.findViewById(R.id.txtArtist);
        rcvAlbumForArtist = view.findViewById(R.id.rcvAlbumForArtist);
        rcvSongForArtist = view.findViewById(R.id.rcvSongForArtist);

        RecyclerView.LayoutManager layoutManagerAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerSong = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvAlbumForArtist.setLayoutManager(layoutManagerAlbum);
        rcvSongForArtist.setLayoutManager(layoutManagerSong);


        Bundle bundle = getArguments();
        imvArtist.setImageDrawable(Drawable.createFromPath(bundle.getString("imvArtist")));
        txtArtist.setText(bundle.getString("txtArtist"));
        listSong = getListSong(bundle.getString("artistID"));
        listAlbum = getListAlbum(bundle.getString("artistID"));

        //adapter part
        adapterAlbum = new AdapterAlbum(getContext(),listAlbum);
        adapterSongForArtist = new AdapterSongForArtist(getContext(),listSong);

        //recycle view part
        rcvAlbumForArtist.setAdapter(adapterAlbum);
        rcvSongForArtist.setAdapter(adapterSongForArtist);
        layoutManagerAlbum.setAutoMeasureEnabled(true);
        layoutManagerSong.setAutoMeasureEnabled(true);
        rcvSongForArtist.setNestedScrollingEnabled(false);
        rcvAlbumForArtist.setNestedScrollingEnabled(false);

        return view;
    }

    private List<Album> getListAlbum(String string) {
        return null;
    }

    private List<Song> getListSong(String artistID) {
        return null;
    }
}