package vn.enclave.iramovies.ui.fragments.Reminder;

import vn.enclave.iramovies.local.storage.entity.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

interface IReminderView {

    void onSuccess(Movie movie);

    void onFailure(String message);
}
