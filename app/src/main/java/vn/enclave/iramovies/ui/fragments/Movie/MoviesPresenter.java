package vn.enclave.iramovies.ui.fragments.Movie;

import android.content.Context;

import java.util.List;

import vn.enclave.iramovies.services.response.MovieData;

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
    private IMoviesModel mInboxModel;

    MoviesPresenter(Context context) {
        this.mContext = context;
        mInboxModel = new MoviesModel(mContext);
        mInboxModel.attachView(this);
    }

    @Override
    public void attachView(IMoviesView view) {
        this.mIMoviesView = view;
    }

    @Override
    public void getMoviesFromApi(int mPageIndex, boolean isLoadMore) {
        if (mIMoviesView != null) {
            mIMoviesView.showProgressDialog(isLoadMore);
            mInboxModel.getMoviesFromApi(mPageIndex);
        }
    }

    @Override
    public void onSuccess(List<MovieData> movies) {
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
    public void addMovie(MovieData movieData) {
        mInboxModel.addMovie(movieData);
    }
}
