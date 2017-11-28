package vn.enclave.iramovies.ui.fragments.Detail;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.enclave.iramovies.BuildConfig;
import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.services.IraMovieWebAPIs;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.services.response.CastResponse;
import vn.enclave.iramovies.services.response.CrewResponse;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 23/11/2017.
 */

public class MovieDetailModel implements IMovieDetailModel {

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IMovieDetailPresenter
     */
    private IMovieDetailPresenter mIMovieDetailPresenter;

    /**
     * IraMovieWebAPIs
     */
    private IraMovieWebAPIs mApiService;

    private Call<CastAndCrewResponse> mCallerCastAndCrew;

    MovieDetailModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
    }

    @Override
    public void attachView(IMovieDetailPresenter view) {
        this.mIMovieDetailPresenter = view;
    }

    @Override
    public void getCastAndCrewFromApi(int movieId) {
        mCallerCastAndCrew = mApiService.getMovieDetail(String.valueOf(movieId), BuildConfig.THE_MOVIE_DB_API_TOKEN);
        mCallerCastAndCrew.enqueue(new Callback<CastAndCrewResponse>() {
            @Override
            public void onResponse(Call<CastAndCrewResponse> call, Response<CastAndCrewResponse> response) {
                if (response.isSuccessful()) {
                    CastAndCrewResponse castAndCrewResponse = response.body();
                    if (castAndCrewResponse != null) {
                        mIMovieDetailPresenter.onSuccess(castAndCrewResponse);
                    }
                } else {
                    mIMovieDetailPresenter.onFailure(mContext.getString(R.string.cannot_get_data));
                }
            }

            @Override
            public void onFailure(Call<CastAndCrewResponse> call, Throwable t) {
                mIMovieDetailPresenter.onFailure(mContext.getString(R.string.cannot_get_data));
            }
        });
    }

    @Override
    public void cancelProcessing() {
        if (mCallerCastAndCrew != null && mCallerCastAndCrew.isExecuted() && !mCallerCastAndCrew.isCanceled()) {
            mCallerCastAndCrew.cancel();
            mIMovieDetailPresenter.onFailure(Constants.EMPTY_STRING);
        }
    }

}
