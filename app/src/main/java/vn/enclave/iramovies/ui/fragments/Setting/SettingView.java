package vn.enclave.iramovies.ui.fragments.Setting;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.views.CategoryDialog;
import vn.enclave.iramovies.ui.views.SeekBarDialog;
import vn.enclave.iramovies.utilities.Constants;
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

    @BindView(R.id.edtCategory)
    EditText edtCategory;

    @BindView(R.id.edtMovieWithRate)
    EditText edtMovieWithRate;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void fragmentCreated() {
        edtCategory.setText(getString(R.string.category_popular));
    }

    @Override
    protected void initAtributes(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initAtributes(inflater, container, savedInstanceState);
        edtCategory.setClickable(false);
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
                openSeekBarDialog();
                break;
            case R.id.fromReleaseYearLayout:
                Utils.Toast.showToast(mActivity, "C");
                break;
            case R.id.releaseDateLayout:
                Utils.Toast.showToast(mActivity, "E");
                break;
        }
    }

    private void openSeekBarDialog() {
        SeekBarDialog seekBarDialog = new SeekBarDialog(mActivity, new SeekBarDialog.OnGetValueSeekBar() {
            @Override
            public void onGetValueSeekBar(String value) {
                edtMovieWithRate.setText(value);
            }
        }, getValueFromSeekBar());
        seekBarDialog.show();
    }

    private void openCategoryDialog() {

        CategoryDialog categoryDialog = new CategoryDialog(mActivity, new CategoryDialog.OnRadioSelected() {
            @Override
            public void onRadioSelected(String text) {
                edtCategory.setText(text);
            }
        }, getRadioSelected());
        categoryDialog.show();
    }

    public CategoryDialog.TYPE getRadioSelected() {
        String text = edtCategory.getText().toString();
        if (TextUtils.equals(text, getString(R.string.category_popular))) {
            return CategoryDialog.TYPE.POPULAR;
        } else if (TextUtils.equals(text, getString(R.string.category_top_rated))) {
            return CategoryDialog.TYPE.TOP_RATED;
        } else if (TextUtils.equals(text, getString(R.string.category_up_coming))) {
            return CategoryDialog.TYPE.UP_COMING;
        } else if (TextUtils.equals(text, getString(R.string.category_now_playing))) {
            return CategoryDialog.TYPE.NOW_PLAYING;
        }
        return null;
    }

    public String getValueFromSeekBar() {
        if (TextUtils.equals(edtMovieWithRate.getText().toString(), Constants.EMPTY_STRING)) {
            return "0";
        } else {
            return edtMovieWithRate.getText().toString();
        }
    }
}
