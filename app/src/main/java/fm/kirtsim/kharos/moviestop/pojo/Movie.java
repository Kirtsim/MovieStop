package fm.kirtsim.kharos.moviestop.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.db.typeCoverter.movie.GenreIdsTypeConverter;
import fm.kirtsim.kharos.moviestop.db.typeCoverter.movie.StatusConverter;

/**
 * Created by kharos on 09/02/2018
 */

@Entity(primaryKeys = "id")
public class Movie {


    @SerializedName("vote_count")
    @Expose private int voteCount;

    @SerializedName("id")
    @Expose private int id;

    @SerializedName("video")
    @Expose private boolean video;

    @SerializedName("vote_average")
    @Expose private double voteAverage;

    @SerializedName("title")
    @Expose private String title = "";

    @SerializedName("popularity")
    @Expose private double popularity;

    @SerializedName("poster_path")
    @Expose private String posterPath = "";

    @SerializedName("original_language")
    @Expose private String originalLanguage = "";

    @TypeConverters(GenreIdsTypeConverter.class)
    @SerializedName("genre_ids")
    @Expose private List<Integer> genreIds = Collections.emptyList();

    @SerializedName("backdrop_path")
    @Expose private String backdropPath = "";

    @SerializedName("adult")
    @Expose private boolean adult;

    @SerializedName("overview")
    @Expose
    private String overview = "";

    @SerializedName("release_date")
    @Expose private String releaseDate = "";

    @TypeConverters(StatusConverter.class)
    private List<String> status;

    public Movie() {}

    public Movie(Movie other) {
        voteCount = other.voteCount;
        id = other.id;
        video = other.video;
        voteAverage = other.voteAverage;
        title = other.title;
        popularity = other.popularity;
        posterPath = other.posterPath;
        originalLanguage = other.originalLanguage;
        genreIds = other.genreIds;
        backdropPath = other.backdropPath;
        adult = other.adult;
        overview = other.overview;
        releaseDate = other.releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setTitle(String title) {
        if (title == null)
            title = "";
        this.title = title;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        if (posterPath == null)
            posterPath = "";
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        if (originalLanguage == null)
             originalLanguage = "";
        this.originalLanguage = originalLanguage;
    }

    public void setGenreIds(List<Integer> genreIds) {
        if (genreIds == null)
            genreIds = Collections.emptyList();
        this.genreIds = genreIds;
    }

    public void setBackdropPath(String backdropPath) {
        if (backdropPath == null)
            backdropPath = "";
        this.backdropPath = backdropPath;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setOverview(String overview) {
        if (overview == null)
            overview = "";
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        if (releaseDate == null)
            releaseDate = "";
        this.releaseDate = releaseDate;
    }

    public void setStatus(List<String> status) {
        this.status = new ArrayList<>(status.size());
        for (String singleStatus : status)
            addStatus(singleStatus);
    }

    public void addStatus(String newStatus) {
        if (!isStatusValid(newStatus) || status.contains(newStatus))
            return;
        status.add(newStatus);
    }

    private boolean isStatusValid(String status) {
        return status.equals(STATUS_FEATURED) ||
                status.equals(STATUS_POPULAR) ||
                status.equals(STATUS_UPCOMING) ||
                status.equals(STATUS_TOP_RATED);
    }

    public static class Builder {
        private final Movie movie;

        public Builder() {
            movie = new Movie();
        }

        public Builder title(String title) {
            movie.title = title;
            return this;
        }

        public Movie build() {
            return new Movie(movie);
        }
    }

    @Override
    public String toString() {
        return "Movie - title: " + title + " - bckURL: " + backdropPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Movie)) return false;

        Movie movie = (Movie) obj;
        return voteCount == movie.voteCount &&
                id == movie.id &&
                video == movie.video &&
                voteAverage == movie.voteAverage &&
                title.equals(movie.title) &&
                popularity == movie.popularity &&
                posterPath.equals(movie.posterPath) &&
                originalLanguage.equals(movie.originalLanguage) &&
                genreIds == movie.genreIds &&
                backdropPath.equals(movie.backdropPath) &&
                adult == movie.adult &&
                overview.equals(movie.overview) &&
                releaseDate.equals(movie.releaseDate);
    }
}
