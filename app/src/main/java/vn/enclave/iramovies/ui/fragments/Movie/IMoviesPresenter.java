package vn.enclave.iramovies.ui.fragments.Movie;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 * Created by lorence on 13/11/2017.
 */

public interface IMoviesPresenter extends IBasePresenter<IMoviesView> {

    // Copy IMoviesView and paste it in here
    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void getMoviesFromApi(int mPageIndex);
}
