package vn.enclave.iramovies.ui.fragments.Detail;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.IraMovieWebAPIs;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 23/11/2017.
 */

public class MovieDetailModel implements IMovieDetailModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMovieDetailPresenter
     */
    private IMovieDetailPresenter mIMovieDetailPresenter;

    /**
     * IraMovieWebAPIs
     */
    private IraMovieWebAPIs mApiService;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    private Call<CastAndCrewResponse> mCallerCastAndCrew;

    MovieDetailModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IMovieDetailPresenter view) {
        this.mIMovieDetailPresenter = view;
    }

    @Override
    public void getCastAndCrewFromApi(int movieId) {
        mCallerCastAndCrew = mApiService.getMovieDetail(String.valueOf(movieId), BuildConfig.THE_MOVIE_DB_API_TOKEN);
        mCallerCastAndCrew.enqueue(new Callback<CastAndCrewResponse>() {
            @Override
            public void onResponse(Call<CastAndCrewResponse> call, Response<CastAndCrewResponse> response) {
                if (response.isSuccessful()) {
                    CastAndCrewResponse castAndCrewResponse = response.body();
                    if (castAndCrewResponse != null) {
                        mIMovieDetailPresenter.onSuccess(castAndCrewResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<CastAndCrewResponse> call, Throwable t) {
                mIMovieDetailPresenter.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void deleteMovie(final Movie movie) {
        DeleteMovieAsyncTask mDeleteAsyncTask = new DeleteMovieAsyncTask(movie);
        mDeleteAsyncTask.execute(movie);
    }

    @Override
    public void cancelProcessing() {
        if (mCallerCastAndCrew != null && mCallerCastAndCrew.isExecuted() && !mCallerCastAndCrew.isCanceled()) {
            mCallerCastAndCrew.cancel();
            mIMovieDetailPresenter.onFailure(Constants.EMPTY_STRING);
        }
    }

    @Override
    public void addMovie(Movie movie) {
        AddMovieAsyncTask mAddAsyncTask = new AddMovieAsyncTask(movie);
        mAddAsyncTask.execute(movie);
    }

    @Override
    public void addReminder(Reminder reminder) {
        AddReminderAsyncTask mAddAsyncTask = new AddReminderAsyncTask(reminder);
        mAddAsyncTask.execute(reminder);
    }

    @Override
    public void getReminderMovie(int id) {
        new FindReminderAsyncTask().execute(id);
    }

    @Override
    public void updateReminder(Reminder reminder) {
        // Update reminder
        EditReminderAsyncTask mEditAsyncTask = new EditReminderAsyncTask(reminder);
        mEditAsyncTask.execute(reminder);
    }

    @Override
    public void getMovie(Integer id) {
        new FindMovieAsyncTask().execute(id);
    }

    private class EditReminderAsyncTask extends AsyncTask<Reminder, Void, Integer> {

        private Reminder mReminder;

        EditReminderAsyncTask(Reminder reminder) {
            this.mReminder = reminder;
        }

        @Override
        protected Integer doInBackground(Reminder... params) {
            return mAppDatabase.getReminderDao().updateReminders(params[0]);
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id > 0) {
                mIMovieDetailPresenter.updateReminderSuccess(mReminder);
            } else {
                Toast.makeText(mContext, "Update reminder failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class FindMovieAsyncTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... params) {
            return mAppDatabase.getMovieDao().getMovie(params[0]);
        }

        @Override
        protected void onPostExecute(Movie movie) {
            mIMovieDetailPresenter.findMovieSuccess(movie);
        }
    }

    private class FindReminderAsyncTask extends AsyncTask<Integer, Void, Reminder> {

        @Override
        protected Reminder doInBackground(Integer... params) {
            return mAppDatabase.getReminderDao().getReminderMovie(params[0]);
        }

        @Override
        protected void onPostExecute(Reminder reminder) {
            mIMovieDetailPresenter.findReminderSuccess(reminder);
        }
    }


    private class AddReminderAsyncTask extends AsyncTask<Reminder, Void, Long> {

        private Reminder reminder;

        AddReminderAsyncTask(Reminder reminder) {
            this.reminder = reminder;
        }

        @Override
        protected Long doInBackground(Reminder... params) {
            return mAppDatabase.getReminderDao().insertReminders(params[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                reminder.setId(Integer.parseInt(String.valueOf(id)));
                mIMovieDetailPresenter.addReminderSuccess(reminder);
            } else {
                Toast.makeText(mContext, "Add reminder failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddMovieAsyncTask extends AsyncTask<Movie, Void, Long> {

        private Movie movie;

        AddMovieAsyncTask(Movie movie) {
            this.movie = movie;
        }

        @Override
        protected Long doInBackground(Movie... params) {
            return mAppDatabase.getMovieDao().insertMovies(params[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                movie.setId(Integer.parseInt(String.valueOf(id)));
                mIMovieDetailPresenter.addMovieSuccess(movie);
            }
        }
    }

    private class DeleteMovieAsyncTask extends AsyncTask<Movie, Void, Integer> {

        private Movie movie;

        DeleteMovieAsyncTask(Movie movie) {
            this.movie = movie;
        }

        @Override
        protected Integer doInBackground(Movie... movies) {
            return mAppDatabase.getMovieDao().deleteMovies(movies);
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id > 0) {
                mIMovieDetailPresenter.deleteMovieSuccess(movie);
            }
        }
    }

}
