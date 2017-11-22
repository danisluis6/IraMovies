package vn.enclave.iramovies.ui.fragments.Movie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
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
import vn.enclave.iramovies.ui.fragments.Movie.adapter.MovieAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 * @Run: Apply Mode-View_Presenter : MVP
 * => Done
 * @Run: https://stackoverflow.com/questions/28494637/android-how-to-stop-refreshing-fragments-on-tab-change
 * => Done
 *
 * @Run: https://www.coderefer.com/android-recyclerview-cardview-tutorial/
 * => @TODO
 *
 * @Run: http://pointofandroid.blogspot.com/2016/12/recyclerviewhorizontal-and-vertical.html
 * => Done
 */

public class MovieView extends IRBaseFragment implements IMoviesView {

    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;

    /* RecyclerView */
    @BindView(R.id.rcvMovies)
    public RecyclerView rcvMovies;

    private RecyclerView.LayoutManager mLayoutManager;
    private MovieInterface mMovieInterface;
    private MovieAdapter mMoviesListAdapter, mMoviesGridAdapter;
    private List<Movie> mGroupMovies;

    /**
     * Work with MVP
     */
    private MoviesPresenter mMoviesPresenter;
    /**
     * Work with load more
     */
    private int mPageIndex;
    private boolean mIsLoadMore = false;

    public MovieView() {
    }

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void fragmentCreated() {
        initViews(true);
        initAttributes();
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            getMoviesFromApi();
        } else {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},
                    Constants.Permissions.ACCESS_INTERNET);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getMoviesFromApi() {
        handleData(mGroupMovies);
    }

    private void handleData(List<Movie> movies) {
        if (Utils.isInternetOn(mActivity)) {
            mMoviesPresenter.getMoviesFromApi(mPageIndex, true, MODE.POPULAR);
        } else {
            if (movies.isEmpty()) {
                mFailureLayout.setFailureMessage(getResources().getString(R.string.no_internet_connection));
            } else {
                Utils.Toast.showToast(mActivity, getString(R.string.no_internet_connection));
            }
        }
    }

    private void initViews(boolean isDisplay) {
        mGroupMovies = new ArrayList<>();
        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (rcvMovies != null) {
            rcvMovies.setHasFixedSize(true);
        }
        if (isDisplay) {
            if (mMoviesListAdapter == null) {
                mMoviesListAdapter = new MovieAdapter(mActivity, mActivity, mGroupMovies, isDisplay);
            }
            mLayoutManager = new LinearLayoutManager(mActivity);
            rcvMovies.setLayoutManager(mLayoutManager);
            rcvMovies.setAdapter(mMoviesListAdapter);
        } else {
            if (mMoviesGridAdapter == null) {
                mMoviesGridAdapter = new MovieAdapter(mActivity, mActivity, mGroupMovies, isDisplay);
            }
            mLayoutManager = new GridLayoutManager(mActivity, 2);
            rcvMovies.setLayoutManager(mLayoutManager);
            rcvMovies.setAdapter(mMoviesGridAdapter);
        }

        mMoviesListAdapter.setMoreDataAvailable(true);
        mMoviesListAdapter.updateStatusLoading(false);
        mMoviesListAdapter.setLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvMovies.post(new Runnable() {
                    @Override
                    public void run() {
                        loadNextDataFromApi();
                    }
                });
            }
        });
        mMoviesListAdapter.setChooseFavoriteListener(new MovieAdapter.OnChooseFavoriteListener() {
            @Override
            public void onChoose(Movie movie) {
                mMoviesPresenter.addMovie(movie);
            }

            @Override
            public void onRemove(Movie movie) {
                mMoviesPresenter.deleteMovie(movie);
            }
        });
    }

    private void loadNextDataFromApi() {
        if (mIsLoadMore) {
            return;
        }
        mMoviesListAdapter.add(new Movie(Constants.Objects.LOAD));
        mIsLoadMore = true;
        mMoviesListAdapter.updateStatusLoading(false);
        mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.POPULAR);
    }

    private void initAttributes() {
        mMoviesPresenter = new MoviesPresenter(mActivity);
        mMoviesPresenter.attachView(this);

        mPageIndex = Constants.FIRST_PAGE;
        mMoviesListAdapter.setMoreDataAvailable(true);
    }

    @Override
    public void showProgressDialog(boolean isLoadMore) {
        if (!mDiaLoadView.isShowing() && isLoadMore) {
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
        mMoviesListAdapter.setMoreDataAvailable(mPageIndex < SessionManager.getInstance(mActivity).getTotalPages());
        ++mPageIndex; /* End fix */
        if (mIsLoadMore) {
            // Handle the dismiss loadingMore if there is another caller API is executing
            updateListMovies(movies);
        } else {
            dismissProgressDialog();
            mMoviesListAdapter.setMovies(movies);
        }
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        mMovieInterface.updateCountFavoritesOnMenu(movie.getFavorite());
        mMovieInterface.refreshFavoriteInFavoriteScreen(movie);
    }

    private void updateListMovies(List<Movie> listMovies) {
        mMoviesListAdapter.remove(mMoviesListAdapter.getItemCount() - 1);
        mMoviesListAdapter.addAll(listMovies);
    }

    @Override
    public void onFailure(String message) {
        Utils.Toast.showToast(mActivity, message);
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        mMovieInterface.updateCountFavoritesOnMenu(movie.getFavorite());
        mMovieInterface.refreshFavoriteInFavoriteScreen(movie);
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

    public void removeMovieFavorite(Movie movie) {
        mMoviesListAdapter.refreshFavorite(movie);
    }

    public void reload(MODE mode) {
        if (Utils.isInternetOn(mActivity)) {
            mPageIndex = Constants.FIRST_PAGE;
            mMoviesPresenter.getMoviesFromApi(mPageIndex, true, mode);
        } else {
            if (mGroupMovies.isEmpty()) {
                mFailureLayout.setFailureMessage(getResources().getString(R.string.no_internet_connection));
            } else {
                Utils.Toast.showToast(mActivity, getString(R.string.no_internet_connection));
            }
        }
    }

    public void setMovieInterface(MovieInterface movieInterface) {
        this.mMovieInterface = movieInterface;
    }

    public void setOnDisplay(boolean onDisplay) {
        initViews(onDisplay);
    }

    public enum MODE {
        POPULAR, TOP_RATED, UPCOMING, NOWPLAYING
    }

    /* Interface */
    public interface MovieInterface {
        void refreshFavoriteInFavoriteScreen(Movie movie);

        void updateCountFavoritesOnMenu(int value);
    }
}