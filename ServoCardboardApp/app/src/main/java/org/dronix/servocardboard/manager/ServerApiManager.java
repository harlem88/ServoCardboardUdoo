package org.dronix.servocardboard.manager;

import com.squareup.okhttp.OkHttpClient;

import org.dronix.servocardboard.BuildConfig;
import org.dronix.servocardboard.model.Direction;
import org.dronix.servocardboard.model.Response;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

public class ServerApiManager {

    private ServerApiService mServerApiService;


    public ServerApiManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.SERVER_URL + ":8888")
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setLog(new AndroidLog("DROIDCARDBOARD"))
                .setClient(new OkClient(new OkHttpClient()))
                .build();

        mServerApiService = restAdapter.create(ServerApiService.class);
    }

    public void move( Direction direction){
        mServerApiService.move(direction).subscribe();
    }

    public interface ServerApiService {

        @POST("/api/direction")
        Observable<Response> move(@Body Direction direction) ;
    }
}
