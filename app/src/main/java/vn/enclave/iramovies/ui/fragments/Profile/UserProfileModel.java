package vn.enclave.iramovies.ui.fragments.Profile;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public class UserProfileModel implements IUserProfileModel{

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMoviePresenter
     */
    private IUserProfilePresenter mUserProfilePresenter;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    UserProfileModel(Context context) {
        this.mContext = context;
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IUserProfilePresenter view) {
        this.mUserProfilePresenter = view;
    }

    @Override
    public void getUser() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... params) {
                return mAppDatabase.getUserDao().getUsers();
            }

            @Override
            protected void onPostExecute(List<User> groupMovies) {
                if (!groupMovies.isEmpty()) {
                    mUserProfilePresenter.onSuccess(groupMovies);
                } else {
                    mUserProfilePresenter.onFailure(mContext.getResources().getString(R.string.cannot_get_data));
                }
            }
        }.execute();
    }

    @Override
    public void getListReminder() {
        new AsyncTask<Void, Void, List<Reminder>>() {
            @Override
            protected List<Reminder> doInBackground(Void... params) {
                return mAppDatabase.getReminderDao().getReminders();
            }

            @Override
            protected void onPostExecute(List<Reminder> groupReminders) {
                if (!groupReminders.isEmpty()) {
                    mUserProfilePresenter.onReminderSuccess(groupReminders);
                } else {
                    mUserProfilePresenter.onFailure(mContext.getResources().getString(R.string.cannot_get_data));
                }
            }
        }.execute();
    }
}
