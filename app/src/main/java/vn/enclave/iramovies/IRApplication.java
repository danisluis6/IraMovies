package vn.enclave.iramovies;

import android.app.Application;

import vn.enclave.iramovies.services.IraMovieWebAPIs;

/**
 *
 * Created by lorence on 14/11/2017.
 */

public class IRApplication extends Application{
    /**
     * Apply singleton in Android
     */
    private static IRApplication sInstance;

    /**
     * EzFaxingWebAPIs
     */
    private IraMovieWebAPIs mIraMoviesWebAPIs;

    public static synchronized IRApplication getInstance() {
        if (sInstance == null) {
            sInstance = new IRApplication();
        }
        return sInstance;
    }

    public IraMovieWebAPIs getEzFaxingWebAPIs() {
        if (mIraMoviesWebAPIs == null) {
            mIraMoviesWebAPIs = IraMovieWebAPIs.Factory.create(sInstance);
        }
        return mIraMoviesWebAPIs;
    }
}
