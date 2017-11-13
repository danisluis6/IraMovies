package vn.enclave.iramovies.services;

import android.arch.core.BuildConfig;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IraMoviesWebAPIs {
    /**
     * Get List Faxes
     */
    @GET("incomingFax")
    Call<MoviesResponse> getListFaxes(@Header("access-token") String token, @Body JsonObject jsonObject);

    class Factory {
        public static IraMoviesWebAPIs create(Context context) {
            String baseUrl = BuildConfig.BUILD_TYPE;
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(IraMoviesWebAPIs.class);
        }
    }
}
