package vn.enclave.iramovies.services;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.services.response.MovieResponse;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IraMovieWebAPIs {

    /**
     * Get Popular Movies
     */
    @GET(IraMovieInfoAPIs.Categories.Popular)
    Call<MovieResponse> getPopularMovies(@Query(IraMovieInfoAPIs.Fields.API_KEY) String apiKey, @Query(IraMovieInfoAPIs.Fields.PAGE) int page);

    /**
     * Get Top Rated Movies
     */
    @GET(IraMovieInfoAPIs.Categories.Top_Rated)
    Call<MovieResponse> getTopRatedMovies(@Query(IraMovieInfoAPIs.Fields.API_KEY) String apiKey, @Query(IraMovieInfoAPIs.Fields.PAGE) int page);

    /**
     * Get Now Playing Movies
     */
    @GET(IraMovieInfoAPIs.Categories.Now_playing)
    Call<MovieResponse> getNowPlayingMovies(@Query(IraMovieInfoAPIs.Fields.API_KEY) String apiKey, @Query(IraMovieInfoAPIs.Fields.PAGE) int page);

    /**
     * Get Upcoming Movies
     */
    @GET(IraMovieInfoAPIs.Categories.Upcoming)
    Call<MovieResponse> getUpcomingMovies(@Query(IraMovieInfoAPIs.Fields.API_KEY) String apiKey, @Query(IraMovieInfoAPIs.Fields.PAGE) int page);

    @GET(IraMovieInfoAPIs.Categories.Detail)
    Call<CastAndCrewResponse> getMovieDetail(@Path(value = "movie_id", encoded = true) String movie_id, @Query(IraMovieInfoAPIs.Fields.API_KEY) String apiKey);

    class Factory {
        public static IraMovieWebAPIs create(Context context) {
            String baseUrl = IraMovieInfoAPIs.BASE_URL;
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
            return retrofit.create(IraMovieWebAPIs.class);
        }
    }
}
