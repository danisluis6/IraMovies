package vn.enclave.iramovies.ui.fragments.Detail;

import vn.enclave.iramovies.services.response.CastAndCrewResponse;

/**
 *
 * Created by lorence on 13/11/2017.
 */

interface IMovieDetailView {

    void onSuccess(CastAndCrewResponse castAndCrewResponse);

    void onFailure(String message);
}
