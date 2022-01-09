package com.example.musiccuoiky.fragments;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;

import android.app.Dialog;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccuoiky.CreatePlaylistDialog;
import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterPlaylist;
import com.example.musiccuoiky.models.Playlist;

import java.util.List;

public class FragmentPlaylist extends Fragment implements View.OnClickListener {
    RecyclerView rcvPlaylist;
    public static AdapterPlaylist adapterPlaylist;
    public static List<Playlist> list;
    Button btnTaoPlaylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        rcvPlaylist = view.findViewById(R.id.rcvPlaylist);
        list = MainActivity.playList;
        adapterPlaylist = new AdapterPlaylist(getContext(), list);
        rcvPlaylist.setAdapter(adapterPlaylist);
        rcvPlaylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        btnTaoPlaylist = view.findViewById(R.id.btnCreatePlaylist);
        btnTaoPlaylist.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnTaoPlaylist) {
            makeDialogCreatePlaylist();
        }
    }

    public void makeDialogCreatePlaylist() {
        CreatePlaylistDialog playlistDialog = new CreatePlaylistDialog();
        playlistDialog.show(getFragmentManager(), "create playlist");


    }
}
