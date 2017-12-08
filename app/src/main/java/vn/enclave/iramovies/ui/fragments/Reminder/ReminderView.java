package vn.enclave.iramovies.ui.fragments.Reminder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.fragments.Detail.MovieDetailView;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Reminder.adapter.ReminderListAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;

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
                    MovieDetailView movieDetailView = new MovieDetailView();
                    movieDetailView.setArguments(getMovieBundle(reminder));
                    mReminderViewInterface.getMovieDetailFragment(movieDetailView, reminder);
                }
            });
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        rcvReminders.setLayoutManager(mLayoutManager);
        rcvReminders.setAdapter(mReminderListAdapter);
    }

    private Bundle getMovieBundle(Reminder reminder) {
        Bundle mBundle = new Bundle();
        mBundle.putString(Constants.Bundle.TYPE, Constants.Bundle.REMINDER);
        mBundle.putInt(DatabaseInfo.Reminder.COLUMN_ID, reminder.getId());
        mBundle.putString(DatabaseInfo.Reminder.COLUMN_TITLE, reminder.getTitle());
        mBundle.putString(DatabaseInfo.Reminder.COLUMN_POSTER_PATH, reminder.getPosterPath());
        mBundle.putString(DatabaseInfo.Reminder.COLUMN_RELEASE_DATE, reminder.getReleaseDate());
        mBundle.putDouble(DatabaseInfo.Reminder.COLUMN_VOTE_AVERAGE, reminder.getVoteAverage());
        mBundle.putString(DatabaseInfo.Reminder.COLUMN_OVERVIEW, reminder.getOverview());
        mBundle.putInt(DatabaseInfo.Reminder.COLUMN_FAVORITE, reminder.getFavorite());
        mBundle.putString(DatabaseInfo.Reminder.COLUMN_REMINDER_DATE, reminder.getReminderDate());
        return mBundle;
    }

    public void getReminderList() {
        mReminderPresenter.getListReminder();
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

    public void openReminderDetail(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_reminder_detail, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        fragmentManager.executePendingTransactions();
    }

    public void updateStarOnStorage(Movie movie, String reminderDate) {
        if (mReminderPresenter != null) {
            mReminderPresenter.updateReminder(getReminderUpdated(movie, reminderDate));
        }
    }

    public Reminder getReminderUpdated(Movie movie, String reminderDate) {
        Reminder reminder = new Reminder();
        reminder.setId(movie.getId());
        reminder.setFavorite(movie.getFavorite());
        reminder.setVoteAverage(movie.getVoteAverage());
        reminder.setPosterPath(movie.getPosterPath());
        reminder.setOverview(movie.getOverview());
        reminder.setReleaseDate(movie.getReleaseDate());
        reminder.setTitle(movie.getTitle());
        reminder.setReminderDate(reminderDate);
        return reminder;
    }

    @Override
    public void onSuccess(Reminder reminder) {
        mReminderListAdapter.updateReminder(reminder);
    }

    @Override
    public void onFailure(String message) {
    }

    @Override
    public void onReminderSuccess(List<Reminder> groupReminders) {
        mReminderListAdapter.setReminders(groupReminders);
    }

    public void onRefresh() {
        if (mReminderPresenter != null) {
            mReminderPresenter.getListReminder();
        }
        onResume();
    }

    public interface ReminderViewInterface {
        void onDestroy();
        void getMovieDetailFragment(MovieDetailView movieDetailView, Reminder reminder);
    }

    public void setReminderViewInterface(ReminderViewInterface reminderViewInterface) {
        this.mReminderViewInterface = reminderViewInterface;
    }
}

