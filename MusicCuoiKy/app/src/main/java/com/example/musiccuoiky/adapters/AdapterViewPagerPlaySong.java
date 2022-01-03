package com.example.musiccuoiky.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.models.Song;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewPagerPlaySong extends PagerAdapter {
    private Context context;
    private List<Song> list;

    public AdapterViewPagerPlaySong(Context context, List<Song> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_playsong,container,false);
        ImageView image_Song;
        TextView tv_Song, tv_Artist;
        image_Song = itemView.findViewById(R.id.imvSong);
        tv_Song = itemView.findViewById(R.id.txtSong);
        tv_Artist = itemView.findViewById(R.id.txtArtist);
        image_Song.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbumArt()));
        tv_Song.setText(list.get(position).getName());
        tv_Artist.setText(list.get(position).getArtist());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
