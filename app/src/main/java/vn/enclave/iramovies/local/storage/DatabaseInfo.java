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
    public static final int DB_VERSION = 1;

    // Database name
    public static final String DB_NAME = "iramovies";

    // Table User
    public static class Tables {
        public static final String User = "user";
    }

    // Columns of Video table
    public static class User {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_GENDER = "gender";
    }
}
