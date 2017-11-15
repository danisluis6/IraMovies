package vn.enclave.iramovies.local.storage.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import vn.enclave.iramovies.local.storage.DatabaseInfo;

@Entity(tableName = DatabaseInfo.Tables.Movie)
public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_POSTER_PATH)
    private String posterPath;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ADULT)
    private boolean adult;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_OVERVIEW)
    private String overview;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_RELEASE_DATE)
    private String releaseDate;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_GENRE_IDS)
    private String genreId;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ID)
    private String id;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ORIGINAL_TITLE)
    private String originalTitle;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_ORIGINAL_LANGUAGE)
    private String originalLanguage;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_TITLE)
    private String title;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_BACKDROP_PATH)
    private String backdropPath;
    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    private Double popularity;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_VOTE_COUNT)
    private int voteCount;
    @ColumnInfo(name = DatabaseInfo.Movie.COLUMN_VIDEO)
    private int video;
    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    private Double voteAverage;

    protected Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        genreId = in.readString();
        id = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        voteCount = in.readInt();
        video = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(genreId);
        dest.writeString(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeInt(voteCount);
        dest.writeInt(video);
    }

    public Movie() {
    }

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, String genreId, String id, String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity, int voteCount, int video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVideo() {
        return video;
    }

    public void setVideo(int video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
