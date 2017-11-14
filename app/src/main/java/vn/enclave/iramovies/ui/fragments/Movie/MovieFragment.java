package vn.enclave.iramovies.ui.fragments.Movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Movie.adapter.MoviesAdapter;
import vn.enclave.iramovies.ui.views.LoadView;

/**
 * Created by lorence on 08/11/2017.
 * @Run:
 */

public class MovieFragment extends IRBaseFragment{

    private MoviesAdapter mMoviesAdapter;
    private static String TAG = MovieFragment.class.getName();
    private LoadView mDiaLoadView;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void fragmentCreated() {

    }

    @Override
    public void onResume() {
        super.onResume();
        LoadView mLoadView = new LoadView(mActivity);
        mLoadView.show();
    }
}
