package vn.enclave.iramovies.ui.activities.base.Profile;

import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public interface IEditUserProfileView {

    void onSuccess(User user);

    void onFailure(String message);

}
