package vn.enclave.iramovies.ui.fragments.Movie;

import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IMoviesModel extends IBasePresenter<IMoviesPresenter> {


    void getMoviesFromApi(int mPageIndex);

    void addMovie(Movie movie);
}