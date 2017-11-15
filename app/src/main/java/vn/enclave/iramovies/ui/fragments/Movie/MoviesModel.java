package vn.enclave.iramovies.ui.fragments.Movie;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.SessionManager;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;
import vn.enclave.iramovies.services.response.MovieData;
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
    public void getMoviesFromApi(int mPageIndex) {
        Call<MoviesResponse> call = mApiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, mPageIndex);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.isSuccessful()) {
                    MoviesResponse moviesResponse = response.body();
                    if (moviesResponse != null) {
                        List<MovieData> grouMoviesDatas = moviesResponse.getResults();
                        mIMoviesPresenter.onSuccess(grouMoviesDatas);
                        SessionManager.getInstance(mContext).setTotalPages(moviesResponse.getTotalPages());
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

    // Work with local database ROOM
    @Override
    public void addMovie(MovieData movieData) {
/*        new AsyncTask<MoviesResponse, Void, Long>() {
            @Override
            protected Long doInBackground(MoviesResponse... params) {
                return IRApplication.getInstance().initAppDatabase().getMovieDao().insertMovies(convertMovieData(params[0]));
            }

            @Override
            protected void onPostExecute(Long id) {
                if (id > 0) {
                    Toast.makeText(mContext, "Add fds successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Add Contact failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(movieData);*/
    }


}
