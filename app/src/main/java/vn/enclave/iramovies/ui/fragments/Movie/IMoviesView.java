package vn.enclave.iramovies.ui.fragments.Movie;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IMoviesView {

    void showProgressDialog(boolean isLoadMore);

    void dismissProgressDialog();

    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void addMovieSuccess(Movie movie);

    void deleteMovieSuccess(Movie movie);
}
