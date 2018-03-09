package fm.kirtsim.kharos.moviestop.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;

/**
 * Created by kharos on 07/03/2018
 */
@Database(entities = {Movie.class, MovieStatus.class},
        version = 1)
public abstract class MovieDB extends RoomDatabase {

    public abstract MovieDao getMovieDao();

    public abstract MovieStatusDao getMovieStatusDao();
}
