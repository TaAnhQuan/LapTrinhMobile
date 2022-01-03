package com.example.musiccuoiky.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.models.Artist;

import java.util.List;

public class AdapterArtist extends RecyclerView.Adapter<AdapterArtist.ViewHolder>{
    List<Artist> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterArtist instance;

    public AdapterArtist(Context context, List<Artist> list) {
        instance = this;
        this.context = context;
        this.list = list;
        //inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //fix
        LayoutInflater inflater = LayoutInflater.from(context);
        //
        View item = inflater.inflate(R.layout.item_artist,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imvArtist.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbum_art()));
        holder.txtArtist.setText(list.get(position).getArtist());
        holder.txtNumOfAlbums.setText(list.get(position).getNumOfAlbums());
        holder.txtNumOfSongs.setText(list.get(position).getNumOfSongs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("artistID",list.get(position).getId());
                bundle.putString("image_Artist",list.get(position).getAlbum_art());
                bundle.putString("tv_Artist",list.get(position).getArtist());
                MainActivity.addFragmentDetailArtist(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imvArtist;
        private TextView txtArtist, txtNumOfAlbums, txtNumOfSongs;
        public ViewHolder(View itemView) {
            super(itemView);
            imvArtist = itemView.findViewById(R.id.imvArtist);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            txtNumOfAlbums = itemView.findViewById(R.id.txtNumOfAlbums);
            txtNumOfSongs = itemView.findViewById(R.id.txtNumOfSongs);
        }
    }
}


