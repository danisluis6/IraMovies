package vn.enclave.iramovies.ui.fragments.Detail;

import android.arch.persistence.room.Room;
import android.content.Context;

import vn.enclave.iramovies.IRApplication;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;

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
     * IraMoviesWebAPIs
     */
    private IraMoviesWebAPIs mApiService;

    /**
     * AppDatabase
     */
    private AppDatabase mAppDatabase;

    MovieDetailModel(Context context) {
        this.mContext = context;
        mApiService = IRApplication.getInstance().getEzFaxingWebAPIs();
        mAppDatabase = Room.databaseBuilder(mContext, AppDatabase.class, AppDatabase.DB_NAME).build();
    }

    @Override
    public void attachView(IMovieDetailPresenter view) {
        this.mIMovieDetailPresenter = view;
    }

    @Override
    public void getCastAndCrewFromApi(int movieId) {

    }
}
