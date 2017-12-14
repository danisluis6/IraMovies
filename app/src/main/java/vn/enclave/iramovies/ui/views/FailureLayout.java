package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.utilities.Utils;

/**
 *
 * Created by lorence on 14/11/2017.
 */

public class FailureLayout extends LinearLayout {

    @BindView(R.id.btnRetry)
    public LinearLayout btnRetry;

    @BindView(R.id.tvMessage)
    public TextView tvMessage;

    private OnRetryListener mOnRetryListener;

    public FailureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_failure, this, true);
        ButterKnife.bind(this, view);
        btnRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isDoubleClick()) {
                    return;
                }
                mOnRetryListener.onRetry();
            }
        });
    }

    public void setFailureMessage(String _message) {
        tvMessage.setText(_message);
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
