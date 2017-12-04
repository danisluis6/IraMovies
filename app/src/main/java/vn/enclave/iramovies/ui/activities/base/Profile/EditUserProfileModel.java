package vn.enclave.iramovies.ui.activities.base.Profile;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public class EditUserProfileModel implements IEditUserProfileModel{

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
        AddAsyncTask mAddAsyncTask = new AddAsyncTask(user);
        mAddAsyncTask.execute(user);
    }

    private class AddAsyncTask extends AsyncTask<User, Void, Long> {

        private User mUser;

        AddAsyncTask(User user) {
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
                Toast.makeText(mContext, "Add Movie failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
