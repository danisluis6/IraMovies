package vn.enclave.iramovies.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.dialog.base.DialogBase;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 21/11/2017.
 *
 */

public class DialogReleaseDate extends DialogBase {

    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;

    @BindView(R.id.rdReleaseDate)
    public RadioButton rdReleaseDate;

    @BindView(R.id.rdRatingMovies)
    public RadioButton rdRatingMovies;

    private OnRadioSelected mOnRadioSelected;
    private int mId;

    public DialogReleaseDate(@NonNull Context context, OnRadioSelected onRadioSelected, TYPE type) {
        super(context);
        this.mOnRadioSelected = onRadioSelected;
        setId(type);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_release_date;
    }

    @OnClick(R.id.btnCancel)
    void dismissDialog() {
        dismiss();
    }

    @OnClick({R.id.rdReleaseDate, R.id.rdRatingMovies})
    public void getValue(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        getValueOfSelectedRadio();
        switch (v.getId()) {
            case R.id.rdReleaseDate:
                dismissDialog();
                break;
            case R.id.rdRatingMovies:
                dismissDialog();
                break;
        }
    }

    private void getValueOfSelectedRadio() {
        if (mRadioGroup.getCheckedRadioButtonId() != -1) {
            View radioButton = mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());
            int radioId = mRadioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) mRadioGroup.getChildAt(radioId);
            mOnRadioSelected.onRadioSelected(String.valueOf(btn.getText()));
        }
    }

    @Override
    public void dialogCreated() {
        mRadioGroup.check(getId());
    }

    public interface OnRadioSelected {
        void onRadioSelected(String text);
    }

    public enum TYPE {
        RELEASE_DATE, RATING_MOVIES
    }

    public int getId() {
        return mId;
    }

    public void setId(TYPE type) {
        switch (type) {
            case RELEASE_DATE:
                this.mId = R.id.rdReleaseDate;
                break;
            case RATING_MOVIES:
                this.mId = R.id.rdRatingMovies;
                break;
        }
    }

}
