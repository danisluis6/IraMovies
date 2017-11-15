package vn.enclave.iramovies.ui.fragments.Base;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.ui.activities.base.BaseView;
import vn.enclave.iramovies.ui.views.DialogView;

/**
 * Created by lorence on 08/11/2017.
 * @Run: https://stackoverflow.com/questions/34201431/can-i-make-abstract-fragment-class-with-builder-pattern
 *
 */

public abstract class IRBaseFragment extends Fragment {

    /**
     * Container activity
     */
    protected BaseView mActivity;
    private Unbinder mUnbinder;
    private View mView;
    protected AppDatabase mAppDatabase;
    protected DialogView mDiaLoadView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (BaseView)this.getActivity();
        mAppDatabase = Room.databaseBuilder(mActivity, AppDatabase.class, AppDatabase.DB_NAME).build();
        mView = getViewLayout(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mView);
        mDiaLoadView = new DialogView(mActivity);
        fragmentCreated();
        return mView;
    }

    public abstract View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public View getView() {
        return mView;
    }

    public abstract void fragmentCreated();

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
