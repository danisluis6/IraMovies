package vn.enclave.iramovies.ui.fragments.Favorite;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IFavoriteModel extends IBasePresenter<IFavoritePresenter> {

    void getMoviesFromLocal();

    void addMovie(Movie movie);

    void deleteMovie(Movie movie);
}