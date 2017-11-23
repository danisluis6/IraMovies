package vn.enclave.iramovies.ui.fragments.Detail;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.fragments.Movie.IMoviesView;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IMovieDetailPresenter extends IBasePresenter<IMovieDetailView> {

    // Copy IMoviesView and paste it in here
    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void getCastAndCrewFromApi(int movieId);
}
