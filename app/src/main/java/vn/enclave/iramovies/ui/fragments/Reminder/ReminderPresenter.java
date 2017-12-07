package vn.enclave.iramovies.ui.fragments.Reminder;

import android.content.Context;

import vn.enclave.iramovies.local.storage.entity.Movie;

/**
 *
 * Created by lorence on 23/11/2017.
 */

class ReminderPresenter implements IReminderPresenter{

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IReminderView
     */
    private IReminderView mReminderView;

    /**
     * IMovieDetailModel
     */
    private IReminderModel mReminderModel;

    ReminderPresenter(Context context) {
        mContext = context;
        mReminderModel = new ReminderlModel(mContext);
        mReminderModel.attachView(this);
    }

    @Override
    public void attachView(IReminderView view) {
        this.mReminderView = view;
    }


    @Override
    public void getReminderDetail(int id) {
        mReminderModel.getReminderDetail(id);
    }

    @Override
    public void onSuccess(Movie movie) {

    }

    @Override
    public void onFailure(String message) {

    }
}
