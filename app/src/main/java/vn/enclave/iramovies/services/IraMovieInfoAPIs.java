package vn.enclave.iramovies.services;

import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 13/11/2017.
 *
 * @Run: https://www.youtube.com/watch?v=OOLFhtyCspA
 * => Done
 *
 * @Run: https://developer.android.com/studio/build/index.html
 * => Done
 */

public class IraMovieInfoAPIs {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static class Categories {
        final static String Popular = "movie/popular";
        final static String Now_playing = "movie/now_playing";
        final static String Top_Rated = "movie/top_rated";
        final static String Upcoming = "movie/upcoming";
        final static String Detail = "movie/{movie_id}/credits";
    }

    public static class Images {
        public final static String Small = "http://image.tmdb.org/t/p/w185";
        public final static String Thumbnail = "http://image.tmdb.org/t/p/w500";
    }

    public static class Fields {
        public final static String API_KEY = "api_key";
        public final static String PAGE = "page";
    }
}
