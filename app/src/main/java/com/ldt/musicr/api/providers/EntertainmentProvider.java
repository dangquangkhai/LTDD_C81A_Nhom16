package com.ldt.musicr.api.providers;

import android.util.Log;
import com.ldt.musicr.api.customModels.ApiResponse;
import com.ldt.musicr.api.models.Song;
import com.ldt.musicr.api.utils.ReflectionHelper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
import com.fasterxml.jackson.databind.ObjectMapper;
*/

public class EntertainmentProvider {

    public static final String TAG = "Entertainment";
    public Call getAllSong(Callback callback) {
        String BACKEND_API = "https://8r0mhcvmh3.execute-api.ap-southeast-1.amazonaws.com/dev";
        String SONG = "/entmt/song";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BACKEND_API + SONG)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public List<Song> getAllSongSync()
    {
        try {
            Observable<Response> fetchDt = Observable.create(
                (ObservableOnSubscribe<Response>) emitter -> {
                    try {
                        String BACKEND_API = "https://8r0mhcvmh3.execute-api.ap-southeast-1.amazonaws.com/dev";
                        String SONG = "/entmt/song";
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(BACKEND_API + SONG)
                                .build();
                        emitter.onNext(client.newCall(request).execute());
                    } catch(Exception e) {
                        emitter.onError(e); // In case there are network errors
                    }
                });
            Response _res = fetchDt.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).blockingFirst();
            ApiResponse apiRes =  ReflectionHelper._mapper().readValue(_res.body().string(), ApiResponse.class);
            if (!apiRes.getSuccess())
            {
                throw new Exception("Can't get list song");
            }
            List<HashMap<String, Object>> lstSong = new ArrayList<>();
            List<Song> lstResult = new ArrayList<>();
            lstSong = ReflectionHelper._mapper().convertValue(apiRes.getData(), lstSong.getClass());
            lstSong.forEach(item -> {
                Song newItem = ReflectionHelper._mapper().convertValue(item, Song.class);
                lstResult.add(newItem);
            });
            return lstResult;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return new ArrayList<>();
        }
    }
}
