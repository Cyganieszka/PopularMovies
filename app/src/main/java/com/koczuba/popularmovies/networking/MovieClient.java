package com.koczuba.popularmovies.networking;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import com.koczuba.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieClient {


    private static final String API_KEY= BuildConfig.MOVIES_DB_API_KEY;
    private final static String SIZE_w185 = "w185";
    private final static String API_KEY_PARAM = "api_key";

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create());

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl url = original.url()
                        .newBuilder()
                        .addQueryParameter(API_KEY_PARAM, API_KEY)
                        .build();
                Request request = original.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }


    private static URL getUrlFromUri(Uri uri){
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildPosterUrl(String poster_path) {

        Uri uri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon()
                .appendEncodedPath(SIZE_w185)
                .appendEncodedPath(poster_path).build();

        return getUrlFromUri(uri);
    }
}