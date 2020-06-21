package com.app.musicapp.api.providers;

import android.app.NotificationManager;
import android.os.Environment;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.app.musicapp.R;
import com.app.musicapp.api.config.ApiConfig;
import com.app.musicapp.api.customModels.ApiResponse;
import com.app.musicapp.api.models.SongApi;
import com.app.musicapp.api.utils.ReflectionHelper;
import com.app.musicapp.config.Env;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
import com.fasterxml.jackson.databind.ObjectMapper;
*/

public class EntertainmentProvider {

    public static final String TAG = "Entertainment";

    public List<SongApi> getAllSongSync() {
        try {
            Observable<Response> fetchDt = Observable.create(
                    (ObservableOnSubscribe<Response>) emitter -> {
                        try {
                            String url = ApiConfig.BACKEND_API + ApiConfig.SONG_API;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            emitter.onNext(client.newCall(request).execute());
                        } catch (Exception e) {
                            emitter.onError(e); // In case there are network errors
                        }
                    });
            Response _res = fetchDt.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).blockingFirst();
            ApiResponse apiRes = ReflectionHelper._mapper().readValue(_res.body().string(), ApiResponse.class);
            if (!apiRes.getSuccess()) {
                throw new Exception("Can't get list song");
            }
            List<HashMap<String, Object>> lstSong = new ArrayList<>();
            List<SongApi> lstResult = new ArrayList<>();
            lstSong = ReflectionHelper._mapper().convertValue(apiRes.getData(), lstSong.getClass());
            for (HashMap<String, Object> item : lstSong) {
                SongApi newItem = ReflectionHelper._mapper().convertValue(item, SongApi.class);
                lstResult.add(newItem);
            }
            return lstResult;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new ArrayList<>();
        }

    }

    public Observable<List<SongApi>> getAllSongAsync() {
        try {
            Observable<Response> fetchDt = Observable.create(
                    (ObservableOnSubscribe<Response>) emitter -> {
                        try {
                            String url = ApiConfig.BACKEND_API + ApiConfig.SONG_API;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            emitter.onNext(client.newCall(request).execute());
                        } catch (Exception e) {
                            emitter.onError(e); // In case there are network errors
                        }
                    });
            Observable<List<SongApi>> lstSongObj = (Observable<List<SongApi>>) fetchDt.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(val -> {
                ApiResponse apiRes = ReflectionHelper._mapper().readValue(val.body().string(), ApiResponse.class);
                List<HashMap<String, Object>> lstSong = new ArrayList<>();
                List<SongApi> lstResult = new ArrayList<>();
                lstSong = ReflectionHelper._mapper().convertValue(apiRes.getData(), lstSong.getClass());
                for (HashMap<String, Object> item : lstSong) {
                    SongApi newItem = ReflectionHelper._mapper().convertValue(item, SongApi.class);
                    lstResult.add(newItem);
                }
                return lstResult;
            });
            return lstSongObj;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public SongApi getSongByIdSync(int SongId) {
        try {
            Observable<Response> fetchDt = Observable.create(
                    (ObservableOnSubscribe<Response>) emitter -> {
                        try {
                            String url = ApiConfig.BACKEND_API + ApiConfig.SONG_API + "/" + SongId;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            emitter.onNext(client.newCall(request).execute());
                        } catch (Exception e) {
                            emitter.onError(e); // In case there are network errors
                        }
                    });
            Response _res = fetchDt.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).blockingFirst();
            ApiResponse apiRes = ReflectionHelper._mapper().readValue(_res.body().string(), ApiResponse.class);
            if (!apiRes.getSuccess()) {
                throw new Exception("Can't get song data");
            }
            SongApi _song = new SongApi();
            _song = ReflectionHelper._mapper().convertValue(apiRes.getData(), SongApi.class);
            return _song;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new SongApi();
        }
    }

    public Observable<Boolean> downloadSongAsync(String urlDownload) {
        try {
            Observable<Response> fetchDt = Observable.create(
                    (ObservableOnSubscribe<Response>) emitter -> {
                        try {
                            String url = urlDownload;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            emitter.onNext(client.newCall(request).execute());
                        } catch (Exception e) {
                            emitter.onError(e); // In case there are network errors
                        }
                    });
            return fetchDt.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(val -> {
                        try {
                            InputStream is = val.body().byteStream();
                            String filename = val.header("content-disposition").replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
                            BufferedInputStream input = new BufferedInputStream(is);
                            String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Env.MUSIC_APP_PATH + "/";
                            Files.createDirectories(Paths.get(downloadPath));
                            OutputStream output = new FileOutputStream(downloadPath + filename);
                            long contentLength = val.body().contentLength();
                            byte[] data = new byte[1024];
                            long total = 0;
                            int count = 0;
                            while ((count = is.read(data)) != -1) {
                                total += count;
                                int progress = (int) ((total * 100) / contentLength);
                                output.write(data, 0, count);
                            }

                            output.flush();
                            output.close();
                            input.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public Observable<Boolean> downloadSongAsync(String urlDownload, NotificationCompat.Builder _builder, NotificationManager _manager, AppCompatActivity activity, int notiId) {
        try {
            Observable<Response> fetchDt = Observable.create(
                    (ObservableOnSubscribe<Response>) emitter -> {
                        try {
                            String url = urlDownload;
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            emitter.onNext(client.newCall(request).execute());
                        } catch (Exception e) {
                            emitter.onError(e); // In case there are network errors
                        }
                    });
            return fetchDt.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map(val -> {
                        try {
                            InputStream is = val.body().byteStream();
                            String filename = val.header("content-disposition").replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
                            BufferedInputStream input = new BufferedInputStream(is);
                            String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Env.MUSIC_APP_PATH + "/";
                            Files.createDirectories(Paths.get(downloadPath));
                            OutputStream output = new FileOutputStream(downloadPath + filename);
                            long contentLength = val.body().contentLength();
                            byte[] data = new byte[1024];
                            long total = 0;
                            int count = 0;
                            while ((count = is.read(data)) != -1) {
                                total += count;
                                int progress = (int) ((total * 100) / contentLength);
                                if (_builder != null && _manager != null) {
                                    _builder.setProgress(100, progress, false).setContentText( activity.getResources().getString(R.string.percent) +": " + progress + "/100");
                                    _manager.notify(notiId, _builder.build());
                                }
                                output.write(data, 0, count);
                            }
                            output.flush();
                            output.close();
                            input.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

}
