package vn.enclave.iramovies.ui.activities.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import vn.enclave.iramovies.local.storage.AppDatabase;
import vn.enclave.iramovies.utilities.Utils;


public abstract class BaseView extends AppCompatActivity{

    private static String TAG = Utils.makeLogTag(BaseView.class);

    /**
     * Context
     */
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutResId());
        setView();
        activityCreated(savedInstanceState);
    }

    private void setView() {
        mUnbinder = ButterKnife.bind(this);
    }

    public abstract int getLayoutResId();
    public abstract void activityCreated(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

}
