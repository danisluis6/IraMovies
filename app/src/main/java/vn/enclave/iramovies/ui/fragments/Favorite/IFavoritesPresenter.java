package vn.enclave.iramovies.ui.fragments.Favorite;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IFavoritesPresenter extends IBasePresenter<IFavoritesView> {

    void getMoviesFromLocal();

    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void addMovie(Movie movie);

    void deleteMovie(Movie movie);

    void deleteSuccess(Movie movie);
}
