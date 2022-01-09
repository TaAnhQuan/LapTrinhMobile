package com.example.musiccuoiky.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.musiccuoiky.service.MusicService.setPos;


import com.example.musiccuoiky.R;
import com.example.musiccuoiky.defines.Define;
import com.example.musiccuoiky.fragments.FragmentSong;
import com.example.musiccuoiky.models.Song;
import com.example.musiccuoiky.service.MusicService;

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder> {
    public static List<Song> list;
    Context context;
    LayoutInflater inflater;
    public static int pos = FragmentSong.pos;
    public static AdapterSong instance;

    public AdapterSong(Context context, List<Song> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_song, parent, false);
        return new ViewHolder(item);
    }

    public static void setCurrentPos(int pos) {
        instance.pos = pos;
        instance.notifyDataSetChanged();
    }

    public static int getCurrentPos() {
        return pos;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imvSong.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbumArt()));
        holder.txtSong.setText(list.get(position).getName());
        holder.txtArtist.setText(list.get(position).getArtist());

        boolean[] isFavorite = {false};
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFavorite[0]){
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
                    isFavorite[0] = true;
                }else{
                    holder.btnFavorite.setImageResource(R.drawable.ic_unfavorite);
                    isFavorite[0] = false;
                }
            }
        });

        if (list.get(position).getId().compareTo(MusicService.list.get(MusicService.pos).getId()) == 0) {
            holder.txtSong.setTextColor(Color.MAGENTA);
            holder.txtArtist.setTextColor(Color.MAGENTA);
        } else {
            holder.txtSong.setTextColor(Color.BLACK);
            holder.txtArtist.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                notifyDataSetChanged();
                setPos(position);
                MusicService.list = list;
                Intent intent = new Intent(context, MusicService.class);
                intent.setAction(Define.actStart);
                context.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvSong;
        TextView txtSong, txtArtist;
        ImageButton btnFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            imvSong = itemView.findViewById(R.id.imvSong);
            txtSong = itemView.findViewById(R.id.txtSong);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            btnFavorite = itemView.findViewById(R.id.bntFavorite);
        }
    }
}
