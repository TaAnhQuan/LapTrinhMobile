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
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.adapters.AdapterSongForAlbum;
import com.example.musiccuoiky.models.Song;

import java.util.ArrayList;
import java.util.List;


public class FragmentDetailAlbum extends Fragment {


    private static ImageView imvAlbum;
    private static TextView txtAlbum, txtArtist;
    private static RecyclerView rcvSongForAlbum;
    private static List<Song> list;
    private static AdapterSongForAlbum adapterSongForAlbum;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_album, container, false);


        imvAlbum = view.findViewById(R.id.imvAlbum);
        txtAlbum = view.findViewById(R.id.txtAlbum);
        txtArtist = view.findViewById(R.id.txtArtist);
        rcvSongForAlbum = view.findViewById(R.id.rcvSongForAlbum);

        Bundle bundle = getArguments();
        imvAlbum.setImageDrawable(Drawable.createFromPath(bundle.getString("imvAlbum")));
        txtAlbum.setText(bundle.getString("txtAlbum"));
        txtArtist.setText(bundle.getString("txtArtist"));
        list = getList(bundle.getString("albumID"));

        //adapter part
        adapterSongForAlbum = new AdapterSongForAlbum(getContext(),list);
        rcvSongForAlbum.setAdapter(adapterSongForAlbum);

        //recycle view part
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        //layoutManager.setAutoMeasureEnabled(true);
        rcvSongForAlbum.setLayoutManager(layoutManager);
        rcvSongForAlbum.setNestedScrollingEnabled(false);
        rcvSongForAlbum.setLayoutManager(layoutManager);

        return view;
    }

    private List<Song> getList(String albumID) {
        List<Song> list = new ArrayList<>();
        for (int i = 0; i< MainActivity.listAlbum.size(); i++){
            for (int j=0;j<MainActivity.listSong.size();j++)
                if (MainActivity.listAlbum.get(i).getId().compareTo(albumID)==0
                        && MainActivity.listAlbum.get(i).getId().compareTo(MainActivity.listSong.get(j).getAlbumId())==0){
                    list.add(MainActivity.listSong.get(j));
                }
        }
        return list;
    }
}