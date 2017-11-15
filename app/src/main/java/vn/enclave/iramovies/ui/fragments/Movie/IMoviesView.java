package vn.enclave.iramovies.ui.fragments.Movie;

import java.util.List;

import vn.enclave.iramovies.services.response.MovieData;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IMoviesView {

    void showProgressDialog(boolean isLoadMore);

    void dismissProgressDialog();

    void onSuccess(List<MovieData> movies);

    void onFailure(String message);
}
