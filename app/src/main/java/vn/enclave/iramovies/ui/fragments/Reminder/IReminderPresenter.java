package vn.enclave.iramovies.ui.fragments.Reminder;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/*
 * Created by lorence on 13/11/2017.
 */

public interface IReminderPresenter extends IBasePresenter<IReminderView> {

    // Copy IMovieView and paste it in here
    void onSuccess(Reminder reminder);

    void onFailure(String message);

    void updateReminder(Reminder reminderUpdated);

    void getListReminder();

    void onReminderSuccess(List<Reminder> groupReminders);
}
