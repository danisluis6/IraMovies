package vn.enclave.iramovies.utilities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

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

    public static class Toast {

        public static void showToast(Context context, String message) {
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap convertToBitmap(byte[] bitmapdata) {
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    public static byte[] convertToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static boolean isInternetOn(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public enum FAILURE_CASE {
        NO_DATA, NO_DATA_FOUND, NO_CONECTION
    }
}
