package vn.enclave.iramovies.utilities;

/**
 *
 * Created by lorence on 03/11/2017.
 */

public class Constants {

    public static final String EMPTY_STRING = "";
    public static final int DOUBLE_CLICK_TIME_DELTA = 400;

    /** Intent information */
    public static class Activities_Result {
        public static final int GALLERY = 1;
        public static final int CAMERA = 2;
        public static final int USER = 3;
    }

    /* Dialog show pick image and gallery in Android */
    public final static String DIALOG_CHOOSER = "Dialog_Chooser";

    /** Intent argument */
    public static final String IMAGE_PATH = "path_image";

    /** Index of these fragments in ViewPaper */
    public static final int FIRST_PAGE = 1;
    public static final String REGEX_SPACE = "\\s+";

    public static class Tab {
        public static int Movie = 0;
        public static int Favorite = 1;
        public static int Setting = 2;
        public static int About = 3;
    }

    public static class Parcelable {
        public static String USER = "user";
        public static String LIST_REMINDER = "reminders";
        public static String REMINDER = "reminder";
    }

    /* Toolbar Information */
    public static class ToolbarLayoutInfo {
        public static String _TITLE = "Home";
    }


    /* Check permission */
    public static class Permissions {
        public static final int CAMERA = 1;
        public static final int INTERNET = 2;
        public static final int STORAGE = 3;
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

    public static class Bundle {
        public static String TYPE = "type";
        public static String MOVIE = "movie";
        public static String REMINDER = "reminder";
    }

    public static String URL = "https://www.themoviedb.org/";

    public static String IMAGE_FOLDER = "IraMovies";
}
