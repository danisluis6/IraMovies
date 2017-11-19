package vn.enclave.iramovies.ui.fragments.Favorite;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Favorite.adapter.FavoritesAdapter;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 *
 */

public class FavoriteView extends IRBaseFragment implements IFavoritesView{

    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;
    @BindView(R.id.rcvMovies)

    /** RecyclerView */
    public RecyclerView rcvMovies;
    public RecyclerView.LayoutManager mLayoutManager;

    private FavoritesAdapter mFavoritesAdapter;
    private List<Movie> mGroupMovies;

    /** Work with MVP */
    private FavoritesPresenter mFavoritesPresenter;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void fragmentCreated() {
        initViews();
        initAttributes();
        getMoviesFromLocal();

    }

    private void initViews() {
        mGroupMovies = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(mActivity);
        rcvMovies.setLayoutManager(mLayoutManager);

        if (mFavoritesAdapter == null) {
            mFavoritesAdapter = new FavoritesAdapter(mActivity,mActivity, mGroupMovies);
        }
        rcvMovies.setAdapter(mFavoritesAdapter);

        mFavoritesAdapter.setChooseFavoriteListener(new FavoritesAdapter.OnChooseFavoriteListener() {
            @Override
            public void onChoose(Movie movie) {
                if (Utils.isDoubleClick()) {
                    return;
                }
                mMovieInterface.updateCountFavoritesOnMenu(movie.getFavorite());
                mMovieInterface.refreshFavoriteInMovieScreen(movie);
                mFavoritesPresenter.addMovie(movie);
            }

            @Override
            public void onRemove(Movie movie) {
                if (Utils.isDoubleClick()) {
                    return;
                }
                mMovieInterface.updateCountFavoritesOnMenu(movie.getFavorite());
                mFavoritesPresenter.deleteMovie(movie);
                mFavoritesAdapter.remove(movie);
            }
        });
    }

    private void initAttributes() {
        mFavoritesPresenter = new FavoritesPresenter(mActivity);
        mFavoritesPresenter.attachView(this);
    }

    public void getMoviesFromLocal() {
        mFavoritesPresenter.getMoviesFromLocal();
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
        mFavoritesAdapter.setMovies(movies);
        updateStatusFavorite(movies.size());
    }

    private void updateStatusFavorite(int count) {
        mMovieInterface.setTotalFavoritesOnMenu(count);
    }

    @Override
    public void onFailure(String message) {
        // TODO
    }

    public void refreshStatusFavorite(Movie movie) {
        if (movie.getFavorite() == Constants.Favorites.DEFAULT) {
            mFavoritesAdapter.remove(movie);
        } else {
            mFavoritesAdapter.add(movie);
        }
    }

    public FavoriteInterface mMovieInterface;

    /* Interface */
    public interface FavoriteInterface {
        void refreshFavoriteInMovieScreen(Movie movie);
        void setTotalFavoritesOnMenu(int count);
        void updateCountFavoritesOnMenu(int value);
    }

    public void setFavoriteInterface(FavoriteInterface favoriteInterface) {
        this.mMovieInterface = favoriteInterface;
    }
}
