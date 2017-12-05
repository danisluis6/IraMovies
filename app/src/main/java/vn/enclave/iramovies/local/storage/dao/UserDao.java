package vn.enclave.iramovies.local.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.User;

/**
 *
 * Created by lorence on 02/11/2017.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM "+ DatabaseInfo.Tables.User)
    List<User> getUsers();

    @Insert
    long insertUsers(User user);

    @Update
    int updateUsers(User... users);
}
