package com.app.musicapp.api.utils;

import android.media.MediaMetadataRetriever;

import java.util.HashMap;

public class LibUtils {
    public static long getSongDuration(String source) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(source, new HashMap<String, String>());
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        return duration;
    }
}
