package vn.enclave.iramovies.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import vn.enclave.iramovies.R;

/**
 * Created by lorence on 14/11/2017.
 *
 */

public class DialogView extends Dialog {

    /**
     * Activity
     */
    private Activity mActivity;

    /**
     * Initiate dialog.
     *
     * @param activity activity
     */
    public DialogView(Activity activity) {
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
