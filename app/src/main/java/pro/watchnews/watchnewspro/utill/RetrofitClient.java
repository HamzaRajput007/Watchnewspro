package pro.watchnews.watchnewspro.utill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import pro.watchnews.watchnewspro.interfaces.RemoteClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private static Retrofit retrofit;
    private static String BASE_URL;
    private RetrofitClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public static void connectTo(String url){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        BASE_URL = url;
    }

    public RemoteClient getApi(){
        return retrofit.create(RemoteClient.class);
    }
}
