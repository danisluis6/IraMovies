package vn.enclave.iramovies.ui.fragments.Profile;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Reminder;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public interface IUserProfilePresenter extends IBasePresenter<IUserProfileView> {

    void onSuccess(List<User> users);

    void onFailure(String message);

    void getUser();

    void getListReminder();

    void onReminderSuccess(List<Reminder> groupReminders);

    void removeReminder(int id);

    void onDeleteReminderSuccess(Integer id);
}
