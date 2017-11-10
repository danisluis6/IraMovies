package vn.enclave.iramovies.ui.fragments.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;

/**
 * Created by lorence on 08/11/2017.
 */

public class FavoriteFragment extends IRBaseFragment {
    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void fragmentCreated() {

    }
}
