package vn.enclave.iramovies.services;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import vn.enclave.iramovies.services.response.MoviesResponse;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IraMoviesWebAPIs {

    /**
     * Get Popular Movies
     *
     * @Run: http://api.themoviedb.org/3/movie/popular?api_key=2d390f2deaa23e8b65d42a80beb8c1bd
     * => Done
     */
    @GET(IraMoviesInfoAPIs.Catogories.Popular)
    Call<MoviesResponse> getPopularMovies(@Header(IraMoviesInfoAPIs.Fields.API_KEY) String apiKey);

    /**
     * Get Top Rated Movies
     * @Run: http://api.themoviedb.org/3/movie/top_rated?api_key=2d390f2deaa23e8b65d42a80beb8c1bd
     * => Done
     */
    @GET(IraMoviesInfoAPIs.Catogories.Top_Rated)
    Call<MoviesResponse> getTopRatedMovies(@Header(IraMoviesInfoAPIs.Fields.API_KEY) String apiKey);

    /**
     * Get Now Playing Movies
     * @Run: http://api.themoviedb.org/3/movie/now_playing?api_key=2d390f2deaa23e8b65d42a80beb8c1bd
     * => Done
     */
    @GET(IraMoviesInfoAPIs.Catogories.Now_playing)
    Call<MoviesResponse> getNowPlayingMovies(@Header(IraMoviesInfoAPIs.Fields.API_KEY) String apiKey);

    /**
     * Get Upcoming Movies
     * @Run: http://api.themoviedb.org/3/movie/upcoming?api_key=2d390f2deaa23e8b65d42a80beb8c1bd
     * => Done
     */
    @GET(IraMoviesInfoAPIs.Catogories.Upcoming)
    Call<MoviesResponse> getUpcomingMovies(@Header(IraMoviesInfoAPIs.Fields.API_KEY) String apiKey);

    class Factory {
        public static IraMoviesWebAPIs create(Context context) {
            String baseUrl = IraMoviesInfoAPIs.BASE_URL;
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
