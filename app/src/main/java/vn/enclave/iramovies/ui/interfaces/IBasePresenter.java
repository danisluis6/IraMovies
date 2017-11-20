package vn.enclave.iramovies.ui.interfaces;

import vn.enclave.iramovies.services.response.Movie;

/**
 *
 * Created by lorence on 14/11/2017.
 */

public interface IBasePresenter<V> {
    void attachView(V view);
}
