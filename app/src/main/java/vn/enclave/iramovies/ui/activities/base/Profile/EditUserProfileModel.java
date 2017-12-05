package vn.enclave.iramovies.ui.activities.base.Profile;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 04/12/2017.
 */

class EditUserProfileModel implements IEditUserProfileModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMoviePresenter
     */
    private IEditUserProfilePresenter mIEditUserProfilePresenter;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    EditUserProfileModel(Context context) {
        this.mContext = context;
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IEditUserProfilePresenter view) {
        this.mIEditUserProfilePresenter = view;
    }

    @Override
    public void addUser(User user) {
        AddUserAsyncTask mAddAsyncTask = new AddUserAsyncTask(user);
        mAddAsyncTask.execute(user);
    }

    @Override
    public void updateUser(User user) {
        EditUserAsyncTask mEditAsyncTask = new EditUserAsyncTask(user);
        mEditAsyncTask.execute(user);
    }

    private class EditUserAsyncTask extends AsyncTask<User, Void, Integer> {
        private User mUser;

        EditUserAsyncTask(User user) {
            this.mUser = user;
        }

        @Override
        protected Integer doInBackground(User... params) {
            return mAppDatabase.getUserDao().updateUsers(params[0]);
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id > 0) {
                mIEditUserProfilePresenter.onSuccess(mUser);
            } else {
                Toast.makeText(mContext, "Update user failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddUserAsyncTask extends AsyncTask<User, Void, Long> {

        private User mUser;

        AddUserAsyncTask(User user) {
            this.mUser = user;
        }

        @Override
        protected Long doInBackground(User... params) {
            return mAppDatabase.getUserDao().insertUsers(params[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                mIEditUserProfilePresenter.onSuccess(mUser);
            } else {
                Toast.makeText(mContext, "Add new user failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
