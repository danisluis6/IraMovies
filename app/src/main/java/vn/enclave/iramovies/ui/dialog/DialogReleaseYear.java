package vn.enclave.iramovies.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.dialog.base.DialogBase;
import vn.enclave.iramovies.utilities.Utils;

/**
 *
 * Created by lorence on 21/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/20838227/set-cursor-position-in-android-edit-text
 * => Done
 */

public class DialogReleaseYear extends DialogBase {

    @BindView(R.id.edtReleaseYear)
    EditText edtReleaseYear;

    private OnGetValueEditText mOnGetValueEditText;
    private String mValue;

    public DialogReleaseYear(@NonNull Context context, OnGetValueEditText onGetValueEditText, String value) {
        super(context);
        this.mOnGetValueEditText = onGetValueEditText;
        setValue(value);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_release_year;
    }

    @Override
    public void dialogCreated() {
        edtReleaseYear.setText(getValue());
        if (getValue().length() > 1) {
            edtReleaseYear.setSelection(edtReleaseYear.length());
        }
    }

    @OnClick({R.id.btnOK, R.id.btnCancel})
    void getValue(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btnOK:
                mOnGetValueEditText.onGetValueEditText(edtReleaseYear.getText().toString());
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

    public interface OnGetValueEditText {
        void onGetValueEditText(String text);
    }
}
