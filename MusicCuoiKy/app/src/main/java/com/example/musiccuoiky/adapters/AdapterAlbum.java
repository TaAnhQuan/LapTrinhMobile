package com.example.musiccuoiky.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.models.Album;

import java.util.List;

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.ViewHolder> {
    List<Album> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterAlbum instance;

    public AdapterAlbum(Context context, List<Album> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_album, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imvAlbum.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbumArt()));
        holder.txtAlbum.setText(list.get(position).getAlbum());
        holder.txtArtist.setText(list.get(position).getArtist());
        holder.txtCount.setText(list.get(position).getNumberOfSongs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("albumID", list.get(position).getId());
                bundle.putString("imvAlbum", list.get(position).getAlbumArt());
                bundle.putString("txtAlbum", list.get(position).getAlbum());
                bundle.putString("txtArtist", list.get(position).getArtist());
                MainActivity.addFragmentDetailAlbum(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvAlbum;
        TextView txtAlbum, txtArtist, txtCount;

        public ViewHolder(View itemView) {
            super(itemView);
            imvAlbum = itemView.findViewById(R.id.imvAlbum);
            txtAlbum = itemView.findViewById(R.id.txtAlbum);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            txtCount = itemView.findViewById(R.id.txtCount);
        }
    }
}

