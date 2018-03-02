package fm.kirtsim.kharos.moviestop.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
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

    private List<MovieItem> topRatedMovies;
    private List<MovieItem> popularMovies;
    private List<MovieItem> upcomingMovies;
    private List<MovieItem> featuredMovies;

    public MainCache(MovieListService movieService, String apiKey,
                     SchedulerProvider subscriptionSchedulerProvider) {
        this.apiKey = apiKey;
        this.service = movieService;
        this.subscriptionSchedulerProvider = subscriptionSchedulerProvider;
    }

    @Override
    public void setFeaturedMovies(List<MovieItem> movies) {
        featuredMovies = movies;
    }

    @Override
    public void setTopRatedMovies(List<MovieItem> movies) {
        topRatedMovies = movies;
    }

    @Override
    public void setPopularMovies(List<MovieItem> movies) {
        popularMovies = movies;
    }

    @Override
    public void setUpcomingMovies(List<MovieItem> movies) {
        upcomingMovies = movies;
    }

    @Override
    public Single<List<MovieItem>> getTopRatedMovies(boolean refresh) {
        return createRequest(() -> topRatedMovies,
                (movies) -> topRatedMovies = movies,
                () -> service.listTopRatedMovies(apiKey), refresh);
    }

    @Override
    public Single<List<MovieItem>> getPopularMovies(boolean refresh) {
        return createRequest(() -> popularMovies,
                (movies) -> popularMovies = movies,
                () -> service.listPopularMovies(apiKey), refresh);
    }

    @Override
    public Single<List<MovieItem>> getUpcomingMovies(boolean refresh) {
        return createRequest(() -> upcomingMovies,
                (movies) -> upcomingMovies = movies,
                () -> service.listUpcomingMovies(apiKey), refresh);
    }

    @Override
    public Single<List<MovieItem>> getFeaturedMovies(boolean refresh) {
        return createRequest(() -> featuredMovies,
                (movies) -> featuredMovies = movies,
                () -> service.listFeaturedMovies(apiKey), refresh);
    }

    private List<MovieItem> findInDatabase() {
        // TODO: implement Room database calls
        return null;
    }

    private Single<List<MovieItem>> createRequest(MoviesGetter moviesGetter,
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
                .map(response -> responseToMovieItems(response, moviesAssigner));
    }

    private List<MovieItem> responseToMovieItems(MovieResponse response, MoviesAssigner assigner) {
        final List<Movie> results = response.results;
        final List<MovieItem> movies = createTranslatedMovieItems(results);
        assigner.assign(movies);
        return movies;
    }

    private List<MovieItem> createTranslatedMovieItems(List<Movie> results) {
        if (results == null)
            return Collections.emptyList();

        final List<MovieItem> items = new ArrayList<>(results.size());
        for (Movie result : results) {
            final MovieItem movie = new MovieItem();
            movie.setTitle(result.title);
            movie.setBackdropPosterURL(result.backdropPath);
            items.add(movie);
        }
        return items;
    }



     /**
     * Interface MoviesAssigner
     *
     * - functional interface used for assigning fetched and translated movies to their
     * corresponding attribute in the MainCache class
     */

    private interface MoviesAssigner {

        void assign(List<MovieItem> movieItems);

    }

    private interface ServiceRequester {

        Single<MovieResponse> request();

    }

    private interface MoviesGetter {

        List<MovieItem> get();

    }
}
