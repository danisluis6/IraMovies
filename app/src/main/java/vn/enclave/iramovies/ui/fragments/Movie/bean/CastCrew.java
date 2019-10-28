package vn.enclave.iramovies.ui.fragments.Movie.bean;

/**
 *
 * Created by lorence on 24/11/2017.
 */

public class CastCrew {

    private String name;
    private String pathImage;

    public CastCrew(String name, String pathImage) {
        this.name = name;
        this.pathImage = pathImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
