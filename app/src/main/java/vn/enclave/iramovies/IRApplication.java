package vn.enclave.iramovies;

import android.app.Application;
import android.arch.persistence.room.Room;

import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.services.IraMoviesWebAPIs;

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
    private IraMoviesWebAPIs mIraMoviesWebAPIs;



    public static synchronized IRApplication getInstance() {
        if (sInstance == null) {
            sInstance = new IRApplication();
        }
        return sInstance;
    }

    public IraMoviesWebAPIs getEzFaxingWebAPIs() {
        if (mIraMoviesWebAPIs == null) {
            mIraMoviesWebAPIs = IraMoviesWebAPIs.Factory.create(sInstance);
        }
        return mIraMoviesWebAPIs;
    }
}
