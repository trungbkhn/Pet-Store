package com.example.animalfood.WebService;

import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.example.animalfood.Model.PlacesResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // https://fish-species.p.rapidapi.com/fish_api/fishes
    // https://648fb67875a96b6644454791.mockapi.io/api/get-animals/Birds
    // https://648fb67875a96b6644454791.mockapi.io/api/get-animals/Cats
    // https://api.thedogapi.com/v1/images/search?has_breeds=true&limit=50&api_key=live_PPrPjMxsZc3NddMGmsK0v87XK5hh9nOBoFxEWwo0YN7iAUQpLFyHHf6IKhutLwbz
    final static String BASE_CAT_URL = "https://api.thecatapi.com/";
    final static String BASE_DOG_URL = "https://api.thedogapi.com/";
    final static String BASE_BIRD_URL = "https://648fb67875a96b6644454791.mockapi.io/api/get-animals/";
    final static String BASE_FISH_URL = "https://fish-species.p.rapidapi.com/fish_api/";
    final static String apiKey = "AIzaSyDmjtFG00Trb4AIAnqfidORAER883F16FQ";
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) ;
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();
    private static OkHttpClient okHttpClientFish = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder()
                            .header("X-RapidAPI-Key", "f68cd1c151mshe8e45b48863632bp1f149ejsn84a1518c56ef");

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            })
            .build();

    private static Retrofit retrofitCat = new Retrofit.Builder().baseUrl(BASE_CAT_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static Retrofit retrofitDog = new Retrofit.Builder().baseUrl(BASE_DOG_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static Retrofit retrofitBird = new Retrofit.Builder().baseUrl(BASE_BIRD_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static Retrofit retrofitFish = new Retrofit.Builder().baseUrl(BASE_FISH_URL).client(okHttpClientFish)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static Retrofit retrofitPlace = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiService getApiServiceCat() {
        return retrofitCat.create(ApiService.class);
    }

    public static ApiService getApiServiceDog() {
        return retrofitDog.create(ApiService.class);
    }

    public static ApiService getApiServiceBird() {
        return retrofitBird.create(ApiService.class);
    }

    public static ApiService getApiServiceFish() {
        return retrofitFish.create(ApiService.class);
    }
    public static ApiService getNearbyPlaces() {
        return retrofitPlace.create(ApiService.class);
    }
}
