package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.utilities.OverrideFonts;

/**
 *
 * Created by lorence on 01/12/2017.
 */

public class SnackBarLayout extends LinearLayout {

    @BindView(R.id.lnCamera)
    LinearLayout lnCamera;

    @BindView(R.id.lnGallery)
    LinearLayout lnGallery;

    @BindView(R.id.tvCamera)
    TextView tvCamera;

    @BindView(R.id.tvGallery)
    TextView tvGallery;

    private Snackbar mSnackbar;
    private SnackInterface mSnackInterface;

    public SnackBarLayout(Context context, @Nullable AttributeSet attrs, Unbinder mUnbinder, Snackbar snackbar) {
        super(context, attrs);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUnbinder = ButterKnife.bind(this, mInflater.inflate(R.layout.dialog_pick, this, true));
        mSnackbar = snackbar;
        initAttributes(context);
    }

    private void initAttributes(Context context) {
        tvCamera.setTypeface(OverrideFonts.getTypeFace(context, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
        tvGallery.setTypeface(OverrideFonts.getTypeFace(context, OverrideFonts.TYPE_FONT_NAME.HELVETICANEUE, OverrideFonts.TYPE_STYLE.BOLD));
    }

    @OnClick({R.id.lnCamera, R.id.lnGallery})
    public void onClick(View v) {
        if (mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
        switch (v.getId()) {
            case R.id.lnCamera:
                openCamera();
                break;
            case R.id.lnGallery:
                openGallery();
                break;
        }
    }

    private void openCamera() {
        mSnackInterface.openCamera();
    }

    private void openGallery() {
        mSnackInterface.openGallery();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public interface SnackInterface {
        void openCamera();
        void openGallery();
    }

    public void setSnackInterface(SnackInterface snackInterface) {
        mSnackInterface = snackInterface;
    }
}
