package vn.enclave.iramovies.ui.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.enclave.iramovies.R;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: http://o7planning.org/vi/10491/huong-dan-su-dung-android-fragment
 * => Done
 */

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_profile, container, false);
    }

}
