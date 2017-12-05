package vn.enclave.iramovies.ui.activities.base.Profile;

import android.content.Context;

import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 04/12/2017.
 */

public class EditUserProfilePresenter implements IEditUserProfilePresenter{

    /**
     * Context
     */
    private final Context mContext;

    /**
     * IInboxView
     */
    private IEditUserProfileView mEditUserProfileView;

    /**
     * IEditUserProfileModel
     */
    private IEditUserProfileModel mEditUserProfileModel;

    EditUserProfilePresenter(Context context) {
        this.mContext = context;
        mEditUserProfileModel = new EditUserProfileModel(mContext);
        mEditUserProfileModel.attachView(this);
    }

    @Override
    public void attachView(IEditUserProfileView view) {
        this.mEditUserProfileView = view;
    }

    @Override
    public void addUser(User user) {
        mEditUserProfileModel.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        mEditUserProfileModel.updateUser(user);
    }

    @Override
    public void onSuccess(User user) {
        mEditUserProfileView.onSuccess(user);
    }

    @Override
    public void onFailure(String message) {
        mEditUserProfileView.onFailure(message);
    }
}
