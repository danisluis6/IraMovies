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
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "car_supper";

    // Video table name
    public static class Tables {
        public static final String Video = "video";
        public static final String Topic = "topic";
    }

    // Columns in Video table
    public static class Video {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_TOPIC_ID = "topic_id";
    }

    public static class Topic {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
    }
}
