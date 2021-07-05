package vn.enclave.iramovies.ui.fragments.Favorite;

import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.fragments.Movie.IMoviesPresenter;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IFavoritesModel extends IBasePresenter<IFavoritesPresenter> {

    void getMoviesFromLocal();

    void addMovie(Movie movie);

    void deleteMovie(Movie movie);
}