package vn.enclave.iramovies.ui.fragments.Detail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.ui.fragments.Detail.adapter.MovieDetailAdapter;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Movie.bean.CastCrew;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 *
 * Created by lorence on 23/11/2017.
 */

/**
 * @Run: https://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments
 * => Done
 *
 * @Run: How to disable past dates in Android date picker?
 * => Done
 */

public class MovieDetailView extends IRBaseFragment implements IMovieDetailView {

    @BindView(R.id.imvFavorite)
    ImageView imvFavorite;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.tvRating)
    TextView tvRating;

    @BindView(R.id.imvThumnail)
    ImageView imvThumnail;

    @BindView(R.id.rcvCast)
    RecyclerView rcvCast;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.tvReminder)
    TextView tvReminder;

    /**
     * Work with MVP
     */
    private MovieDetailPresenter mDetailMoviePresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReminderInterface mReminderInterface;
    private  MovieDetailInterface  mMovieDetailInterface;
    private UpdateTitleOnReminderView mUpdateTitleOnReminderView;

    private Movie mMovie;
    private Reminder mReminder;
    private boolean mIsFavorite;
    private boolean mIsUpdateReminder;
    private boolean mIsReminder = false;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void fragmentCreated() {
        setMovie(getMovieDetail());
        initAtribute();
        displayMovieDetail(getMovie());
        getCastAndCrew();
        if (!mIsReminder) {
            getReminderMovie(getMovie().getId());
        } else {
            updatedReminder(getMovie());
        }
    }

    private void updatedReminder(Movie movie) {
        Reminder reminder = new Reminder();
        reminder.setId(movie.getId());
        reminder.setTitle(movie.getTitle());
        reminder.setReminderDate(tvReminder.getText().toString());
        reminder.setOverview(movie.getOverview());
        reminder.setPosterPath(movie.getPosterPath());
        reminder.setFavorite(movie.getFavorite());
        reminder.setReleaseDate(movie.getReleaseDate());
        reminder.setVoteAverage(movie.getVoteAverage());
        setReminder(reminder);
    }

    private void getReminderMovie(int id) {
        mDetailMoviePresenter.getReminderMovie(id);
    }

    private TimePickerDialog mTimePickerDialog;

    @OnClick(R.id.btnReminder)
    public void openDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mTimePickerDialog.show();
            }

        };

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateDisplay(myCalendar);
            }
        };

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(mActivity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        mTimePickerDialog = new TimePickerDialog(getActivity(), time,
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
        mDatePickerDialog.show();
    }

    private void updateDisplay(Calendar myCalendar) {
        // Check invalid
        if (System.currentTimeMillis() > myCalendar.getTime().getTime()) {
            Utils.Toast.showToast(mActivity, getString(R.string.error_pick_date));
        } else {
            String myFormat = "yyyy/MM/dd HH:mm"; // In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            tvReminder.setText(sdf.format(myCalendar.getTime()));
            saveReminderInStorage(tvReminder.getText().toString());
        }
    }

    private void saveReminderInStorage(String timeReminder) {
        setReminder(getInfoReminder(timeReminder));
        if (Utils.isInternetOn(mActivity)) {
            if (mIsUpdateReminder) {
                mDetailMoviePresenter.updateReminder(getReminder());
            } else {
                mDetailMoviePresenter.addReminder(getReminder());
            }
        } else {
            Utils.Toast.showToast(mActivity, getString(R.string.no_internet_connection));
        }
    }

    @OnClick(R.id.imvFavorite)
    public void onClick() {
        if (Utils.isDoubleClick()) {
            return;
        }
        mIsFavorite = !mIsFavorite;
        imvFavorite.setImageResource(mIsFavorite ? R.drawable.ic_star_picked : R.drawable.ic_star);
        getMovie().setFavorite(mIsFavorite ? Constants.Favorites.FAVORITE : Constants.Favorites.DEFAULT);
        if (getMovie().getFavorite() == Constants.Favorites.DEFAULT) {
            mDetailMoviePresenter.deleteMovie(getMovie());
        } else {
            mDetailMoviePresenter.addMovie(getMovie());
        }
    }

    private void initAtribute() {
        mDetailMoviePresenter = new MovieDetailPresenter(mActivity);
        mDetailMoviePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mIsUpdateReminder = false;
        mIsFavorite = (getMovie().getFavorite() == Constants.Favorites.FAVORITE);
    }

    private void displayMovieDetail(Movie movie) {
        imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(String.valueOf(movie.getVoteAverage()) + Constants.Keyboards.FORWARD_SLASH + "10.0");
        tvOverview.setText(movie.getOverview());
        String poster = IraMovieInfoAPIs.Images.Thumbnail + movie.getPosterPath();
        Glide.with(mActivity)
                .load(poster)
                .placeholder(R.drawable.load)
                .into((imvThumnail));
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }

    public Movie getMovieDetail() {
        Movie movie = new Movie();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (TextUtils.equals(bundle.getString(Constants.Bundle.TYPE), Constants.Bundle.REMINDER)) {
                movie.setId(bundle.getInt(DatabaseInfo.Reminder.COLUMN_ID));
                movie.setTitle(bundle.getString(DatabaseInfo.Reminder.COLUMN_TITLE));
                movie.setPosterPath(bundle.getString(DatabaseInfo.Reminder.COLUMN_POSTER_PATH));
                movie.setOverview(bundle.getString(DatabaseInfo.Reminder.COLUMN_OVERVIEW));
                movie.setReleaseDate(bundle.getString(DatabaseInfo.Reminder.COLUMN_RELEASE_DATE));
                movie.setVoteAverage(bundle.getDouble(DatabaseInfo.Reminder.COLUMN_VOTE_AVERAGE));
                movie.setFavorite(bundle.getInt(DatabaseInfo.Reminder.COLUMN_FAVORITE));
                updateReminderOnUI(bundle.getString(DatabaseInfo.Reminder.COLUMN_REMINDER_DATE));
                mIsReminder = true;
            } else {
                movie.setId(bundle.getInt(DatabaseInfo.Movie.COLUMN_ID));
                movie.setTitle(bundle.getString(DatabaseInfo.Movie.COLUMN_TITLE));
                movie.setPosterPath(bundle.getString(DatabaseInfo.Movie.COLUMN_POSTER_PATH));
                movie.setOverview(bundle.getString(DatabaseInfo.Movie.COLUMN_OVERVIEW));
                movie.setReleaseDate(bundle.getString(DatabaseInfo.Movie.COLUMN_RELEASE_DATE));
                movie.setVoteAverage(bundle.getDouble(DatabaseInfo.Movie.COLUMN_VOTE_AVERAGE));
                movie.setFavorite(bundle.getInt(DatabaseInfo.Movie.COLUMN_FAVORITE));
                mIsReminder = false;
            }
        }
        return movie;
    }

    private void updateReminderOnUI(String title) {
        tvReminder.setText(title);
    }

    @Override
    public void onSuccess(CastAndCrewResponse castAndCrewResponse) {
        loadCastAndCrew(getPathOfCastAndCrew(castAndCrewResponse));
    }

    private void loadCastAndCrew(List<CastCrew> castCrewList) {
        MovieDetailAdapter movieDetailAdapter = new MovieDetailAdapter(mActivity, mActivity, castCrewList);
        rcvCast.setLayoutManager(mLayoutManager);
        rcvCast.setAdapter(movieDetailAdapter);
    }

    private List<CastCrew> getPathOfCastAndCrew(CastAndCrewResponse castAndCrewResponse) {
        List<CastCrew> mCastCrews = new ArrayList<>();
        for (int i = 0; i < castAndCrewResponse.getCast().size(); i++) {
            mCastCrews.add(new CastCrew(castAndCrewResponse.getCast().get(i).getName(), castAndCrewResponse.getCast().get(i).getProfilePath()));
        }
        for (int j = 0; j < castAndCrewResponse.getCrew().size(); j++) {
            mCastCrews.add(new CastCrew(castAndCrewResponse.getCrew().get(j).getName(), castAndCrewResponse.getCrew().get(j).getProfilePath()));
        }
        return mCastCrews;
    }

    @Override
    public void onFailure(String message) {
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        mMovieDetailInterface.updateCountStarOnMenu(getMovie().getFavorite());
        mMovieDetailInterface.refreshStarInFavoriteScreen(getMovie());
        mMovieDetailInterface.refreshStarInMovieScreen(getMovie());
        mMovieDetailInterface.refreshStarInDetailScreen(getMovie());
        mMovieDetailInterface.refreshStarInReminderDetail(getMovie(), getReminder().getReminderDate());
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        mMovieDetailInterface.updateCountStarOnMenu(getMovie().getFavorite());
        mMovieDetailInterface.refreshStarInFavoriteScreen(getMovie());
        mMovieDetailInterface.refreshStarInMovieScreen(getMovie());
        mMovieDetailInterface.refreshStarInDetailScreen(getMovie());
        if (getReminder() != null) {
            mMovieDetailInterface.refreshStarInReminderDetail(getMovie(), getReminder().getReminderDate());
        }
    }

    @Override
    public void addReminderSuccess(Reminder reminder) {
        mIsUpdateReminder = true;
        mReminderInterface.addReminder(reminder);
    }

    @Override
    public void findReminderSuccess(Reminder reminder) {
        if (reminder != null) {
            setReminder(reminder);
            mIsUpdateReminder = true;
            tvReminder.setText(getReminder().getReminderDate());
        } else {
            mIsUpdateReminder = false;
        }
    }

    @Override
    public void updateReminderSuccess(Reminder reminder) {
        mIsUpdateReminder = true;
        mReminderInterface.updateReminder(reminder);
    }

    public void getCastAndCrew() {
        mDetailMoviePresenter.getCastAndCrewFromApi(getMovie().getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mDetailMoviePresenter.cancelProcessing();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (!mIsReminder) {
            mMovieDetailInterface.onDestroy();
        } else {
            mUpdateTitleOnReminderView.updateTitle();
        }
        super.onDetach();
    }

    public void setMovieDetailInterface(MovieDetailInterface movieDetailInterface) {
        this.mMovieDetailInterface = movieDetailInterface;
    }

    public interface UpdateTitleOnReminderView {
        void updateTitle();
    }

    public void setUpdateTitleOnReminderView(UpdateTitleOnReminderView updateTitleOnReminderView) {
        this.mUpdateTitleOnReminderView = updateTitleOnReminderView;
    }

    public void reload(Movie movie) {
        if (imvFavorite != null && getMovie().getId().equals(movie.getId())) {
            imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
            mIsFavorite = !mIsFavorite;
        }
        onResume();
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public Reminder getReminder() {
        return mReminder;
    }

    public void setReminder(Reminder mReminder) {
        this.mReminder = mReminder;
    }

    public Reminder getInfoReminder(String timeReminder) {
        Reminder reminder = new Reminder();
        reminder.setId(getMovie().getId());
        reminder.setTitle(getMovie().getTitle());
        reminder.setPosterPath(getMovie().getPosterPath());
        reminder.setReminderDate(timeReminder);
        reminder.setOverview(getMovie().getOverview());
        reminder.setReleaseDate(getMovie().getReleaseDate());
        reminder.setFavorite(getMovie().getFavorite());
        reminder.setVoteAverage(getMovie().getVoteAverage());
        return reminder;
    }

    public void reloadReminder(Reminder reminder) {
        if (reminder.getId().equals(getReminder().getId())) {
            tvReminder.setText(reminder.getReminderDate());
        }
        onResume();
    }

    public void refreshStar(Movie movie, String reminderDate) {
        if (movie.getId().equals(getReminder().getId())) {
            if (imvFavorite != null) {
                imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
            }
            if (tvReminder != null) {
                tvReminder.setText(reminderDate);
            }
        }
        onResume();
    }

    public void refreshStar(Reminder reminder) {
        if (reminder.getId().equals(getReminder().getId())) {
            if (imvFavorite != null) {
                imvFavorite.setImageResource((reminder.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
            }
        }
        onResume();
    }

    public interface MovieDetailInterface {
        void onDestroy();
        void updateCountStarOnMenu(int value);
        // Refresh favorite in favorite screen
        void refreshStarInFavoriteScreen(Movie movie);
        // Refresh favorite in movie screen
        void refreshStarInMovieScreen(Movie movie);
        // Refresh favorite in Detail screen
        void refreshStarInDetailScreen(Movie movie);

        // Refresh favorite in Reminder Detail
        void refreshStarInReminderDetail(Movie movie, String reminderDate);
    }

    public interface ReminderInterface {
        void addReminder(Reminder reminder);
        void updateReminder(Reminder reminder);
    }

    public void setUpdateReminderInterface(ReminderInterface reminderInterface) {
        this.mReminderInterface = reminderInterface;
    }

}
