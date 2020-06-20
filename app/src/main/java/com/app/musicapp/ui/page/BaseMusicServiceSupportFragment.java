package com.app.musicapp.ui.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.app.musicapp.service.MusicServiceEventListener;
import com.app.musicapp.ui.BaseActivity;
import com.app.musicapp.ui.widget.fragmentnavigationcontroller.SupportFragment;

public abstract class BaseMusicServiceSupportFragment extends SupportFragment implements MusicServiceEventListener {
    @Override
    public void onServiceConnected() {

    }

    @Override
    public void onPaletteChanged() {

    }

    @Override
    public void onServiceDisconnected() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) ((BaseActivity) activity).addMusicServiceEventListener(this);
    }

    @Override
    public void onDestroyView() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) ((BaseActivity) activity).removeMusicServiceEventListener(this);
        super.onDestroyView();
    }

    @Override
    public void onPlayStateChanged() {

    }

    @Override
    public void onRepeatModeChanged() {

    }

    @Override
    public void onShuffleModeChanged() {

    }

    @Override
    public void onQueueChanged() {

    }

    @Override
    public void onPlayingMetaChanged() {

    }

    @Override
    public void onMediaStoreChanged() {

    }
}
