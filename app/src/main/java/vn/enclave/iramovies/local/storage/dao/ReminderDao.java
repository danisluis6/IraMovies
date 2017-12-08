package vn.enclave.iramovies.local.storage.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.local.storage.entity.Reminder;

/**
 *
 * Created by lorence on 13/11/2017.
 */

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM "+ DatabaseInfo.Tables.Reminder)
    List<Reminder> getReminders();

    @Insert
    long insertReminders(Reminder reminder);

    @Query("SELECT * FROM "+DatabaseInfo.Tables.Reminder+" WHERE "+DatabaseInfo.Reminder.COLUMN_ID+" = :id")
    Reminder getReminderMovie(Integer id);

    @Update
    int updateReminders(Reminder... reminders);

    @Query("UPDATE "+DatabaseInfo.Tables.Reminder+" SET "+DatabaseInfo.Reminder.COLUMN_FAVORITE+" = :favorite WHERE "+DatabaseInfo.Reminder.COLUMN_ID+" = :id")
    int updateReminder(Integer favorite, Integer id);
}
