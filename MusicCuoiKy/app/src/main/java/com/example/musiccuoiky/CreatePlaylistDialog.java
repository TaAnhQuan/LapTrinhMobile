package com.example.musiccuoiky;

import static com.example.musiccuoiky.MusicPlayer.updatePlaylist;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;


import com.afollestad.materialdialogs.MaterialDialog;


public class CreatePlaylistDialog extends DialogFragment {
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
                        long playistId = MusicPlayer.createPlaylist(getActivity(), input.toString());
                        updatePlaylist();
                    }
                }).show();
    }

    public void show(FragmentManager fragmentManager, String create_playlist) {
    }
}

