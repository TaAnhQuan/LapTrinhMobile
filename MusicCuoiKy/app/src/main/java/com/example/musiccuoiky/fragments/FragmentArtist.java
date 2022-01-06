package com.example.musiccuoiky.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterArtist;
import com.example.musiccuoiky.models.Artist;

import java.util.List;


public class FragmentArtist extends Fragment {
    RecyclerView rcvArtist;
    AdapterArtist adapterArtist;
    List<Artist> list;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist,container,false);
        rcvArtist = view.findViewById(R.id.rcvArtist);
        list = MainActivity.listArtist;
        adapterArtist = new AdapterArtist(getContext(),list);
        rcvArtist.setAdapter(adapterArtist);
        rcvArtist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }
}


