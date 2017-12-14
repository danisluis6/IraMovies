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
 */

public class TabItem extends LinearLayout {

    @BindView(R.id.tvCountFavorite)
    public TextView tvCountFavorite;

    @BindView(R.id.imvTabIcon)
    public ImageView imvTabIcon;

    @BindView(R.id.tvTabText)
    public TextView tvTabText;

    private View mView;
    private int mCountFavorites = 0;

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.item_tab, this, false);
        ButterKnife.bind(this, mView);
    }

    public View getView() {
        return mView;
    }

    public void setVisibleIconFavorite(boolean isVisible) {
        tvCountFavorite.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setTabIcon(int resId) {
        imvTabIcon.setImageResource(resId);
    }

    public void setTabText(String tabText) {
        tvTabText.setText(tabText);
    }

    public void setNumberFavorites(int count) {
        tvCountFavorite.setVisibility(VISIBLE);
        if (count == 0) {
            tvCountFavorite.setVisibility(GONE);
        } else {
            mCountFavorites = count;
            showCountFavorites(mCountFavorites);
        }
    }

    public void updateNumberFavorites(int favorite) {
        tvCountFavorite.setVisibility(VISIBLE);
        mCountFavorites = (favorite == 1) ? ++mCountFavorites : --mCountFavorites;
        if (mCountFavorites == 0) {
            tvCountFavorite.setVisibility(GONE);
        } else {
            showCountFavorites(mCountFavorites);
        }
    }

    private void showCountFavorites(int count) {
        if (count < 9) {
            tvCountFavorite.setText(String.valueOf(count));
        } else {
            tvCountFavorite.setText("9+");
        }
    }
}
