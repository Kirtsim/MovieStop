package fm.kirtsim.kharos.moviestop.cache;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import io.reactivex.Single;

/**
 * Created by kharos on 05/02/2018
 */

public interface MoviesCache {

    void setTopRatedMovies(List<Movie> movies);
    void setPopularMovies(List<Movie> movies);
    void setUpcomingMovies(List<Movie> movies);
    void setFeaturedMovies(List<Movie> movies);

    Single<List<Movie>> getTopRatedMovies(boolean refresh);
    Single<List<Movie>> getPopularMovies(boolean refresh);
    Single<List<Movie>> getUpcomingMovies(boolean refresh);
    Single<List<Movie>> getFeaturedMovies(boolean refresh);
}
