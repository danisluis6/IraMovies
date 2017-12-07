package vn.enclave.iramovies.local.storage.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 06/12/2017.
 */

@Entity(tableName = DatabaseInfo.Tables.Reminder)
public class Reminder implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_ID)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_ID)
    private Integer id;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_TITLE)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_REMINDER_DATE)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_REMINDER_DATE)
    private String reminderDate;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_POSTER_PATH)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_POSTER_PATH)
    private String posterPath;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_OVERVIEW)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_OVERVIEW)
    private String overview;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_RELEASE_DATE)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_RELEASE_DATE)
    private String releaseDate;

    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    @SerializedName(DatabaseInfo.Reminder.COLUMN_VOTE_AVERAGE)
    private Double voteAverage;

    @ColumnInfo(name = DatabaseInfo.Reminder.COLUMN_FAVORITE)
    private Integer favorite = Constants.Favorites.DEFAULT;

    public Reminder(){}

    protected Reminder(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.reminderDate = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.favorite = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.reminderDate);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.favorite);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }
}
