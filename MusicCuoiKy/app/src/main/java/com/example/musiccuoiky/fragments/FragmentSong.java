package com.example.musiccuoiky.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterSong;
import com.example.musiccuoiky.models.Song;

import java.util.List;

public class FragmentSong extends Fragment {
    public static FragmentSong instance;
    public static List<Song> list = MainActivity.listSong;
    RecyclerView rcvSong;
    AdapterSong adapterSong;
    public static int pos = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        rcvSong = view.findViewById(R.id.rcvSong);
        instance = this;
        adapterSong = new AdapterSong(getContext(),list);
        rcvSong.setAdapter(adapterSong);
        rcvSong.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }
}
