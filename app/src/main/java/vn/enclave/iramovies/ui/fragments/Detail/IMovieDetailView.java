package vn.enclave.iramovies.ui.fragments.Detail;

import java.util.List;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

interface IMovieDetailView {

    void onSuccess(List<Movie> movies);

    void onFailure(String message);
}
