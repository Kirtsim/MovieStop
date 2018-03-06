package fm.kirtsim.kharos.moviestop.repository;

import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.threading.SchedulerProvider;
import io.reactivex.Single;

/**
 * Created by kharos on 04/03/2018
 */

public final class MovieRepository {

    private String api_key;

    private final MovieCache cachedMovies;

    private final MovieListService apiService;

    private final MovieDao movieDao;

    private final SchedulerProvider schedulerProvider;


    public MovieRepository(MovieCache cache, MovieListService apiService, MovieDao movieDao,
                           SchedulerProvider subsSchedulerProvider, String api_key) {
        this.cachedMovies = cache;
        this.apiService = apiService;
        this.movieDao = movieDao;
        this.schedulerProvider = subsSchedulerProvider;
        this.api_key = api_key;
    }

    public Single<List<Movie>> getFeaturedMovies(boolean forceRefresh) {
        if (forceRefresh)
            return apiService.listFeaturedMovies(api_key).subscribeOn(schedulerProvider.newScheduler())
                    .map(MovieResponse::getResults).doOnSuccess(list -> {
                        cachedMovies.setFeaturedMovies(list);
                        //movieDb.saveFeaturedMovies(list);
                    });

        if (!cachedMovies.getFeaturedMovies().isEmpty())
            return Single.just(cachedMovies.getFeaturedMovies());

        return movieDao.loadFeaturedMovies().subscribeOn(schedulerProvider.newScheduler())
                .doOnSuccess(cachedMovies::setFeaturedMovies);
    }

}
