package fm.kirtsim.kharos.moviestop.cache;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import io.reactivex.Single;

/**
 * Created by kharos on 05/02/2018
 */

public interface MoviesCache {

    Single<List<MovieItem>> getTopRatedMovies(boolean refresh);
    Single<List<MovieItem>> getPopularMovies(boolean refresh);
    Single<List<MovieItem>> getUpcomingMovies(boolean refresh);
    Single<List<MovieItem>> getFeaturedMovies(boolean refresh);
}
