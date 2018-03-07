package fm.kirtsim.kharos.moviestop.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by kharos on 07/03/2018
 */

@Entity(primaryKeys = {"movie_id", "status"})
public class MovieStatus {

    public static final String STATUS_FEATURED = "F";
    public static final String STATUS_UPCOMING = "U";
    public static final String STATUS_POPULAR = "P";
    public static final String STATUS_TOP_RATED = "T";


    @ForeignKey(entity=Movie.class,
                parentColumns="id",
                childColumns="movie_id",
                onDelete=CASCADE)
    @ColumnInfo(name = "movie_id")
    private int movieId;

    private String status;

    public MovieStatus(int movieId, String status) {
        this.movieId = movieId;
        this.status = status;
    }

    private Integer getMovieId() {
        return movieId;
    }

    public String getStatus() {
        return status;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
