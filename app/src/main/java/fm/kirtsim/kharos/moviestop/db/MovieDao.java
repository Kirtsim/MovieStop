package fm.kirtsim.kharos.moviestop.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import io.reactivex.Single;

/**
 * Created by kharos on 05/03/2018
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie JOIN MovieStatus ON (id = movie_id) WHERE status = :movieStatus")
    Single<List<Movie>> selectMovies(String movieStatus);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);

    @Query("DELETE FROM Movie")
    void deleteAll();

    @Query("DELETE FROM Movie WHERE id IN (SELECT id FROM Movie LEFT JOIN MovieStatus ON (id = movie_id)" +
            "WHERE status IS NULL)")
    void deleteMoviesWithoutStatus();
}
