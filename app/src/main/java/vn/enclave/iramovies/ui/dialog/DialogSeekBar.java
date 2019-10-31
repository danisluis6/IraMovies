package vn.enclave.iramovies.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.dialog.base.DialogBase;
import vn.enclave.iramovies.utilities.Utils;

/**
 *
 * Created by lorence on 21/11/2017.
 *
 * @Run: https://stackoverflow.com/questions/15647676/android-seekbar-float-values-from-0-0-to-2-0
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/22833515/rounding-to-6-decimal-places-using-math-round-method-in-java-android
 * => Done
 */

public class DialogSeekBar extends DialogBase {

    @BindView(R.id.tvStar)
    TextView tvStar;

    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @BindView(R.id.btnYes)
    Button btnYes;

    @BindView(R.id.btnNo)
    Button btnNo;

    private String mValue;
    private OnGetValueSeekBar mOnGetValueSeekBar;

    public DialogSeekBar(@NonNull Context context, OnGetValueSeekBar onGetValueSeekBar, String value) {
        super(context);
        setValue(value);
        this.mOnGetValueSeekBar = onGetValueSeekBar;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_seekbar;
    }

    @Override
    public void dialogCreated() {
        tvStar.setText(TextUtils.equals(getValue(), "0") ? "0.0" : getValue());
        seekBar.setProgress(Math.round(Float.parseFloat(getValue())*10));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvStar.setText(String.valueOf(getConvertedValue(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private float getConvertedValue(int intVal){
        setValue(String.valueOf(Math.round(intVal)/10.0f));
        return Float.parseFloat(getValue());
    }

    private String getValue() {
        return mValue;
    }

    private void setValue(String mValue) {
        this.mValue = mValue;
    }

    @OnClick({R.id.btnYes, R.id.btnNo})
    void getValue(View v) {
        if (Utils.isDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btnYes:
                mOnGetValueSeekBar.onGetValueSeekBar(getValue());
                dismiss();
                break;
            case R.id.btnNo:
                dismiss();
                break;
        }
    }

    public interface OnGetValueSeekBar {
        void onGetValueSeekBar(String text);
    }
}
