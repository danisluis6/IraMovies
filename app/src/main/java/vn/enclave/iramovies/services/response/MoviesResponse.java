package vn.enclave.iramovies.services.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MoviesResponse {
    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("data")
    @Expose
    private InboxData data;


    public Error getError() {
        return error;
    }

    public InboxData getData() {
        return data;
    }
}
