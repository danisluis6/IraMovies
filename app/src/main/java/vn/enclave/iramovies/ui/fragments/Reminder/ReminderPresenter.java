package vn.enclave.iramovies.ui.fragments.Reminder;

import android.content.Context;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Reminder;

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
    public void updateReminder(Reminder reminder) {
        mReminderModel.updateReminder(reminder);
    }

    @Override
    public void onSuccess(Reminder reminder) {
        if (mReminderView != null) {
            mReminderView.onSuccess(reminder);
        }
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void getListReminder() {
        mReminderModel.getListReminder();
    }

    @Override
    public void onReminderSuccess(List<Reminder> groupReminders) {
        if (mReminderView != null) {
            mReminderView.onReminderSuccess(groupReminders);
        }
    }
}