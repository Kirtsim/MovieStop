package fm.kirtsim.kharos.moviestop.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import io.reactivex.Single;

/**
 * Created by kharos on 05/03/2018
 */

@Dao
public interface MovieDao {


    Single<List<Movie>> selectMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);
}
