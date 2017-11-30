package vn.enclave.iramovies.services.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by lorence on 24/11/2017.
 */

public class CastAndCrewResponse {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("cast")
    @Expose
    private List<CastResponse> cast;

    @SerializedName("crew")
    @Expose
    private List<CrewResponse> crew;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CastResponse> getCast() {
        return cast;
    }

    public void setCast(List<CastResponse> cast) {
        this.cast = cast;
    }

    public List<CrewResponse> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewResponse> crew) {
        this.crew = crew;
    }
}
