package vn.enclave.iramovies.ui.fragments.Movie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
    private MovieAdapter mMoviesAdapter;
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
        initViews();
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

    private void initViews() {
        mGroupMovies = new ArrayList<>();
        //Use this setting to improve performance if you know that changes in
        //the content do not change the layout size of the RecyclerView
        if (rcvMovies != null) {
            rcvMovies.setHasFixedSize(true);
        }
        if (mMoviesAdapter == null) {
            mMoviesAdapter = new MovieAdapter(mActivity, mActivity, mGroupMovies, true);
        }
        mLayoutManager = new LinearLayoutManager(mActivity);
        rcvMovies.setLayoutManager(mLayoutManager);
        rcvMovies.setAdapter(mMoviesAdapter);
        mMoviesAdapter.setMoreDataAvailable(true);
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesAdapter.setLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
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
        mMoviesAdapter.setChooseFavoriteListener(new MovieAdapter.OnChooseFavoriteListener() {
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
        mMoviesAdapter.add(new Movie(Constants.Objects.LOAD));
        mIsLoadMore = true;
        mMoviesAdapter.updateStatusLoading(false);
        mMoviesPresenter.getMoviesFromApi(mPageIndex, false, MODE.POPULAR);
    }

    private void initAttributes() {
        mMoviesPresenter = new MoviesPresenter(mActivity);
        mMoviesPresenter.attachView(this);

        mPageIndex = Constants.FIRST_PAGE;
        mMoviesAdapter.setMoreDataAvailable(true);
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
        mMoviesAdapter.setMoreDataAvailable(mPageIndex < SessionManager.getInstance(mActivity).getTotalPages());
        ++mPageIndex; /* End fix */
        if (mIsLoadMore) {
            // Handle the dismiss loadingMore if there is another caller API is executing
            updateListMovies(movies);
        } else {
            dismissProgressDialog();
            mMoviesAdapter.setMovies(movies);
        }
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        mMovieInterface.updateCountFavoritesOnMenu(movie.getFavorite());
        mMovieInterface.refreshFavoriteInFavoriteScreen(movie);
    }

    private void updateListMovies(List<Movie> listMovies) {
        mMoviesAdapter.remove(mMoviesAdapter.getItemCount() - 1);
        mMoviesAdapter.addAll(listMovies);
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
        mMoviesAdapter.refreshFavorite(movie);
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
        mMoviesAdapter.setModeDisplay(onDisplay);
        if (onDisplay) {
            rcvMovies.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            rcvMovies.setLayoutManager(new GridLayoutManager(mActivity, 2));
        }
        rcvMovies.setAdapter(mMoviesAdapter);
    }

    public enum MODE {
        POPULAR, TOP_RATED, UPCOMING, NOW_PLAYING
    }

    /* Interface */
    public interface MovieInterface {
        void refreshFavoriteInFavoriteScreen(Movie movie);

        void updateCountFavoritesOnMenu(int value);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}