package com.example.musiccuoiky.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterAlbum;
import com.example.musiccuoiky.models.Album;

import java.util.List;

public class FragmentAlbum extends Fragment {
    RecyclerView rcvAlbum;
    AdapterAlbum adapterAlbum;
    List<Album> list;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);
        rcvAlbum = view.findViewById(R.id.rcvAlbum);
        list = MainActivity.listAlbum;
        adapterAlbum = new AdapterAlbum(getContext(),list);
        rcvAlbum.setAdapter(adapterAlbum);
        rcvAlbum.setLayoutManager(new GridLayoutManager(getContext(),3));
        return view;
    }
}
