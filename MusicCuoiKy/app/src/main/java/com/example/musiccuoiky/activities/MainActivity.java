package com.example.musiccuoiky.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.adapters.AdapterViewPagerMain;
import com.example.musiccuoiky.dataloaders.AlbumLoader;
import com.example.musiccuoiky.dataloaders.ArtistLoader;
import com.example.musiccuoiky.dataloaders.PlaylistLoader;
import com.example.musiccuoiky.dataloaders.SongLoader;
import com.example.musiccuoiky.defines.Define;
import com.example.musiccuoiky.fragments.FragmentDetailAlbum;
import com.example.musiccuoiky.fragments.FragmentDetailArtist;
import com.example.musiccuoiky.fragments.FragmentDetailPlaylist;
import com.example.musiccuoiky.models.Album;
import com.example.musiccuoiky.models.Artist;
import com.example.musiccuoiky.models.Playlist;
import com.example.musiccuoiky.models.Song;
import com.example.musiccuoiky.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import static com.example.musiccuoiky.service.MusicService.getPos;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainActivity instance;
    public static ViewPager viewPager;
    public static TabLayout tabLayout;
    public static FragmentManager fragmentManager;
    public static ImageView imvSong;
    public static TextView txtSong, txtArtist;
    public static ImageButton btnPrevious, btnPlay, btnNext;
    public static List<Song> listSong = new ArrayList<>();
    public static List<Album> listAlbum = new ArrayList<>();
    public static List<Artist> listArtist = new ArrayList<>();
    public static List<Playlist> playList = new ArrayList<>();
    public static LinearLayout bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        addPermission();
    }

    public static void updateList() {
        listSong = SongLoader.getListSongs(instance);
        listAlbum = AlbumLoader.getListAlbums(instance);
        listArtist = ArtistLoader.getListArtist(instance);
        playList = PlaylistLoader.getPlaylist(instance);
    }

    public void setControl() {
        updateList();
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        MusicService.list = listSong;
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        AdapterViewPagerMain adapterViewPager = new AdapterViewPagerMain(fragmentManager);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapterViewPager);
        imvSong = findViewById(R.id.imvSong);
        txtSong = findViewById(R.id.txtSong);
        txtArtist = findViewById(R.id.txtArtist);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnClickListener(this);
        updateUI();
    }

    public static void addFragmentDetailAlbum(Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailAlbum fragmentDetailAlbum = new FragmentDetailAlbum();
        fragmentDetailAlbum.setArguments(bundle);
        transaction.add(R.id.placeHolder, fragmentDetailAlbum);
        transaction.addToBackStack("album");
        transaction.commit();
    }

    public static void addFragmentDetailArtist(Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailArtist fragmentDetailArtist = new FragmentDetailArtist();
        fragmentDetailArtist.setArguments(bundle);
        transaction.add(R.id.placeHolder, fragmentDetailArtist);
        transaction.addToBackStack("artist");
        transaction.commit();
    }

    public static void addFragmentDetailPlaylist(Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailPlaylist fragmentDetailPlaylist = new FragmentDetailPlaylist();
        fragmentDetailPlaylist.setArguments(bundle);
        transaction.add(R.id.placeHolder, fragmentDetailPlaylist);
        transaction.addToBackStack("playlist");
        transaction.commit();
    }

    public static void updateUI() {
        try {
            imvSong.setImageDrawable(Drawable.createFromPath(MusicService.list.get(getPos()).getAlbumArt()));
            txtSong.setText(MusicService.list.get(getPos()).getName());
            txtArtist.setText(MusicService.list.get(getPos()).getArtist());
        } catch (IndexOutOfBoundsException e) {

        }
    }

    void addPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setControl();

            } else {

                Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        switch (view.getId()) {
            case R.id.btnPrevious:
                intent.setAction(Define.actPrevious);
                startService(intent);
                break;
            case R.id.btnPlay:
                intent.setAction(Define.actPlay);
                startService(intent);
                break;
            case R.id.btnNext:
                intent.setAction(Define.actNext);
                startService(intent);
                break;
            case R.id.bottomBar:
                Intent intent1 = new Intent(MainActivity.this, PlaySongActivity.class);
                startActivity(intent1);
        }
    }
}
