package vn.enclave.iramovies.ui.fragments.Detail;

import android.content.Context;

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
    private IMovieDetailModel mIMovieDetailModel;

    MovieDetailPresenter(Context context) {
        this.mContext = context;
        mIMovieDetailModel = new MovieDetailModel(mContext);
        mIMovieDetailModel.attachView(this);
    }

    @Override
    public void attachView(IMovieDetailView view) {
        this.mIMovieDetailView = view;
    }

    @Override
    public void getCastAndCrewFromApi(int movieId) {
        mIMovieDetailModel.getCastAndCrewFromApi(movieId);
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
        mIMovieDetailModel.cancelProcessing();
    }
}
