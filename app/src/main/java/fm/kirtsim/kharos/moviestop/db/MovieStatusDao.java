package fm.kirtsim.kharos.moviestop.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;

/**
 * Created by kharos on 07/03/2018
 */
@Dao
public interface MovieStatusDao {

    @Query("SELECT * FROM MovieStatus WHERE status LIKE :status")
    List<MovieStatus> selectByStatus(String status);

    @Query("SELECT * FROM MovieStatus WHERE movie_id LIKE :movieId")
    List<MovieStatus> selectByMovieId(int movieId);

    @Insert
    void insert(List<MovieStatus> statuses);

    @Delete
    void delete(List<MovieStatus> statuses);


    @Query("DELETE FROM MovieStatus WHERE status LIKE :status")
    void deleteStatusesWithName(String status);

    @Query("DELETE FROM MovieStatus")
    void deleteAll();
}
