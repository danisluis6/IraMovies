package vn.enclave.iramovies.local.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * Created by lorence on 15/11/2017.
 */

public class SessionManager {

    /**
     * Shared preferences file name
     */
    private static final String PREF_NAME = "iraMovies_sharedPref";
    /**
     * Shared preferences Instance
     */
    private static SessionManager instance;
    /**
     * Shared Preferences
     */
    private final SharedPreferences pref;
    /**
     * Editor for shared preferences
     */
    private final SharedPreferences.Editor editor;

    /**
     * totalPages
     */
    private static final String TOTAL_PAGES = "total_pages";

    /**
     * Constructor
     *
     * @param context the context
     */
    private SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public static SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public int getTotalPages() {
        return pref.getInt(TOTAL_PAGES, 0);
    }

    public void setTotalPages(int totalPages) {
        editor.putInt(TOTAL_PAGES, totalPages);
        editor.apply();
    }

}
