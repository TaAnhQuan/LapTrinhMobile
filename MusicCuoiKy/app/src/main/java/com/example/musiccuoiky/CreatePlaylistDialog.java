package com.example.musiccuoiky;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.musiccuoiky.MusicPlayer;

import static com.example.musiccuoiky.MusicPlayer.updatePlaylist;

public class  CreatePlaylistDialog extends DialogFragment {
    public static CreatePlaylistDialog instance;

    public CreatePlaylistDialog() {
        instance = this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(instance.getContext())
                .title("Tạo danh sách")
                .inputRange(1, 50)
                .positiveText("Tạo")
                .negativeText("Thoát")
                .input("", "Danh sách phát...", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        long playlistId = MusicPlayer.createPlaylist(getActivity(), input.toString());
                        updatePlaylist();
                    }
                }).show();
    }
}
