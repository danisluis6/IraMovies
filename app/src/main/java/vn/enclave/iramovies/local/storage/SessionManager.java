package vn.enclave.iramovies.local.storage;

import android.content.Context;
import android.content.SharedPreferences;

import vn.enclave.iramovies.utilities.Constants;

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
     * Total Pages
     */
    private static final String TOTAL_PAGES = "total_pages";

    /**
     * Category
     */
    private static final String CATEGORY = "category";

    /**
     * Release Year
     */
    private static final String RELEASE_YEAR = "release_year";

    /**
     * Release Date
     */
    private static final String RELEASE_DATE = "release_date";

    /**
     * Rate
     */
    private static final String RATE = "rate";

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

    public String getCategory() {
        return pref.getString(CATEGORY, Constants.EMPTY_STRING);
    }

    public void setCategory(String category) {
        editor.putString(CATEGORY, category);
        editor.apply();
    }

    public String getReleaseYear() {
        return pref.getString(RELEASE_YEAR, Constants.EMPTY_STRING);
    }

    public void setReleaseYear(String releaseYear) {
        editor.putString(RELEASE_YEAR, releaseYear);
        editor.apply();
    }

    public String getReleaseDate() {
        return pref.getString(RELEASE_DATE, Constants.EMPTY_STRING);
    }

    public void setReleaseDate(String releaseDate) {
        editor.putString(RELEASE_DATE, releaseDate);
        editor.apply();
    }

    public String getRate() {
        return pref.getString(RATE, Constants.EMPTY_STRING);
    }

    public void setRate(String rate) {
        editor.putString(RATE, rate);
        editor.apply();
    }

}
