package vn.enclave.iramovies.ui.fragments.Detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Detail.adapter.MovieDetailAdapter;
import vn.enclave.iramovies.ui.fragments.Movie.bean.CastCrew;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 *
 * Created by lorence on 23/11/2017.
 */

/**
 * @Run: https://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments
 * => Done
 */

public class MovieDetailView extends IRBaseFragment implements IMovieDetailView {

    @BindView(R.id.imvFavorite)
    ImageView imvFavorite;

    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;

    @BindView(R.id.tvRating)
    TextView tvRating;

    @BindView(R.id.imvThumnail)
    ImageView imvThumnail;

    @BindView(R.id.rcvCast)
    RecyclerView rcvCast;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    /**
     * Work with MVP
     */
    private MovieDetailPresenter mDetailMoviePresenter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Movie mMovie;
    private boolean mIsFavorite;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void fragmentCreated() {
        setMovie(getMovieDetail());
        displayMovieDetail(getMovie());
        mIsFavorite = (getMovie().getFavorite() == Constants.Favorites.FAVORITE);
        initAtribute();
        getCastAndCrew();
    }

    @OnClick(R.id.imvFavorite)
    public void onClick() {
        if (Utils.isDoubleClick()) {
            return;
        }
        mIsFavorite = !mIsFavorite;
        imvFavorite.setImageResource(mIsFavorite ? R.drawable.ic_star_picked : R.drawable.ic_star);
        getMovie().setFavorite(mIsFavorite ? Constants.Favorites.FAVORITE : Constants.Favorites.DEFAULT);
        if (getMovie().getFavorite() == Constants.Favorites.DEFAULT) {
            mDetailMoviePresenter.deleteMovie(getMovie());
        } else {
            mDetailMoviePresenter.addMovie(getMovie());
        }
        mMovieDetailInterface.updateCountStarOnMenu(getMovie().getFavorite());
        mMovieDetailInterface.refreshStarInFavoriteScreen(getMovie());
        mMovieDetailInterface.refreshStarInMovieScreen(getMovie());
        mMovieDetailInterface.refreshStarInDetailScreen(getMovie());
    }

    private void initAtribute() {
        mDetailMoviePresenter = new MovieDetailPresenter(mActivity);
        mDetailMoviePresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
    }

    private void displayMovieDetail(Movie movie) {
        imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(String.valueOf(movie.getVoteAverage()) + Constants.Keyboards.FORWARD_SLASH + "10.0");
        tvOverview.setText(movie.getOverview());
        String poster = IraMovieInfoAPIs.Images.Thumbnail + movie.getPosterPath();
        Glide.with(mActivity)
                .load(poster)
                .placeholder(R.drawable.load)
                .into((imvThumnail));
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }

    public Movie getMovieDetail() {
        Movie movie = new Movie();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            movie.setId(bundle.getInt(DatabaseInfo.Movie.COLUMN_ID));
            movie.setTitle(bundle.getString(DatabaseInfo.Movie.COLUMN_TITLE));
            movie.setPosterPath(bundle.getString(DatabaseInfo.Movie.COLUMN_POSTER_PATH));
            movie.setOverview(bundle.getString(DatabaseInfo.Movie.COLUMN_OVERVIEW));
            movie.setReleaseDate(bundle.getString(DatabaseInfo.Movie.COLUMN_RELEASE_DATE));
            movie.setVoteAverage(bundle.getDouble(DatabaseInfo.Movie.COLUMN_VOTE_AVERAGE));
            movie.setFavorite(bundle.getInt(DatabaseInfo.Movie.COLUMN_FAVORITE));
        }
        return movie;
    }

    @Override
    public void onSuccess(CastAndCrewResponse castAndCrewResponse) {
        loadCastAndCrew(getPathOfCastAndCrew(castAndCrewResponse));
    }



    private void loadCastAndCrew(List<CastCrew> castCrewList) {
        MovieDetailAdapter movieDetailAdapter = new MovieDetailAdapter(mActivity, mActivity, castCrewList);
        rcvCast.setLayoutManager(mLayoutManager);
        rcvCast.setAdapter(movieDetailAdapter);
    }

    private List<CastCrew> getPathOfCastAndCrew(CastAndCrewResponse castAndCrewResponse) {
        List<CastCrew> mCastCrews = new ArrayList<>();
        for (int i = 0; i < castAndCrewResponse.getCast().size(); i++) {
            mCastCrews.add(new CastCrew(castAndCrewResponse.getCast().get(i).getName(), castAndCrewResponse.getCast().get(i).getProfilePath()));
        }
        for (int j = 0; j < castAndCrewResponse.getCrew().size(); j++) {
            mCastCrews.add(new CastCrew(castAndCrewResponse.getCrew().get(j).getName(), castAndCrewResponse.getCrew().get(j).getProfilePath()));
        }
        return mCastCrews;
    }

    @Override
    public void onFailure(String message) {
        // TODO
    }

    @Override
    public void deleteMovieSuccess(Movie movie) {
        // TODO
    }

    @Override
    public void addMovieSuccess(Movie movie) {
        // TODO
    }

    public void getCastAndCrew() {
        mDetailMoviePresenter.getCastAndCrewFromApi(getMovie().getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mDetailMoviePresenter.cancelProcessing();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mMovieDetailInterface.onDestroy();
        super.onDetach();
    }

    public void setMovieDetailInterface(MovieDetailInterface movieDetailInterface) {
        this.mMovieDetailInterface = movieDetailInterface;
    }

    private  MovieDetailInterface  mMovieDetailInterface;

    public void reload(Movie movie) {
        if (imvFavorite != null && getMovie().getId().equals(movie.getId())) {
            imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
        }
        onResume();
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public interface MovieDetailInterface {
        void onDestroy();
        void updateCountStarOnMenu(int value);
        // Refresh favorite in favorite screen
        void refreshStarInFavoriteScreen(Movie movie);
        // Refresh favorite in movie screen
        void refreshStarInMovieScreen(Movie movie);
        // Refresh favorite in Detail screen
        void refreshStarInDetailScreen(Movie movie);
    }

}
