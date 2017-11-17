package vn.enclave.iramovies.ui.fragments.Favorite;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;
import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class FavoritesModel implements IFavoritesModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMoviesPresenter
     */
    private IFavoritesPresenter mIFavoritesPresenter;

    /**
     * IraMoviesWebAPIs
     */
    private IraMoviesWebAPIs mApiService;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    FavoritesModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }


    @Override
    public void attachView(IFavoritesPresenter view) {
        mIFavoritesPresenter = view;
    }

    @Override
    public void getMoviesFromLocal() {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... params) {
                return mAppDatabase.getMovieDao().getMovies();
            }

            @Override
            protected void onPostExecute(List<Movie> groupMovies) {
                if (!groupMovies.isEmpty()) {
                    mIFavoritesPresenter.onSuccess(groupMovies);
                } else {
                    mIFavoritesPresenter.onFailure(mContext.getResources().getString(R.string.cannot_get_data));
                }
            }
        }.execute();
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
                } else {
                    Toast.makeText(mContext, "Delete Movie failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(movie);
    }
}
