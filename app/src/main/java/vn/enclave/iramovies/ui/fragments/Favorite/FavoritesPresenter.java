package vn.enclave.iramovies.ui.fragments.Favorite;

import android.content.Context;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class FavoritesPresenter implements IFavoritesPresenter {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IInboxView
     */
    private IFavoritesView mIFavoritesView;

    /**
     * IInboxModel
     */
    private IFavoritesModel mFavoritesModel;

    FavoritesPresenter(Context context) {
        this.mContext = context;
        mFavoritesModel = new FavoritesModel(mContext);
        mFavoritesModel.attachView(this);
    }

    @Override
    public void attachView(IFavoritesView view) {
        this.mIFavoritesView = view;
    }

    @Override
    public void getMoviesFromLocal() {
        if (mIFavoritesView != null) {
            mIFavoritesView.showProgressDialog();
            mFavoritesModel.getMoviesFromLocal();
        }
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        if (mIFavoritesView != null) {
            mIFavoritesView.dismissProgressDialog();
            mIFavoritesView.onSuccess(movies);
        }
    }

    @Override
    public void onFailure(String message) {
        if (mIFavoritesView != null) {
            mIFavoritesView.dismissProgressDialog();
            mIFavoritesView.onFailure(message);
        }
    }

    /** Work with database local ROOM */
    @Override
    public void addMovie(Movie movie) {
        mFavoritesModel.addMovie(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        mFavoritesModel.deleteMovie(movie);
    }
}
