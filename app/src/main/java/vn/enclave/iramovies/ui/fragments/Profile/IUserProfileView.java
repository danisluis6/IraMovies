package vn.enclave.iramovies.ui.fragments.Profile;

import java.util.List;

import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 05/12/2017.
 */

public interface IUserProfileView {

    void onSuccess(List<User> users);

    void onFailure(String message);
}
