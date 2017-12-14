package vn.enclave.iramovies.ui.activities.base.Home.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.enclave.iramovies.R;

/**
 *
 * Created by Lorence on 27/12/2016.
 */

public class PaperAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private Context mContext;

    public PaperAdapter(Context context, FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getResources().getStringArray(R.array.menu_bottom_nav)[0];
            case 1:
                return mContext.getResources().getStringArray(R.array.menu_bottom_nav)[1];
            case 2:
                return mContext.getResources().getStringArray(R.array.menu_bottom_nav)[2];
            case 3:
                return mContext.getResources().getStringArray(R.array.menu_bottom_nav)[3];
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}