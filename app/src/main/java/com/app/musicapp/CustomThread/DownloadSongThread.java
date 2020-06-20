package com.app.musicapp.CustomThread;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.app.musicapp.R;
import com.app.musicapp.api.providers.EntertainmentProvider;
import com.app.musicapp.config.Env;
import com.app.musicapp.model.Song;
import org.jetbrains.annotations.NotNull;

public class DownloadSongThread extends Thread {
    private static final String TAG = "DownloadSongThread";
    private long minPrime;
    private AppCompatActivity activity;
    private Song song;
    public EntertainmentProvider _provider = new EntertainmentProvider();

    public DownloadSongThread(long minPrime) {
        this.minPrime = minPrime;
    }

    public DownloadSongThread(long minPrime, @NotNull Song song, @NotNull AppCompatActivity appCompatActivity) {
        this.minPrime = minPrime;
        this.song = song;
        this.activity = appCompatActivity;
    }

    public long getMinPrime() {
        return minPrime;
    }

    public void setMinPrime(long minPrime) {
        this.minPrime = minPrime;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void run() {
        try {
            NotificationManager mNotificationManager =
                    (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(Env.CHANNEL_ID_DOWNLOAD,
                        Env.CHANNEL_NAME_DOWNLOAD,
                        NotificationManager.IMPORTANCE_LOW);
                channel.setDescription("Download Song Notification");
                mNotificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), Env.CHANNEL_ID_DOWNLOAD);
            builder.setContentTitle(activity.getResources().getString(R.string.music_notification_download))
                    .setContentText(activity.getResources().getString(R.string.download_in_progress))
                    .setSmallIcon(R.drawable.default_image2)
                    .setPriority(NotificationCompat.PRIORITY_LOW);
            builder.setProgress(0, 0, true);
            mNotificationManager.notify(1, builder.build());
            _provider.downloadSongAsync(this.song.data, builder, mNotificationManager, activity).subscribe(val -> {
                builder.setContentText(activity.getResources().getString(R.string.completed_download_song))
                        .setProgress(0, 0, false);
                mNotificationManager.notify(1, builder.build());
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, activity.getResources().getString(R.string.completed_download_song), Toast.LENGTH_LONG).show();
                    }
                });
            }, throwable -> {
                Log.e("TAG", throwable.getMessage());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
