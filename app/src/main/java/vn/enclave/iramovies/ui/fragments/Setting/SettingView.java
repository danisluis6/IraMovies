package vn.enclave.iramovies.ui.fragments.Setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: https://developer.android.com/guide/topics/ui/settings.html
 * @Run: http://www.androidinterview.com/android-custom-listview-with-checkbox-example/
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/16163215/android-styling-seek-bar
 *
 * @Run: https://stackoverflow.com/questions/34932963/android-seekbar-with-custom-drawable
 * => android:left = "2dp"
 *
 * @Run: https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
 * => Done
 */

public class SettingView extends IRBaseFragment {

    @BindView(R.id.categoryLayout)
    ConstraintLayout categoryLayout;

    @BindView(R.id.movieWithRateLayout)
    ConstraintLayout movieWithRateLayout;

    @BindView(R.id.fromReleaseYearLayout)
    ConstraintLayout fromReleaseYearLayout;

    @BindView(R.id.releaseDateLayout)
    ConstraintLayout releaseDateLayout;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void fragmentCreated() {

    }

    @OnClick({R.id.categoryLayout, R.id.movieWithRateLayout, R.id.fromReleaseYearLayout, R.id.releaseDateLayout})
    public void onClick(View view) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.categoryLayout:
                openCategoryDialog();
                break;
            case R.id.movieWithRateLayout:
                Utils.Toast.showToast(mActivity, "B");
                break;
            case R.id.fromReleaseYearLayout:
                Utils.Toast.showToast(mActivity, "C");
                break;
            case R.id.releaseDateLayout:
                Utils.Toast.showToast(mActivity, "E");
                break;
        }
    }

    private void openCategoryDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
