package vn.enclave.iramovies.ui.fragments.Movie;

import android.content.Context;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MoviesPresenter implements IMoviesPresenter {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IInboxView
     */
    private IMoviesView mIMoviesView;

    /**
     * IInboxModel
     */
    private IMoviesModel mMovieModel;

    MoviesPresenter(Context context) {
        this.mContext = context;
        mMovieModel = new MoviesModel(mContext);
        mMovieModel.attachView(this);
    }

    @Override
    public void attachView(IMoviesView view) {
        this.mIMoviesView = view;
    }

    @Override
    public void getMoviesFromApi(int mPageIndex, boolean isLoadMore) {
        if (mIMoviesView != null) {
            mIMoviesView.showProgressDialog(isLoadMore);
            mMovieModel.getMoviesFromApi(mPageIndex);
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
            mIMoviesView.onFailure(message);
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
}
