package vn.enclave.iramovies.ui.fragments.Reminder;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.local.storage.entity.Reminder;

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
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    public ReminderlModel(Context context) {
        mContext = context;
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IReminderPresenter view) {
        this.mReminderPresenter = view;
    }

    @Override
    public void updateReminder(Reminder reminder) {
        EditUserAsyncTask mEditAsyncTask = new EditUserAsyncTask(reminder);
        mEditAsyncTask.execute(reminder);
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
                    mReminderPresenter.onReminderSuccess(groupReminders);
                } else {
                    mReminderPresenter.onFailure(mContext.getResources().getString(R.string.cannot_get_data));
                }
            }
        }.execute();
    }

    private class EditUserAsyncTask extends AsyncTask<Reminder, Void, Integer> {

        private Reminder mReminder;

        EditUserAsyncTask(Reminder reminder) {
            this.mReminder = reminder;
        }

        @Override
        protected Integer doInBackground(Reminder... params) {
            return mAppDatabase.getReminderDao().updateReminders(params[0]);
        }

        @Override
        protected void onPostExecute(Integer id) {
            if (id > 0) {
                mReminderPresenter.onSuccess(mReminder);
            } else {
                Toast.makeText(mContext, "Update reminder failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
