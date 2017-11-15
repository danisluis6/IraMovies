package vn.enclave.iramovies.utilities;

/**
 *
 * Created by lorence on 03/11/2017.
 */

public class Constants {

    public static final String EMPTY_STRING = "";
    public static final int DOUBLE_CLICK_TIME_DELTA = 500;

    /** Intent information */
    public static final int GALLERY_REQUEST = 11;
    public static final int REVIEW_IMAGE = 22;
    public static final int VIEW_IMAGE = 33;

    /** Intent argument */
    public static final String IMAGE_PATH = "path_image";

    /** Index of these fragments in ViewPaper */
    public static final int MOVIES_INDEX = 0;
    public static final int FAVORITES_INDEX = 1;
    public static final int SETTING_INDEX = 2;
    public static final int ABOUT_INDEX = 3;
    public static final int FIRST_PAGE = 0;

    /* Toolbar Information */
    public static class ToolbarLayoutInfo {
        public static String _TITLE = "Home";
        public static String _BACKGROUND_COLOR = "#FAFBBA";
        public static String _TITLE_TEXT_COLOR = "#FFFFFF";
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
        public static final String MOVIE = "Movie";
        public static final String LOAD = "Load";
    }
}
