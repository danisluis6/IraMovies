package vn.enclave.iramovies.ui.activities.base.Home.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by enclaveit on 27/12/2016.
 */

public class PaperAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public PaperAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
