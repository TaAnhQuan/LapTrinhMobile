package com.example.musiccuoiky.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import static com.example.musiccuoiky.MusicPlayer.updatePlaylist;

import com.example.musiccuoiky.MusicPlayer;
import com.example.musiccuoiky.R;
import com.example.musiccuoiky.activities.MainActivity;
import com.example.musiccuoiky.models.Playlist;


public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolder> {
    List<Playlist> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterPlaylist instance;

    public AdapterPlaylist(Context context, List<Playlist> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtPlaylist.setText(list.get(position).getName());
        holder.txtCount.setText(list.get(position).getSongCount() + " bài hát");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("playlistID", list.get(position).getId());
                bundle.putString("txtPlaylist", list.get(position).getName());
                bundle.putInt("txtCount", list.get(position).getSongCount());
                MainActivity.addFragmentDetailPlaylist(bundle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MusicPlayer.deletePlaylist(context, list.get(position).getName());
                updatePlaylist();
                Toast.makeText(context, "Đã xóa playlist", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPlaylist, txtCount;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPlaylist = itemView.findViewById(R.id.txtPlaylist);
            txtCount = itemView.findViewById(R.id.txtCount);
        }
    }
}

