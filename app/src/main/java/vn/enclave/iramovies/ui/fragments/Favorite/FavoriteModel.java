package vn.enclave.iramovies.ui.fragments.Favorite;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.IraMovieWebAPIs;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class FavoriteModel implements IFavoriteModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMoviesPresenter
     */
    private IFavoritePresenter mFavoritesPresenter;

    /**
     * IraMovieWebAPIs
     */
    private IraMovieWebAPIs mApiService;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    FavoriteModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }


    @Override
    public void attachView(IFavoritePresenter view) {
        mFavoritesPresenter = view;
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
                    mFavoritesPresenter.onSuccess(groupMovies);
                } else {
                    mFavoritesPresenter.onFailure(mContext.getResources().getString(R.string.cannot_get_data));
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
    public void deleteMovie(final Movie movie) {
        new AsyncTask<Movie, Void, Integer>() {
            @Override
            protected Integer doInBackground(Movie... movies) {
                return mAppDatabase.getMovieDao().deleteMovies(movies);
            }

            @Override
            protected void onPostExecute(Integer id) {
                if (id > 0) {
                    mFavoritesPresenter.deleteSuccess(movie);
                } else {
                    Toast.makeText(mContext, "Delete Movie failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(movie);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        EditReminderAsyncTask mEditReminderAsyncTask = new EditReminderAsyncTask(reminder);
        mEditReminderAsyncTask.execute(reminder);
    }

    private class EditReminderAsyncTask extends AsyncTask<Reminder, Void, Integer> {

        private Reminder mReminder;

        EditReminderAsyncTask(Reminder reminder) {
            this.mReminder = reminder;
        }

        @Override
        protected Integer doInBackground(Reminder... params) {
            return mAppDatabase.getReminderDao().updateReminder(params[0].getFavorite(), params[0].getId());
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id > 0) {
                mFavoritesPresenter.onUpdatedReminderSuccess(mReminder);
            }
        }
    }

}
