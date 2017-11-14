package vn.enclave.iramovies.ui.fragments.Movie;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.services.response.MoviesResponse;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MoviesModel implements IMoviesModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMoviesPresenter
     */
    private IMoviesPresenter mIMoviesPresenter;

    /**
     * IraMoviesWebAPIs
     */
    private IraMoviesWebAPIs mApiService;


    MoviesModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
    }


    @Override
    public void attachView(IMoviesPresenter view) {
        mIMoviesPresenter = view;
    }

    @Override
    public void getMoviesFromApi() {
        Call<MoviesResponse> call = mApiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null) {
                        List<Movie> grouMoviesDatas = moviesResponse.getResults();
                        mIMoviesPresenter.onSuccess(grouMoviesDatas);
                    }
                } else  {
                    mIMoviesPresenter.onFailure(mContext.getString(R.string.cannot_get_data));
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                mIMoviesPresenter.onFailure(mContext.getString(R.string.cannot_get_data));
            }
        });
    }
}
