package vn.enclave.iramovies.ui.fragments.Detail;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.Reminder;
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

    void deleteMovie(Movie movie);

    void deleteMovieSuccess(Movie movie);

    void addMovieSuccess(Movie movie);

    void addMovie(Movie movie);

    void addReminder(Reminder reminder);

    void addReminderSuccess(Reminder reminder);

    void getReminderMovie(int id);

    void findReminderSuccess(Reminder reminder);

    void updateReminder(Reminder reminder);

    void updateReminderSuccess(Reminder mReminder);

    void getMovie(Integer id);

    void findMovieSuccess(Movie movie);
}
