package vn.enclave.iramovies.ui.views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.activities.base.Home.HomeView;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: http://o7planning.org/vi/10491/huong-dan-su-dung-android-fragment
 * => Done
 */

public class ProfileFragment extends IRBaseFragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeView) {
            this.mContext = context;
        }
    }

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_profile, container, false);
    }

    @Override
    public void fragmentCreated() {

    }
}
