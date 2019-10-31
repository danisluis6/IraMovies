package vn.enclave.iramovies.ui.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * Created by lorence on 21/11/2017.
 */

public abstract class DialogBase extends Dialog {

    private Unbinder mUnbinder;

    public DialogBase(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAtributes();
        setContentView(getLayoutResId());
        setView();
        dialogCreated();
    }

    private void setView() {
        this.mUnbinder = ButterKnife.bind(this);
    }

    protected void initAtributes() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected abstract int getLayoutResId();
    public abstract void dialogCreated();

    @Override
    public void onDetachedFromWindow() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDetachedFromWindow();
    }
}
