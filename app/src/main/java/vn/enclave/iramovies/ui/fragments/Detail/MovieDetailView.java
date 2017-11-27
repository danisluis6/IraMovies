package vn.enclave.iramovies.ui.fragments.Detail;

import android.content.Context;
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
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.services.IraMovieInfoAPIs;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.ui.fragments.Base.IRBaseFragment;
import vn.enclave.iramovies.ui.fragments.Detail.adapter.MovieDetailAdapter;
import vn.enclave.iramovies.ui.fragments.Movie.bean.CastCrew;
import vn.enclave.iramovies.ui.views.ToolbarLayout;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 23/11/2017.
 */

/**
 * @Run: https://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments
 * => Done
 *
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

    private Movie mMovie;
    private ToolbarLayout mToolbar;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void fragmentCreated() {
        setMovie(getMovieDetail());;
        displayMovieDetail(getMovie());
        initAtribute();
        getCastAndCrew();
    }

    private void initAtribute() {
        mDetailMoviePresenter = new MovieDetailPresenter(mActivity);
        mDetailMoviePresenter.attachView(this);
    }

    private void displayMovieDetail(Movie movie) {
        imvFavorite.setImageResource((movie.getFavorite() == Constants.Favorites.FAVORITE) ? R.drawable.ic_star_picked : R.drawable.ic_star);
        tvReleaseDate.setText(movie.getReleaseDate());
        tvRating.setText(String.valueOf(movie.getVoteAverage()));
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
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

    public void getCastAndCrew() {
        mDetailMoviePresenter.getCastAndCrewFromApi(getMovie().getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    public void setToolbar(ToolbarLayout toolbar) {
        this.mToolbar = toolbar;
    }
}
