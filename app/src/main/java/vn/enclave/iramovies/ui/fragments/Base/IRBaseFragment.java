package vn.enclave.iramovies.ui.fragments.Base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lorence on 08/11/2017.
 * @Run: https://stackoverflow.com/questions/34201431/can-i-make-abstract-fragment-class-with-builder-pattern
 *
 */

public abstract class IRBaseFragment extends Fragment{

    private Unbinder mUnbinder;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentCreated();
        mView = getViewLayout(inflater, container, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, mView);
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
