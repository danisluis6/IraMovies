package vn.enclave.iramovies.utilities;

/**
 *
 * Created by lorence on 03/11/2017.
 */

public class Constants {

    public static final String EMPTY_STRING = "";
    public static final int DOUBLE_CLICK_TIME_DELTA = 600;

    /** Intent information */
    public static final int GALLERY_REQUEST = 11;
    public static final int REVIEW_IMAGE = 22;
    public static final int VIEW_IMAGE = 33;

    /** Intent argument */
    public static final String IMAGE_PATH = "path_image";

    /** Index of these fragments in ViewPaper */
    public static final int FIRST_PAGE = 1;
    public static class Tab {
        public static int Movie = 0;
        public static int Favorite = 1;
        public static int Setting = 2;
        public static int About = 3;
    }

    /* Toolbar Information */
    public static class ToolbarLayoutInfo {
        public static String _TITLE = "Home";
    }


    /* Check permission */
    public static class Permissions {
        public static final int ACCESS_INTERNET = 1;
        public static final int ACCESS_EXTERNAL_STORAGE = 2;
    }

    public static class Keyboards {
        public static final String FORWARD_SLASH = "/";
    }

    public static class Objects {
        public static final String MOVIE = "fds";
        public static final String LOAD = "Load";
    }

    public static class Favorites {
        public static int DEFAULT = 0;
        public static int FAVORITE = 1;
    }

    public static String URL = "https://www.themoviedb.org/";
}
