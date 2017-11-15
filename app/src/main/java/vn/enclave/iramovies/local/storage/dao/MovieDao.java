package vn.enclave.iramovies.local.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Movie;

/**
 *
 * Created by lorence on 13/11/2017.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM "+ DatabaseInfo.Tables.User)
    List<Movie> getMovies();

    @Insert
    long insertMovies(Movie movie);

    @Update
    int updateMovies(Movie... movies);

    @Delete
    int deleteMovies(Movie... movies);

}
