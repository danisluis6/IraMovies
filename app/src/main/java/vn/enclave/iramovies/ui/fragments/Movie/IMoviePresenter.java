package vn.enclave.iramovies.ui.fragments.Movie;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IMoviePresenter extends IBasePresenter<IMovieView> {

    // Copy IMovieView and paste it in here
    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void getMoviesFromApi(int mPageIndex, boolean isLoadMore, MovieView.MODE mode);

    void addMovie(Movie movie);

    void deleteMovie(Movie movie);

    void addMovieSuccess(Movie movie);

    void deleteMovieSuccess(Movie movie);

    void updateReminder(Reminder reminder);

    void onUpdatedReminderSuccess(Reminder mReminder);
}
