package fm.kirtsim.kharos.moviestop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.threading.SchedulerProvider;
import io.reactivex.Completable;
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

    public Completable clearDatabase() {
        return Completable.create(e -> {
            movieStatusDao.deleteAll();
            movieDao.deleteAll();
        }).subscribeOn(schedulerProvider.newScheduler());
    }

    public Single<List<Movie>> getFeaturedMovies(boolean refresh) {
        return getMovies(
                cachedMovies::getFeaturedMovies,
                cachedMovies::setFeaturedMovies,
                MovieStatus.STATUS_FEATURED, refresh
        );
    }

    public Single<List<Movie>> getTopRatedMovies(boolean refresh) {
        return getMovies(
                cachedMovies::getTopRatedMovies,
                cachedMovies::setTopRatedMovies,
                MovieStatus.STATUS_TOP_RATED, refresh
        );
    }

    public Single<List<Movie>> getUpcomingMovies(boolean refresh) {
        return getMovies(
                cachedMovies::getUpcomingMovies,
                cachedMovies::setUpcomingMovies,
                MovieStatus.STATUS_UPCOMING, refresh
        );
    }

    public Single<List<Movie>> getPopularMovies(boolean refresh) {
        return getMovies(
                cachedMovies::getPopularMovies,
                cachedMovies::setPopularMovies,
                MovieStatus.STATUS_POPULAR, refresh
        );
    }


    private Single<List<Movie>> getMovies(CachedMoviesGetter cachedMoviesGetter,
                                          AssignerToCache cacheAssigner,
                                          String movieStatus, boolean refresh) {
        if (refresh)
            return requestMovies(cacheAssigner, movieStatus);

        if (!cachedMoviesGetter.getCachedMovies().isEmpty())
            return Single.just(cachedMoviesGetter.getCachedMovies());

        return movieDao.selectMovies(movieStatus).subscribeOn(schedulerProvider.newScheduler())
                .doOnSuccess(cacheAssigner::assignMovies);
    }

    private Single<List<Movie>> requestMovies(AssignerToCache assignerToCache, String status) {
        return apiService.listFeaturedMovies(api_key).subscribeOn(schedulerProvider.newScheduler())
               .map(MovieResponse::getResults).doOnSuccess(movies -> {
                    assignerToCache.assignMovies(movies);
                    movieStatusDao.deleteStatusesWithName(status);
                    movieDao.deleteMoviesWithoutStatus();
                    saveMoviesInDatabase(movies, status);
            });
    }

    private void saveMoviesInDatabase(List<Movie> movies, String status) {
        final List<MovieStatus> statuses = new ArrayList<>(movies.size());
        for (Movie movie : movies)
            statuses.add(new MovieStatus(movie.getId(), status));

        movieDao.insertAll(movies);
        movieStatusDao.insert(statuses);
    }


    /**
     * Functional interfaces for convenience
     */

    private interface AssignerToCache {
        void assignMovies(List<Movie> movies);
    }

    private interface CachedMoviesGetter {
        List<Movie> getCachedMovies();
    }

}
