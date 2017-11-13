package vn.enclave.iramovies.services;

/**
 *
 * Created by lorence on 13/11/2017.
 *
 * @Run: https://www.youtube.com/watch?v=OOLFhtyCspA
 * => Done
 *
 *
 */

public class InformationAPI {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "2d390f2deaa23e8b65d42a80beb8c1bd";
    public static class Catogories {
        static String Movies = "movie";
        static class Movies {
            static String Popular = "movie/popular";
            static String Now_playing = "movie/now_playing";
            static String Top_Rated = "movie/top_rated";
            static String Upcoming = "movie/upcoming";
        }
    }
}
