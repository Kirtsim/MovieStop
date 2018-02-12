package fm.kirtsim.kharos.moviestop.cache;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieResult;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.remote.MovieServiceProvider;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kharos on 05/02/2018
 */

public class MainCacheVM extends ViewModel implements MoviesCache {
    private static final String TAG = MainCacheVM.class.getSimpleName();

    private String apiKey = "";

    private List<MovieItem> topRatedMovies;
    private List<MovieItem> popularMovies;
    private List<MovieItem> upcomingMovies;
    private List<MovieItem> featuredMovies;

    @Override
    public Single<List<MovieItem>> getTopRatedMovies(boolean refresh) {
        if (topRatedMovies != null && !refresh)
            return Single.just(topRatedMovies);
        Log.d(TAG, "network request!");
        final MovieListService service = new MovieServiceProvider().getMovieListService();
        return service.listTopRatedMovies(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(r -> resultsToMovieItems(r, movieItems -> topRatedMovies = movieItems));
    }

    @Override
    public Single<List<MovieItem>> getPopularMovies(boolean refresh) {
        if (popularMovies != null && !refresh)
            return Single.just(popularMovies);
        Log.d(TAG, "network request!");
        final MovieListService service = new MovieServiceProvider().getMovieListService();
        return service.listPopularMovies(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(r -> resultsToMovieItems(r, movieItems -> popularMovies = movieItems));
    }

    @Override
    public Single<List<MovieItem>> getUpcomingMovies(boolean refresh) {
        if (upcomingMovies != null && !refresh)
            return Single.just(upcomingMovies);
        final MovieListService service = new MovieServiceProvider().getMovieListService();
        return service.listUpcomingMovies(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(r -> resultsToMovieItems(r, movieItems -> upcomingMovies = movieItems));
    }

    @Override
    public Single<List<MovieItem>> getFeaturedMovies(boolean refresh) {
        if (featuredMovies == null || refresh) {
            final MovieListService service = new MovieServiceProvider().getMovieListService();
            return service.listFeaturedMovies(apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(r -> resultsToMovieItems(r, movieItems -> featuredMovies = movieItems));
        }
        return Single.just(featuredMovies);
    }

    private List<MovieItem> resultsToMovieItems(MovieResponse response, MovieItemAssigner assigner) {
        final List<MovieResult> results = response.getResults();
        ArrayList<MovieItem> movies = new ArrayList<>(0);
        if (results != null) {
            movies.ensureCapacity(results.size());
            for (MovieResult result : results) {
                final MovieItem movie = new MovieItem();
                movie.setBackdropPosterURL(result.getBackdropPath());
                movies.add(movie);
            }
        }
        assigner.cacheMovieItems(movies);
        return movies;
    }

    public void setApiKey(String key) {
        this.apiKey = key;
    }


    /**
     * Interface MovieItemAssigner
     *
* - functional interface used for assigning fetched and translated movies to their
     * corresponding attribute in the MainCacheVM class
     */

    private interface MovieItemAssigner {

        void cacheMovieItems(List<MovieItem> movieItems);

    }
}
