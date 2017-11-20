package vn.enclave.iramovies.ui.fragments.Setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: https://developer.android.com/guide/topics/ui/settings.html
 * @Run: http://www.androidinterview.com/android-custom-listview-with-checkbox-example/
 * => Done
 *
 * @Run: https://stackoverflow.com/questions/16163215/android-styling-seek-bar
 *
 * @Run: https://stackoverflow.com/questions/34932963/android-seekbar-with-custom-drawable
 * => android:left = "2dp"
 */

public class SettingView extends IRBaseFragment {

    

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void fragmentCreated() {

    }
}
