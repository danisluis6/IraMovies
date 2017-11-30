package vn.enclave.iramovies.ui.fragments.Detail;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IMovieDetailModel extends IBasePresenter<IMovieDetailPresenter> {

    void getCastAndCrewFromApi(int movieId);

    void deleteMovie(Movie movie);

    void cancelProcessing();

    void addMovie(Movie movie);
}