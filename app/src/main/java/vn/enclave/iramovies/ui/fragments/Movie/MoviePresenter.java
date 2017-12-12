package vn.enclave.iramovies.ui.fragments.Movie;

import android.content.Context;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MoviePresenter implements IMoviePresenter {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IInboxView
     */
    private IMovieView mIMoviesView;

    /**
     * IInboxModel
     */
    private IMovieModel mMovieModel;

    MoviePresenter(Context context) {
        this.mContext = context;
        mMovieModel = new MovieModel(mContext);
        mMovieModel.attachView(this);
    }

    @Override
    public void attachView(IMovieView view) {
        this.mIMoviesView = view;
    }

    @Override
    public void getMoviesFromApi(int mPageIndex, boolean isLoadMore, MovieView.MODE mode) {
        if (mIMoviesView != null) {
            mIMoviesView.showProgressDialog(isLoadMore);
            mMovieModel.getMoviesFromApi(mPageIndex, mode);
        }
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        if (mIMoviesView != null) {
            mIMoviesView.dismissProgressDialog();
            mIMoviesView.onSuccess(movies);
        }
    }

    @Override
    public void onFailure(String message) {
        if (mIMoviesView != null) {
            mIMoviesView.dismissProgressDialog();
        }
    }

    /** Work with database local ROOM */
    @Override
    public void addMovie(Movie movie) {
        mMovieModel.addMovie(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        mMovieModel.deleteMovie(movie);
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        if (mIMoviesView != null) {
            mIMoviesView.dismissProgressDialog();
            mIMoviesView.addMovieSuccess(movie);
        }
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        if (mIMoviesView != null) {
            mIMoviesView.dismissProgressDialog();
            mIMoviesView.deleteMovieSuccess(movie);
        }
    }

    @Override
    public void updateReminder(Reminder reminder) {
        mMovieModel.updateReminder(reminder);
    }

    @Override
    public void onUpdatedReminderSuccess(Reminder reminder) {
        if (mIMoviesView != null) {
            mIMoviesView.onUpdatedReminderSuccess(reminder);
        }
    }
}
