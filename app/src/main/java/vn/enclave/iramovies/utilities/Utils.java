package vn.enclave.iramovies.utilities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;

import vn.enclave.iramovies.ui.activities.base.BaseView;

/**
 *
 * Created by lorence on 03/11/2017.
 */

public class Utils {

    private static long sLastClickTime = 0;

    public static String makeLogTag(Class<BaseView> activityClass) throws ActivityNotFoundException {
        return activityClass.getSimpleName();
    }

    public static boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - sLastClickTime < Constants.DOUBLE_CLICK_TIME_DELTA) {
            sLastClickTime = clickTime;
            return true;
        }
        sLastClickTime = clickTime;
        return false;
    }

    public static Double getYear(String releaseDate) {
        return Double.parseDouble(releaseDate.substring(0, 4));
    }

    public static class Toast {

        public static void showToast(Context context, String message) {
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap convertToBitmap(byte[] bitmapdata) {
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    public static boolean isInternetOn(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public enum FAILURE_CASE {
        NO_DATA, NO_DATA_FOUND, NO_CONECTION
    }

    // Close keyboard in android
    public static void dimissKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void clearFocusOnSearchView(View view) {
        if (view instanceof EditText) {
            if (view.isFocused()) {
                view.clearFocus();
            }
        }
    }
}
