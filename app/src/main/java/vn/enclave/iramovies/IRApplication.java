package vn.enclave.iramovies;

import android.app.Application;

import io.branch.referral.Branch;
import vn.enclave.iramovies.services.IraMovieWebAPIs;

/**
 *
 * Created by lorence on 14/11/2017.
 */

/**
 * My First Link: https://nmrv.test-app.link/IjRIrfMpDJ
 * - Configure link:
 *  - Link Settings https://exampleurl.com/iramovie.apk
 *  - Quick Link:
 *    - Deep View: Android: branch_default [If App not installed => show URL(button to get Application => Open new URL)]
 *
 *  Case 1:
 *  - Emulator: Application already installed -> Running URL => Open GUI of Deep View ((Web browser))
 *  - Sony Xperia XA: Application already installed => Running URL => Open GUI of Deep View (branch default)
 *
 *      ---> Click Button Get The App (1> Open Application in device 2> Run url https://exampleurl.com/iramovies.apk)
 *
 *  Case 2: Uninstall Application
 *  - Emulator: Running URL => Open DeepView => Get The App => The site can't be reached
 *  - Sony Xperia XA: Running URL => Open DeepView => Get The App => Item not found. (Play Store)
 *
 *  If we configure URL in Link Settings => Launch URL => Running Settings. Or Set Web URL (Running this link)
 */


/**
 * My Second Link: https://nmrv.test-app.link/RFOzTN8tDJ
 * - Quick Link:
 *    - Deep View: Android: Default Redirect https://exampleurl.com/iramovies.apk (Get it from Link Settings)
 *
 * Case 1: Already installed application
 *  - Emulator: (Running Application or Running URL without DeepView)
 *  - Sony Xperia XA:
 *
 * Case 2: Already uninstall application
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

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Branch object
        Branch.getAutoInstance(this);

    }

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
