package vn.enclave.iramovies.ui.fragments.Reminder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.fragments.Detail.MovieDetailView;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Movie.MoviePresenter;
import vn.enclave.iramovies.ui.fragments.Reminder.adapter.ReminderListAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 * @Run: https://www.mkyong.com/android/android-webview-example/
 */

public class ReminderView extends IRBaseFragment implements IReminderView {

    @BindView(R.id.rcvReminders)
    RecyclerView rcvReminders;

    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;

    /**
     * Work with MVP
     */
    private ReminderPresenter mReminderPresenter;
    private ReminderViewInterface mReminderViewInterface;
    private ReminderListAdapter mReminderListAdapter;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

    @Override
    public void fragmentCreated() {
        initViews();
        getReminderList();
    }

    private void initViews() {
        mReminderPresenter = new ReminderPresenter(mActivity);
        mReminderPresenter.attachView(this);
        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (rcvReminders != null) {
            rcvReminders.setHasFixedSize(true);
        }
        if (mReminderListAdapter == null) {
            mReminderListAdapter = new ReminderListAdapter(mActivity, new ArrayList<Reminder>(), new ReminderListAdapter.OpenReminderDetail() {
                @Override
                public void openReminder(Reminder reminder) {
                    if (Utils.isInternetOn(mActivity)) {
                        mReminderPresenter.getReminderDetail(reminder.getId());
                    }
                }
            });
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        rcvReminders.setLayoutManager(mLayoutManager);
        rcvReminders.setAdapter(mReminderListAdapter);
    }

    public void getReminderList() {
        List<Reminder> groupReminders = new ArrayList<>();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            groupReminders = bundle.getParcelableArrayList(Constants.Parcelable.LIST_REMINDER);
        }
        mReminderListAdapter.setReminders(groupReminders);
    }

    @Override
    public void onDetach() {
        mReminderViewInterface.onDestroy();
        super.onDetach();
    }

    public void reload(Reminder reminder, boolean isUpdated) {
        if (mReminderListAdapter != null) {
            if (isUpdated) {
                mReminderListAdapter.updateReminder(reminder);
            } else {
                mReminderListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSuccess(Movie movie) {
        MovieDetailView mDetailView = new MovieDetailView();
    }

    @Override
    public void onFailure(String message) {

    }

    public interface ReminderViewInterface {
        void onDestroy();
    }

    public void setReminderViewInterface(ReminderViewInterface reminderViewInterface) {
        this.mReminderViewInterface = reminderViewInterface;
    }
}

