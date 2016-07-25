package com.feicuiedu.demotreasure.net;



import com.feicuiedu.demotreasure.Home.TreasureApi;
import com.feicuiedu.demotreasure.User.UserApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/13.
 */
public class netClient {
    private Retrofit retrofit;
    private Gson gson;
    private static netClient netClient;
    public static final String BASE_URL = "http://admin.syfeicuiedu.com";

    private final OkHttpClient client;

    private netClient(){
        gson= new GsonBuilder().setLenient().create();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl("http://admin.syfeicuiedu.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static netClient getInstance(){
        if(netClient == null){
            netClient = new netClient();
        }
        return netClient;

    }
    private  TreasureApi treasureApi;
    /**
     * 获取宝藏API对象
     */
    public TreasureApi getTreasureApi() {
        if (treasureApi == null) {
            treasureApi = retrofit.create(TreasureApi.class);
        }
        return treasureApi;
    }


    public OkHttpClient getClient(){
        return client;
    }
    private UserApi userApi;
    public UserApi getUserApi(){
        if(userApi == null){
            userApi= retrofit.create(UserApi.class);
        }
        return userApi;
    }


}
