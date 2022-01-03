package com.example.musiccuoiky.fragments;

import android.os.Bundle;

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

import java.util.List;

public class FragmentDetailPlaylist extends Fragment {

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
        return null;
    }
}