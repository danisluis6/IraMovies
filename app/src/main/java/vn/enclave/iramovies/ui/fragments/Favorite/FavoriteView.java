package vn.enclave.iramovies.ui.fragments.Favorite;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Favorite.adapter.FavoriteAdapter;
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

    /* RecyclerView */
    public RecyclerView rcvMovies;
    public RecyclerView.LayoutManager mLayoutManager;

    private FavoriteAdapter mFavoritesAdapter;
    private List<Movie> mGroupMovies;

    /** Work with MVP */
    private FavoritesPresenter mFavoritesPresenter;
    public FavoriteInterface mFavoriteInterface;
    public UpdatedFavoriteScreen mInterfaceRefresh;

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
            mFavoritesAdapter = new FavoriteAdapter(mActivity,mActivity, mGroupMovies);
        }
        rcvMovies.setAdapter(mFavoritesAdapter);

        mFavoritesAdapter.setRemoveFavoriteListener(new FavoriteAdapter.OnRemoveFavoriteListener() {
            @Override
            public void onRemove(Movie movie) {
                if (Utils.isDoubleClick()) {
                    return;
                }
                mFavoriteInterface.updateCountFavoritesOnMenu(movie.getFavorite());
                mFavoritesPresenter.deleteMovie(movie);
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

    @Override
    public void deleteSuccess(Movie movie) {
        mFavoritesAdapter.remove(movie);
        mInterfaceRefresh.onRefreshFavoriteOnMovieScreen(movie);
    }

    private void updateStatusFavorite(int count) {
        mFavoriteInterface.setTotalFavoritesOnMenu(count);
    }

    @Override
    public void onFailure(String message) {
        Utils.Toast.showToast(mActivity, message);
    }

    public void refreshStatusFavorite(Movie movie) {
        if (movie.getFavorite() == Constants.Favorites.DEFAULT) {
            mFavoritesAdapter.remove(movie);
        } else {
            mFavoritesAdapter.add(movie);
        }
    }

    public void filter(CharSequence mSearchKey) {
        if (mFavoritesAdapter != null) {
            mFavoritesAdapter.filter(mSearchKey);
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable search) {
            filter(search);
        }
    };

    public void filterAutomatic(EditText edtSearch) {
        edtSearch.addTextChangedListener(mTextWatcher);
    }

    public interface UpdatedFavoriteScreen {
        void onRefreshFavoriteOnMovieScreen(Movie movie);
    }

    /* Interface */
    public interface FavoriteInterface {
        void setTotalFavoritesOnMenu(int count);
        void updateCountFavoritesOnMenu(int value);
    }

    public void setFavoriteInterface(FavoriteInterface favoriteInterface) {
        this.mFavoriteInterface = favoriteInterface;
    }

    public void setOnRefreshFavoriteOnMovieScreen(UpdatedFavoriteScreen mInterfaceRefresh) {
        this.mInterfaceRefresh = mInterfaceRefresh;
    }
}
