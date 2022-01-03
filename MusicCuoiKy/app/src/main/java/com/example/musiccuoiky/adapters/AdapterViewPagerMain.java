package com.example.musiccuoiky.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.musiccuoiky.fragments.FragmentAlbum;
import com.example.musiccuoiky.fragments.FragmentArtist;
import com.example.musiccuoiky.fragments.FragmentPlaylist;
import com.example.musiccuoiky.fragments.FragmentSong;

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {
    public AdapterViewPagerMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 3:
                fragment = new FragmentSong();
                break;
            case 1:
                fragment = new FragmentAlbum();
                break;
            case 2:
                fragment = new FragmentArtist();
                break;
            case 0:
                fragment = new FragmentPlaylist();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Playlist";
                break;
            case 1:
                title = "Album";
                break;
            case 2:
                title = "Artist";
                break;
            case 3:
                title = "Song";
                break;
        }
        return title;
    }
}
