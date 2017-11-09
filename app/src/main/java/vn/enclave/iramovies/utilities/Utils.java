package vn.enclave.iramovies.utilities;

import android.content.ActivityNotFoundException;

import vn.enclave.iramovies.ui.activities.base.BaseView;

/**
 *
 * Created by lorence on 03/11/2017.
 */

public class Utils {

    public static String makeLogTag(Class<BaseView> activityClass) throws ActivityNotFoundException {
        return activityClass.getSimpleName();
    }
}
