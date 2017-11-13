package vn.enclave.iramovies.services.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MoviesData {

    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;

    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;

    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;

    @SerializedName("countUnread")
    @Expose
    private Integer countUnread;

    @SerializedName("currentPageRecords")
    @Expose
    private List<FaxData> currentPageRecords;

    https://developers.themoviedb.org/3/movies/get-popular-movies
    https://developers.themoviedb.org/3/getting-started
    https://www.themoviedb.org/settings/api

}
