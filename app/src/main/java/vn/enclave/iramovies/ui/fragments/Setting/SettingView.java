package vn.enclave.iramovies.ui.fragments.Setting;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.SessionManager;
import vn.enclave.iramovies.ui.dialog.DialogCategory;
import vn.enclave.iramovies.ui.dialog.DialogReleaseDate;
import vn.enclave.iramovies.ui.dialog.DialogReleaseYear;
import vn.enclave.iramovies.ui.dialog.DialogSeekBar;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: https://developer.android.com/guide/topics/ui/settings.html
 * => Done
 *
 * @Run: http://www.androidinterview.com/android-custom-listview-with-checkbox-example/
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/16163215/android-styling-seek-bar
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/34932963/android-seekbar-with-custom-drawable
 * => android:left = "2dp"
 *
 * @Run: https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
 * => Done
 *
 * @Run: https://developer.android.com/guide/topics/ui/settings.html
 * => Implement
 *
 * @Run: https://www.youtube.com/watch?v=0-7YvU9fz8k
 * => Follow
 *
 * @Run: https://developer.android.com/guide/topics/ui/settings.html
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

    @BindView(R.id.edtReleaseYear)
    EditText edtReleaseYear;

    @BindView(R.id.edtReleaseDate)
    EditText edtReleaseDate;

    private SettingInterface mSettingInterface;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void fragmentCreated() {
        if (!TextUtils.equals(SessionManager.getInstance(mActivity).getCategory(), Constants.EMPTY_STRING)) {
            edtCategory.setText(SessionManager.getInstance(mActivity).getCategory());
        } else {
            edtCategory.setText(getString(R.string.category_popular));
        }
        if (!TextUtils.equals(SessionManager.getInstance(mActivity).getRate(), Constants.EMPTY_STRING)) {
            edtMovieWithRate.setText(SessionManager.getInstance(mActivity).getRate());
        }
        edtReleaseDate.setText(getString(R.string.label_rating_movies));
    }

    @Override
    protected void initAtributes(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initAtributes(inflater, container, savedInstanceState);
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
                openReleaseYearDialog();
                break;
            case R.id.releaseDateLayout:
                openReleaseDateDialog();
                break;
        }
    }

    private void openReleaseDateDialog() {
        DialogReleaseDate releaseDateDialog = new DialogReleaseDate(mActivity, new DialogReleaseDate.OnRadioSelected() {
            @Override
            public void onRadioSelected(String sort) {
                edtReleaseDate.setText(sort);
                mSettingInterface.onSortByDateAndRating(sort);
            }
        }, getRadioSelectedRelease());
        releaseDateDialog.show();
    }

    private void openReleaseYearDialog() {
        DialogReleaseYear releaseYearDialog = new DialogReleaseYear(mActivity, new DialogReleaseYear.OnGetValueEditText() {
            @Override
            public void onGetValueEditText(final String text) {
                mActivity.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                edtReleaseYear.setText(text);
                mSettingInterface.onReloadReleaseYear(text);
            }
        }, getValueFromEditText());
        releaseYearDialog.show();
    }

    private void openSeekBarDialog() {
        DialogSeekBar seekBarDialog = new DialogSeekBar(mActivity, new DialogSeekBar.OnGetValueSeekBar() {
            @Override
            public void onGetValueSeekBar(String value) {
                edtMovieWithRate.setText(TextUtils.equals(value, "0") ? "0.0" : value);
                mSettingInterface.onReloadRating(edtMovieWithRate.getText().toString());
            }
        }, getValueFromSeekBar());
        seekBarDialog.show();
    }

    private void openCategoryDialog() {

        DialogCategory categoryDialog = new DialogCategory(mActivity, new DialogCategory.OnRadioSelected() {
            @Override
            public void onRadioSelected(String text) {
                edtCategory.setText(text);
                mSettingInterface.onReloadCategory(text);
            }
        }, getRadioSelectedCategory());
        categoryDialog.show();
    }

    public DialogCategory.TYPE getRadioSelectedCategory() {
        String text = edtCategory.getText().toString();
        if (TextUtils.equals(text, getString(R.string.category_popular))) {
            return DialogCategory.TYPE.POPULAR;
        } else if (TextUtils.equals(text, getString(R.string.category_top_rated))) {
            return DialogCategory.TYPE.TOP_RATED;
        } else if (TextUtils.equals(text, getString(R.string.category_up_coming))) {
            return DialogCategory.TYPE.UP_COMING;
        } else if (TextUtils.equals(text, getString(R.string.category_now_playing))) {
            return DialogCategory.TYPE.NOW_PLAYING;
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

    public String getValueFromEditText() {
        return edtReleaseYear.getText().toString();
    }

    public DialogReleaseDate.TYPE getRadioSelectedRelease() {
        String text = edtReleaseDate.getText().toString();
        if (TextUtils.equals(text, getString(R.string.label_release_date))) {
            return DialogReleaseDate.TYPE.RELEASE_DATE;
        } else if (TextUtils.equals(text, getString(R.string.label_rating_movies))) {
            return DialogReleaseDate.TYPE.RATING_MOVIES;
        }
        return null;
    }

    public void setSettingInterface(SettingInterface settingInterface) {
        this.mSettingInterface = settingInterface;
    }

    public interface SettingInterface {
        void onReloadCategory(String category);
        void onReloadRating(String rate);
        void onReloadReleaseYear(String releaseYear);
        // Sort
        void onSortByDateAndRating(String type);
    }
}
