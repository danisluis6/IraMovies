package vn.enclave.iramovies.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.enclave.iramovies.R;


/**
 * Created by lorence on 13/11/2017.
 *
 * @Run:
 */

public class TabItem extends LinearLayout {

    @BindView(R.id.imvTabIcon)
    public ImageView imvTabIcon;

    @BindView(R.id.tvTabText)
    public TextView tvTabText;
    private View mView;

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.item_tab, this, false);
        ButterKnife.bind(this, mView);
    }

    public View getView() {
        return mView;
    }

    public void setTabIcon(int resId) {
        imvTabIcon.setImageResource(resId);
    }

    public void setTabText(String tabText) {
        tvTabText.setText(tabText);
    }
}
