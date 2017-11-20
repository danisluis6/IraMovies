package vn.enclave.iramovies.ui.fragments.Favorite;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IFavoritesView {

    void showProgressDialog();

    void dismissProgressDialog();

    void onSuccess(List<Movie> movies);

    void onFailure(String message);

    void deleteSuccess(Movie movie);
}
