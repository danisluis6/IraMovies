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
    public void getMoviesFromApi() {
        if (mIMoviesView != null) {
            // mIMoviesView.showProgressDialog();
            mInboxModel.getMoviesFromApi();
        }
    }

    @Override
    public void onSuccess(List<Movie> movies) {

    }

    @Override
    public void onFailure(String message) {

    }
}
