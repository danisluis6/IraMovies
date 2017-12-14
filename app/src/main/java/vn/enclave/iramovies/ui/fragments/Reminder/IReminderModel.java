package vn.enclave.iramovies.ui.fragments.Reminder;

import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public interface IReminderModel extends IBasePresenter<IReminderPresenter> {

    void updateReminder(Reminder reminder);

    void getListReminder();
}