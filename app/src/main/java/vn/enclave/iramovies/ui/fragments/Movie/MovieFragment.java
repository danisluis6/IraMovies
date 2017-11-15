package vn.enclave.iramovies.ui.fragments.Movie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.SessionManager;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Movie.adapter.MoviesAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: Apply Mode-View_Presenter : MVP
 * => Done
 */

public class MovieFragment extends IRBaseFragment implements IMoviesView {

    // private static String TAG = MovieFragment.class.getName();
    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;
    @BindView(R.id.rcvMovies)

    /** RecyclerView */
    public RecyclerView rcvMovies;
    public RecyclerView.LayoutManager mLayoutManager;

    private MoviesAdapter mMoviesAdapter;
    private List<Movie> mGroupMovies;

    /** Work with MVP */
    private MoviesPresenter mMoviesPresenter;

    /** Work with load more */
    private int mPageIndex;
    private boolean mIsLoadMore = false;


    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void fragmentCreated() {
        initViews();
        initAttributes();
        getMoviesFromApi();
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            handleData(mGroupMovies);
        } else {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},
                    Constants.Permissions.ACCESS_INTERNET);
        }
    }

    public void getMoviesFromApi() {
        handleData(mGroupMovies);
    }

    private void handleData(List<Movie> movies) {
        if (Utils.isInternetOn(mActivity)) {
            mMoviesPresenter.getMoviesFromApi(mPageIndex++);
        } else {
            if (movies.isEmpty()) {
                mFailureLayout.setFailureMessage(getResources().getString(R.string.no_internet_connection));
            } else {
                Utils.Toast.showToast(mActivity, getString(R.string.no_internet_connection));
            }
        }
    }

    private void initViews() {
        mGroupMovies = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mActivity);
        rcvMovies.setLayoutManager(mLayoutManager);

        mMoviesAdapter = new MoviesAdapter(mActivity,mActivity, mGroupMovies);
        rcvMovies.setAdapter(mMoviesAdapter);

        mMoviesAdapter.setMoreDataAvailable(true);
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesAdapter.setLoadMoreListener(new MoviesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvMovies.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mPageIndex < SessionManager.getInstance(mActivity).getTotalPages()) {
                            mPageIndex++;
                            mMoviesAdapter.setMoreDataAvailable(true);
                            loadNextDataFromApi();
                        } else {
                            mMoviesAdapter.setMoreDataAvailable(false);
                            Utils.Toast.showToast(mActivity, getString(R.string.no_more_data));
                        }
                    }
                });
            }
        });
    }

    private void loadNextDataFromApi() {
        mMoviesAdapter.add(new Movie(Constants.Objects.LOAD));
        mIsLoadMore = true;
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesPresenter.getMoviesFromApi(mPageIndex);
    }


    private void initAttributes() {
        mMoviesPresenter = new MoviesPresenter(mActivity);
        mMoviesPresenter.attachView(this);
        mPageIndex = Constants.FIRST_PAGE;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showProgressDialog() {
        if (!mDiaLoadView.isShowing()) {
            mDiaLoadView.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mDiaLoadView.isShowing()) {
            mDiaLoadView.dismiss();
        }
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        if (mIsLoadMore) {
            // Handle the dismiss loadingMore if there is another caller API is executing
            updateListMovies(movies);
        } else {
            dismissProgressDialog();
            mMoviesAdapter.setMovies(movies);
        }
    }

    private void updateListMovies(List<Movie> listMovies) {
        mMoviesAdapter.remove(mMoviesAdapter.getItemCount() - 1);
        mMoviesAdapter.addAll(listMovies);
        mIsLoadMore = false;
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.Permissions.ACCESS_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                for (int permissionId : grantResults) {
                    if (permissionId != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mActivity, "Permission denied!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                handleData(mGroupMovies);
                break;
            }
        }
    }
}
