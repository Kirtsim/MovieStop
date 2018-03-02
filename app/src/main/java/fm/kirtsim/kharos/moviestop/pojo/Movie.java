package fm.kirtsim.kharos.moviestop.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kharos on 09/02/2018
 */

public class Movie {

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("id")
    public int id;

    @SerializedName("video")
    public boolean video;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("title")
    public String title;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("genre_ids")
    public List<Integer> genreIds = null;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

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
}
