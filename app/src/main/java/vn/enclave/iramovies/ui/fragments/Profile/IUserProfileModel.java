package vn.enclave.iramovies.ui.fragments.Profile;

import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public interface IUserProfileModel extends IBasePresenter<IUserProfilePresenter> {

    void getUser();

    void getListReminder();

    void removeReminder(int id);
}
