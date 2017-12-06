package vn.enclave.iramovies.ui.fragments.Detail;

import android.content.Context;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;

/**
 *
 * Created by lorence on 23/11/2017.
 */

class MovieDetailPresenter implements IMovieDetailPresenter{

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMovieDetailView
     */
    private IMovieDetailView mIMovieDetailView;

    /**
     * IMovieDetailModel
     */
    private IMovieDetailModel mMovieDetailModel;

    MovieDetailPresenter(Context context) {
        this.mContext = context;
        mMovieDetailModel = new MovieDetailModel(mContext);
        mMovieDetailModel.attachView(this);
    }

    @Override
    public void attachView(IMovieDetailView view) {
        this.mIMovieDetailView = view;
    }

    @Override
    public void getCastAndCrewFromApi(int movieId) {
        mMovieDetailModel.getCastAndCrewFromApi(movieId);
    }

    @Override
    public void onSuccess(CastAndCrewResponse castAndCrewResponse) {
        mIMovieDetailView.onSuccess(castAndCrewResponse);
    }

    @Override
    public void onFailure(String message) {
        mIMovieDetailView.onFailure(message);
    }

    @Override
    public void cancelProcessing() {
        mMovieDetailModel.cancelProcessing();
    }

    @Override
    public void deleteMovie(Movie movie) {
        mMovieDetailModel.deleteMovie(movie);
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        if (mIMovieDetailView != null) {
            mIMovieDetailView.deleteMovieSuccess(movie);
        }
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        if (mIMovieDetailView != null) {
            mIMovieDetailView.addMovieSuccess(movie);
        }
    }

    @Override
    public void addMovie(Movie movie) {
        mMovieDetailModel.addMovie(movie);
    }

    @Override
    public void addReminder(Reminder reminder) {
        mMovieDetailModel.addReminder(reminder);
    }

    @Override
    public void addReminderSuccess(Reminder reminder) {
        if (mIMovieDetailView != null) {
            mIMovieDetailView.addReminderSuccess(reminder);
        }
    }
}
