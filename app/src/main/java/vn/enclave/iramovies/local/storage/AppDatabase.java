package vn.enclave.iramovies.local.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import vn.enclave.iramovies.local.storage.dao.MovieDao;
import vn.enclave.iramovies.local.storage.dao.UserDao;
import vn.enclave.iramovies.local.storage.entity.User;
import vn.enclave.iramovies.services.response.Movie;

/**
 * @author acampbell
 */
@Database(entities = {User.class, Movie.class}, version = DatabaseInfo.DB_VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = DatabaseInfo.DB_NAME;

    public abstract UserDao getUserDao();

    public abstract MovieDao getMovieDao();

}



