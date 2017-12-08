package vn.enclave.iramovies.ui.fragments.Movie;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IMovieModel extends IBasePresenter<IMoviePresenter> {


    void getMoviesFromApi(int mPageIndex, MovieView.MODE mode);

    void addMovie(Movie movie);

    void deleteMovie(Movie movie);

    void updateReminder(Reminder reminder);
}