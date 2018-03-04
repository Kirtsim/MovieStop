package fm.kirtsim.kharos.moviestop.cache;

import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.threading.SchedulerProvider;
import io.reactivex.Single;

/**
 * Created by kharos on 19/02/2018
 */

public final class MainCache implements MoviesCache {
    private final String apiKey;

    private final MovieListService service;

    private final SchedulerProvider subscriptionSchedulerProvider;

    private List<Movie> topRatedMovies;
    private List<Movie> popularMovies;
    private List<Movie> upcomingMovies;
    private List<Movie> featuredMovies;

    public MainCache(MovieListService movieService, String apiKey,
                     SchedulerProvider subscriptionSchedulerProvider) {
        this.apiKey = apiKey;
        this.service = movieService;
        this.subscriptionSchedulerProvider = subscriptionSchedulerProvider;
    }

    @Override
    public void setFeaturedMovies(List<Movie> movies) {
        featuredMovies = movies;
    }

    @Override
    public void setTopRatedMovies(List<Movie> movies) {
        topRatedMovies = movies;
    }

    @Override
    public void setPopularMovies(List<Movie> movies) {
        popularMovies = movies;
    }

    @Override
    public void setUpcomingMovies(List<Movie> movies) {
        upcomingMovies = movies;
    }

    @Override
    public Single<List<Movie>> getTopRatedMovies(boolean refresh) {
        return createRequest(() -> topRatedMovies,
                (movies) -> topRatedMovies = movies,
                () -> service.listTopRatedMovies(apiKey), refresh);
    }

    @Override
    public Single<List<Movie>> getPopularMovies(boolean refresh) {
        return createRequest(() -> popularMovies,
                (movies) -> popularMovies = movies,
                () -> service.listPopularMovies(apiKey), refresh);
    }

    @Override
    public Single<List<Movie>> getUpcomingMovies(boolean refresh) {
        return createRequest(() -> upcomingMovies,
                (movies) -> upcomingMovies = movies,
                () -> service.listUpcomingMovies(apiKey), refresh);
    }

    @Override
    public Single<List<Movie>> getFeaturedMovies(boolean refresh) {
        return createRequest(() -> featuredMovies,
                (movies) -> featuredMovies = movies,
                () -> service.listFeaturedMovies(apiKey), refresh);
    }

    private List<Movie> findInDatabase() {
        // TODO: implement Room database calls
        return null;
    }

    private Single<List<Movie>> createRequest(MoviesGetter moviesGetter,
                                                  MoviesAssigner moviesAssigner,
                                                  ServiceRequester serviceRequester,
                                                  boolean refresh) {
        if (moviesGetter.get() == null && !refresh)
            moviesAssigner.assign(findInDatabase());
        if (!refresh && moviesGetter.get() != null) {
            return Single.just(moviesGetter.get());
        }

        return serviceRequester.request()
                .subscribeOn(subscriptionSchedulerProvider.newScheduler())
                .map(response -> {
                    moviesAssigner.assign(response.getResults());
                    return response.getResults();
                });
    }

     /**
     * Interface MoviesAssigner
     *
     * - functional interface used for assigning fetched and translated movies to their
     * corresponding attribute in the MainCache class
     */

    private interface MoviesAssigner {

        void assign(List<Movie> movieItems);

    }

    private interface ServiceRequester {

        Single<MovieResponse> request();

    }

    private interface MoviesGetter {

        List<Movie> get();

    }
}
