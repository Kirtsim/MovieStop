package fm.kirtsim.kharos.moviestop.repository;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.threading.SchedulerProvider;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static java.util.stream.Collectors.toList;

/**
 * Created by kharos on 04/03/2018
 */

public final class MovieRepository {

    private String api_key;

    private final MovieCache cachedMovies;

    private final MovieListService apiService;

    private final MovieDao movieDao;

    private final MovieStatusDao  movieStatusDao;

    private final SchedulerProvider schedulerProvider;


    public MovieRepository(MovieCache cache, MovieListService apiService,
                           MovieDao movieDao, MovieStatusDao movieStatusDao,
                           SchedulerProvider subsSchedulerProvider, String api_key) {
        this.cachedMovies = cache;
        this.apiService = apiService;
        this.movieDao = movieDao;
        this.movieStatusDao = movieStatusDao;
        this.schedulerProvider = subsSchedulerProvider;
        this.api_key = api_key;
    }

    public Single<List<Movie>> getFeaturedMovies(boolean forceRefresh) {
        if (forceRefresh)
            return apiService.listFeaturedMovies(api_key).subscribeOn(schedulerProvider.newScheduler())
                   .map(MovieResponse::getResults).doOnSuccess(movies -> {
                        cachedMovies.setFeaturedMovies(movies);
                        saveMoviesInDatabase(movies, MovieStatus.STATUS_FEATURED);  // TODO: last line modified here
                    });

        if (!cachedMovies.getFeaturedMovies().isEmpty())
            return Single.just(cachedMovies.getFeaturedMovies());

        return movieDao.selectMovies(MovieStatus.STATUS_FEATURED).subscribeOn(schedulerProvider.newScheduler())
                .doOnSuccess(cachedMovies::setFeaturedMovies);
    }

    private void saveMoviesInDatabase(List<Movie> movies, String status) {
        final List<MovieStatus> statuses = new ArrayList<>(movies.size());
        for (Movie movie : movies)
            statuses.add(new MovieStatus(movie.getId(), status));

        movieDao.insertAll(movies);
        movieStatusDao.insert(statuses);
    }

    private void setStatusToMovies(List<Movie> movies, String status) {
        if (movies == null) return;
        for (Movie movie : movies)
            movie.addStatus(status);
    }

}
