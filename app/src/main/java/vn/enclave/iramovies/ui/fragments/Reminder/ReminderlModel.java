package vn.enclave.iramovies.ui.fragments.Reminder;

import android.arch.persistence.room.Room;
import android.content.Context;

import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.services.IraMovieWebAPIs;

/**
 *
 * Created by lorence on 23/11/2017.
 */

public class ReminderlModel implements IReminderModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IReminderPresenter
     */
    private IReminderPresenter mReminderPresenter;

    /**
     * IraMovieWebAPIs
     */
    private IraMovieWebAPIs mApiService;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    public ReminderlModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IReminderPresenter view) {
        this.mReminderPresenter = view;
    }

    @Override
    public void getReminderDetail(int id) {

    }

    @Override
    public void onSuccess(Movie movie) {

    }

    @Override
    public void onFailure(String message) {

    }
}
