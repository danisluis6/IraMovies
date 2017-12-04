package vn.enclave.iramovies.ui.activities.base.Profile;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.Movie;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.ui.interfaces.IBasePresenter;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public interface IEditUserProfilePresenter extends IBasePresenter<IEditUserProfileView> {

    void onSuccess(User user);

    void onFailure(String message);

    void addUser(User user);
}
