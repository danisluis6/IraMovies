package vn.enclave.iramovies.ui.fragments.Detail;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.services.response.CastAndCrewResponse;

/**
 *
 * Created by lorence on 13/11/2017.
 */

interface IMovieDetailView {

    void onSuccess(CastAndCrewResponse castAndCrewResponse);

    void onFailure(String message);

    void deleteMovieSuccess(Movie movie);

    void addMovieSuccess(Movie movie);

    void addReminderSuccess(Reminder reminder);

    void findReminderSuccess(Reminder reminder);

    void updateReminderSuccess(Reminder reminder);
}
