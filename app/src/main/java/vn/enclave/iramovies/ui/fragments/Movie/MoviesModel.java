package vn.enclave.iramovies.ui.fragments.Movie;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.SessionManager;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.services.response.MoviesResponse;
import vn.enclave.iramovies.utilities.Constants;

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

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    MoviesModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
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
                        List<Movie> grouMoviesDatas = moviesResponse.getResults();
                        mIMoviesPresenter.onSuccess(updateStatusFavorite(grouMoviesDatas));
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

    private List<Movie> updateStatusFavorite(final List<Movie> movies) {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... params) {
                return mAppDatabase.getMovieDao().getMovies();
            }

            @Override
            protected void onPostExecute(List<Movie> groupMovies) {
                for (int i = 0; i < movies.size(); i++) {
                    for (int j = 0; j < groupMovies.size(); j++) {
                        if (movies.get(i).getId().equals(groupMovies.get(j).getId())) {
                            movies.get(i).setFavorite(Constants.Favorites.FAVORITE);
                            break;
                        }
                    }
                }
            }
        }.execute();
        return movies;
    }

    // Work with local database ROOM
    @Override
    public void addMovie(Movie movie) {
        new AsyncTask<Movie, Void, Long>() {
            @Override
            protected Long doInBackground(Movie... params) {
                return mAppDatabase.getMovieDao().insertMovies(params[0]);
            }

            @Override
            protected void onPostExecute(Long id) {
                if (id > 0) {
                    Toast.makeText(mContext, "Add Movie successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Add Movie failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        new AsyncTask<Movie, Void, Integer>() {
            @Override
            protected Integer doInBackground(Movie... movies) {
                return mAppDatabase.getMovieDao().deleteMovies(movies);
            }

            @Override
            protected void onPostExecute(Integer id) {
                if (id > 0) {
                    Toast.makeText(mContext, "Delete Movie successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Delete Movie failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(movie);
    }

}
