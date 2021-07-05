package vn.enclave.iramovies.services.response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 13/11/2017.
 */

@Entity(tableName = DatabaseInfo.Tables.Movie)
public class Movie implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ID)
    @SerializedName(DatabaseInfo.Movie.COLUMN_ID)
    private Integer id;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_TITLE)
    @SerializedName(DatabaseInfo.Movie.COLUMN_TITLE)
    private String title;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ORIGINAL_TITLE)
    @SerializedName(DatabaseInfo.Movie.COLUMN_ORIGINAL_TITLE)
    private String originalTitle;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ORIGINAL_LANGUAGE)
    @SerializedName(DatabaseInfo.Movie.COLUMN_ORIGINAL_LANGUAGE)
    private String originalLanguage;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_POSTER_PATH)
    @SerializedName(DatabaseInfo.Movie.COLUMN_POSTER_PATH)
    private String posterPath;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_BACKDROP_PATH)
    @SerializedName(DatabaseInfo.Movie.COLUMN_BACKDROP_PATH)
    private String backdropPath;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_OVERVIEW)
    @SerializedName(DatabaseInfo.Movie.COLUMN_OVERVIEW)
    private String overview;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_RELEASE_DATE)
    @SerializedName(DatabaseInfo.Movie.COLUMN_RELEASE_DATE)
    private String releaseDate;

    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    @SerializedName(DatabaseInfo.Movie.COLUMN_VOTE_AVERAGE)
    private Double voteAverage;

    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_FAVORITE)
    private Integer favorite = Constants.Favorites.DEFAULT;

    @Ignore
    private String type = Constants.Objects.MOVIE;

    @Ignore
    public Movie(String posterPath, String overview, String releaseDate, Integer id,
                 String originalTitle, String originalLanguage, String title, String backdropPath,
                 Double voteAverage, int favorite) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.favorite = favorite;
    }

    public Movie(){
    }

    public Movie(String type) {
        this.type = type;
    }

    public static final Comparator<Movie> BY_NAME_ALPHABETICAL = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {
            return movie.originalTitle.compareTo(t1.originalTitle);
        }
    };

    public String getPosterPath() {
        return  posterPath;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.type);
        dest.writeValue(this.favorite);
    }

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.type = in.readString();
        this.favorite = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFavorite() { return favorite; }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }
}
