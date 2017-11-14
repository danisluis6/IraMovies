package vn.enclave.iramovies.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import vn.enclave.iramovies.R;

/**
 * Created by lorence on 14/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/16893209/how-to-customize-a-progress-bar-in-android
 * => Cust circle progress bar in Android
 */

public class LoadView extends Dialog {

    /**
     * Activity
     */
    private Activity mActivity;

    /**
     * Initiate dialog.
     *
     * @param activity activity
     */
    public LoadView(Activity activity) {
        super(activity);
        this.mActivity = activity;
        init();
    }

    /**
     * Initiate the views.
     */
    @SuppressWarnings("ConstantConditions")
    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.progress_circular);
        setCancelable(false);
    }

    @Override
    public void show() {
        if (mActivity != null && !mActivity.isFinishing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (mActivity != null && !mActivity.isFinishing()) {
            super.dismiss();
        }
    }
}
