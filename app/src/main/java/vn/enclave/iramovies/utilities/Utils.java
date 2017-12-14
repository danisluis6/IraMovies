package vn.enclave.iramovies.utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.enclave.iramovies.R;

/**
 * Created by lorence on 03/11/2017.
 *
 */

public class Utils {

    private static long sLastClickTime = 0;

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

    @SuppressLint("SimpleDateFormat")
    public static long getTime(String releaseDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = df.parse(releaseDate);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate.getTime();
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = String.format(Locale.US, "Image_%d", System.currentTimeMillis());
        File storageDir = getAlbumStorageDir(Constants.IMAGE_FOLDER);

        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private static File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.i("Camera", "Directory not created");
        }
        return file;
    }

    public static int convertGenderToInt(Context context, String mGender) {
        return TextUtils.equals(mGender, context.getString(R.string.male)) ? 0 : 1;
    }

    public static String convertIntToGender(Context context, int gender) {
        return gender == 0 ? context.getString(R.string.male) : context.getString(R.string.female);
    }

    public static boolean isInternetOn(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void clearFocusOnSearchView(View view) {
        if (view instanceof EditText) {
            if (view.isFocused()) {
                view.clearFocus();
            }
        }
    }

    public static boolean isVisibleSoftKeyBoardAndroid(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    // Close keyboard in android
    public static void dimissKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkPermissionCamera(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void settingPermissionCamera(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, Constants.Permissions.CAMERA
        );
    }

    public static boolean checkPermissionStorage(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void settingPermissionStorage(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, Constants.Permissions.STORAGE
        );
    }

    public static boolean isValidDateofBirth(String dateofBirth) {
        return Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(String.valueOf(dateofBirth.substring(0, 4))) >= 12
                && Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(String.valueOf(dateofBirth.substring(0, 4))) <= 120
                && Calendar.getInstance().get(Calendar.YEAR) > Integer.parseInt(String.valueOf(dateofBirth.substring(0, 4)));
    }

    public static class Toast {

        public static void showToast(Context context, String message) {
            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
