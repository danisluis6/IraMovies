package vn.enclave.iramovies.ui.fragments.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import butterknife.BindView;
import vn.enclave.iramovies.R;
import vn.enclave.iramovies.ui.fragments.IRBaseFragment;
import vn.enclave.iramovies.ui.views.FailureLayout;
import vn.enclave.iramovies.utilities.Constants;
import vn.enclave.iramovies.utilities.Utils;

/**
 * Created by lorence on 08/11/2017.
 * @Run: https://www.mkyong.com/android/android-webview-example/
 */

public class AboutView extends IRBaseFragment{

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.failureLayout)
    public FailureLayout mFailureLayout;

    @Override
    public View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void fragmentCreated() {
        openOfficalWebsite();
    }

    private void openOfficalWebsite() {


        if (Utils.isInternetOn(mActivity)) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(Constants.URL);
        } else {
            mFailureLayout.setFailureMessage(getResources().getString(R.string.no_internet_connection));
        }
    }
}
