package vn.enclave.iramovies.local.storage;

/**
 * Created by lorence on 06/11/2017.
 * @Run: https://github.com/mobilesiri/Android-Sqlite-Database-Tutorial/blob/master/app/src/main/java/com/mobilesiri/sqliteexample/DBHandler.java
 * => Reference
 *
 * @Run: http://www.mobzystems.com/code/using-constants-for-table-and-column-names/
 * => Organize => Done
 */

public class DatabaseInfo {

    // Database version
    public static final int DB_VERSION = 2;

    // Database name
    public static final String DB_NAME = "iramovies";

    // Table User
    public static class Tables {
        public static final String User = "user";
        public static final String Movie = "movie";
    }

    // Columns of Video table
    public static class User {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_GENDER = "gender";
    }

    public static class Movie {
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    }
}
