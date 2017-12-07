package vn.enclave.iramovies.ui.fragments.Reminder;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IReminderPresenter extends IBasePresenter<IReminderView> {

    // Copy IMovieView and paste it in here
    void onSuccess(Movie movie);

    void onFailure(String message);

    void getReminderDetail(int id);
}
