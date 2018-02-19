package fm.kirtsim.kharos.moviestop.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieResult;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kharos on 19/02/2018
 */

public final class MainCache implements MoviesCache {
    private final String apiKey;

    private final MovieListService service;

    private List<MovieItem> topRatedMovies;
    private List<MovieItem> popularMovies;
    private List<MovieItem> upcomingMovies;
    private List<MovieItem> featuredMovies;

    public MainCache(MovieListService movieService, String apiKey) {
        this.apiKey = apiKey;
        this.service = movieService;
    }

    @Override
    public Single<List<MovieItem>> getTopRatedMovies(boolean refresh) {
        if (topRatedMovies == null && !refresh)
            topRatedMovies = findInDatabase();
        if (topRatedMovies == null || refresh)
            return service.listTopRatedMovies(apiKey)
                    .subscribeOn(Schedulers.io())
                    .map(r -> resultsToMovieItems(r, mItems -> topRatedMovies = mItems));

        return null;
    }

    @Override
    public Single<List<MovieItem>> getPopularMovies(boolean refresh) {
        return null;
    }

    @Override
    public Single<List<MovieItem>> getUpcomingMovies(boolean refresh) {
        return null;
    }

    @Override
    public Single<List<MovieItem>> getFeaturedMovies(boolean refresh) {
        return null;
    }

    private List<MovieItem> findInDatabase() {
        return null;
    }

    private Single<List<MovieItem>> createRequest(MoviesGetter moviesGetter,
                                                  MoviesAssigner moviesAssigner,
                                                  ServiceRequester serviceRequester,
                                                  boolean refresh) {
        if (moviesGetter.get() == null && !refresh)
            moviesAssigner.assign(findInDatabase());
        if (refresh && moviesGetter.get() != null)
            return Single.just(moviesGetter.get());

        return serviceRequester.request(apiKey)
                .subscribeOn(Schedulers.io())
                .map(r -> resultsToMovieItems(r, moviesAssigner));
    }

    private List<MovieItem> resultsToMovieItems(MovieResponse response, MoviesAssigner assigner) {
        final List<MovieResult> results = response.getResults();
        final List<MovieItem> movies = createTranslatedMovieItems(results);
        assigner.assign(movies);
        return movies;
    }

    private List<MovieItem> createTranslatedMovieItems(List<MovieResult> results) {
        if (results == null)
            return Collections.emptyList();

        final List<MovieItem> items = new ArrayList<>(results.size());
        for (MovieResult result : results) {
            final MovieItem movie = new MovieItem();
            movie.setBackdropPosterURL(result.getBackdropPath());
            items.add(movie);
        }
        return items;
    }



     /**
     * Interface MoviesAssigner
     *
     * - functional interface used for assigning fetched and translated movies to their
     * corresponding attribute in the MainCacheVM class
     */

    private interface MoviesAssigner {

        void assign(List<MovieItem> movieItems);

    }

    private interface ServiceRequester {

        Single<MovieResponse> request(String apiKey);

    }

    private interface MoviesGetter {

        List<MovieItem> get();

    }
}
