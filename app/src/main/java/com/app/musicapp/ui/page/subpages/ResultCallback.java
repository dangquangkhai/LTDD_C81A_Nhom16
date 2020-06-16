package com.app.musicapp.ui.page.subpages;

import com.app.musicapp.addon.lastfm.rest.model.LastFmArtist;

import java.util.ArrayList;

public interface ResultCallback {
    void onSuccess(LastFmArtist lastFmArtist);

    void onFailure(Exception e);

    void onSuccess(ArrayList<String> mResult);
}