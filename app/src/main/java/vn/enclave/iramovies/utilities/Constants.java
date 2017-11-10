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

}
