package vn.enclave.iramovies.services.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import vn.enclave.iramovies.local.storage.DatabaseInfo;
import vn.enclave.iramovies.utilities.Constants;

/**
 *
 * Created by lorence on 13/11/2017.
 */

public class MovieData implements Parcelable {

    @SerializedName(DatabaseInfo.Movie.COLUMN_POSTER_PATH)
    private String posterPath;
    @SerializedName(DatabaseInfo.Movie.COLUMN_ADULT)
    private boolean adult;
    @SerializedName(DatabaseInfo.Movie.COLUMN_OVERVIEW)
    private String overview;
    @SerializedName(DatabaseInfo.Movie.COLUMN_RELEASE_DATE)
    private String releaseDate;
    @SerializedName(DatabaseInfo.Movie.COLUMN_GENRE_IDS)
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName(DatabaseInfo.Movie.COLUMN_ID)
    private Integer id;
    @SerializedName(DatabaseInfo.Movie.COLUMN_ORIGINAL_TITLE)
    private String originalTitle;
    @SerializedName(DatabaseInfo.Movie.COLUMN_ORIGINAL_LANGUAGE)
    private String originalLanguage;
    @SerializedName(DatabaseInfo.Movie.COLUMN_TITLE)
    private String title;
    @SerializedName(DatabaseInfo.Movie.COLUMN_BACKDROP_PATH)
    private String backdropPath;
    @SerializedName(DatabaseInfo.Movie.COLUMN_POPULARITY)
    private Double popularity;
    @SerializedName(DatabaseInfo.Movie.COLUMN_VOTE_COUNT)
    private Integer voteCount;
    @SerializedName(DatabaseInfo.Movie.COLUMN_VIDEO)
    private Boolean video;
    @SerializedName(DatabaseInfo.Movie.COLUMN_VOTE_AVERAGE)
    private Double voteAverage;

    private String type = Constants.Objects.MOVIE;

    public MovieData(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                     String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                     Integer voteCount, Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
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

    public MovieData(){
    }

    public MovieData(String type) {
        this.type = type;
    }

    public static final Comparator<MovieData> BY_NAME_ALPHABETICAL = new Comparator<MovieData>() {
        @Override
        public int compare(MovieData movieData, MovieData t1) {
            return movieData.originalTitle.compareTo(t1.originalTitle);
        }
    };

    public String getPosterPath() {
        return  posterPath;
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
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

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
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
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeList(this.genreIds);
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.type);
    }

    protected MovieData(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
