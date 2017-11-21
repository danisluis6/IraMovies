package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.utilities.Utils;

import static vn.enclave.iramovies.ui.views.CategoryDialog.TYPE.*;

/**
 * Created by lorence on 20/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/22655599/alertdialog-builder-with-custom-layout-and-edittext-cannot-access-view
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/2134591/add-margin-between-a-radiobutton-and-its-label-in-android
 * => Done
 *
 * @Run: https://crosp.net/blog/android/creating-custom-radio-groups-radio-buttons-android/
 * => Researching
 *
 * @Run: https://stackoverflow.com/questions/9175635/how-to-set-radio-button-checked-as-default-in-radiogroup-with-android
 * => Done
 *
 * @Run:
 */

public class CategoryDialog extends BaseDialog {

    @BindView(R.id.rdPopular)
    public RadioButton rdPopular;

    @BindView(R.id.rdTopRated)
    public RadioButton rdTopRated;

    @BindView(R.id.rdUpcoming)
    public RadioButton rdUpcoming;

    @BindView(R.id.rdNowPlaying)
    public RadioButton rdNowPlaying;

    @BindView(R.id.btnCancel)
    public Button btnCancel;

    @BindView(R.id.radioGroup)
    public RadioGroup mRadioGroup;

    private OnRadioSelected mOnRadioSelected;
    private int mId;

    public CategoryDialog(@NonNull Context context, OnRadioSelected onRadioSelected, TYPE type) {
        super(context);
        this.mOnRadioSelected = onRadioSelected;
        setId(type);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.category_dialog;
    }

    @Override
    public void dialogCreated() {
        mRadioGroup.check(getId());
    }

    @Override
    protected void initAtributes() {
        super.initAtributes();
    }

    @OnClick(R.id.btnCancel)
    public void dismissDialog() {
        dismiss();
    }

    @OnClick({R.id.rdPopular, R.id.rdTopRated, R.id.rdNowPlaying, R.id.rdUpcoming})
    public void getValue(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        getValueOfSelectedRadio();
        switch (v.getId()) {
            case R.id.rdPopular:
                dismissDialog();
                break;
            case R.id.rdTopRated:
                dismissDialog();
                break;
            case R.id.rdUpcoming:
                dismissDialog();
                break;
            case R.id.rdNowPlaying:
                dismissDialog();
                break;
        }
    }

    public void getValueOfSelectedRadio() {
        if (mRadioGroup.getCheckedRadioButtonId() != -1) {
            View radioButton = mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());
            int radioId = mRadioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) mRadioGroup.getChildAt(radioId);
            mOnRadioSelected.onRadioSelected(String.valueOf(btn.getText()));
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(TYPE type) {
        switch (type) {
            case POPULAR:
                this.mId = R.id.rdPopular;
                break;
            case TOP_RATED:
                this.mId = R.id.rdTopRated;
                break;
            case UP_COMING:
                this.mId = R.id.rdUpcoming;
                break;
            case NOW_PLAYING:
                this.mId = R.id.rdNowPlaying;
                break;
        }
    }

    public interface OnRadioSelected {
        void onRadioSelected(String text);
    }

     public enum TYPE {
         POPULAR, TOP_RATED, UP_COMING, NOW_PLAYING
     }
}
