package vn.enclave.iramovies.ui.fragments.Detail;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IMovieDetailPresenter extends IBasePresenter<IMovieDetailView> {

    // Copy IMovieView and paste it in here
    void onSuccess(CastAndCrewResponse castAndCrewResponse);

    void onFailure(String message);

    void getCastAndCrewFromApi(int movieId);

    void cancelProcessing();
}
