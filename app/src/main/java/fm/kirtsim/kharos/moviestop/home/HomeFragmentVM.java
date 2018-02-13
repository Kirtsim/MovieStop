package fm.kirtsim.kharos.moviestop.home;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragmentVM extends ViewModel {

    private static final String TAG = ViewModel.class.getSimpleName();
    private static final String DEFAULT_URL = "http://image.tmdb.org/t/p/w300/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg";
    private MoviesCache cachedMovies;

    public final ObservableField<String> backDropPosterFeaturedUrl;
    public final ObservableField<String> backDropPosterPopularUrl;
    public final ObservableField<String> backDropPosterTopRatedUrl;
    public final ObservableField<String> backDropPosterUpcomingUrl;

    public HomeFragmentVM() {
        backDropPosterFeaturedUrl = new ObservableField<>(DEFAULT_URL);
        backDropPosterPopularUrl = new ObservableField<>(DEFAULT_URL);
        backDropPosterTopRatedUrl = new ObservableField<>(DEFAULT_URL);
        backDropPosterUpcomingUrl = new ObservableField<>(DEFAULT_URL);
    }

    public void setCachedMovies(MoviesCache cache) {
        this.cachedMovies = cache;
        moviesCacheCheck();
    }

    private void moviesCacheCheck() {
        if (cachedMovies == null)
            throw new IllegalStateException(MoviesCache.class.getSimpleName() + " is null and must be set");
    }

    public void fetchFeaturedPosterImage(boolean refresh) {
        moviesCacheCheck();
        performRequest(cachedMovies.getFeaturedMovies(refresh), backDropPosterFeaturedUrl);
    }

    public void fetchPopularPosterImage(boolean refresh) {
        moviesCacheCheck();
        performRequest(cachedMovies.getPopularMovies(refresh), backDropPosterPopularUrl);
    }

    public void fetchTopRatedPosterImage(boolean refresh) {
        moviesCacheCheck();
        performRequest(cachedMovies.getTopRatedMovies(refresh), backDropPosterTopRatedUrl);
    }

    public void fetchUpcomingPosterImage(boolean refresh) {
        moviesCacheCheck();
        performRequest(cachedMovies.getUpcomingMovies(refresh), backDropPosterUpcomingUrl);
    }

    public void fetchAllPosterImages(boolean refresh) {
        fetchFeaturedPosterImage(refresh);
        fetchPopularPosterImage(refresh);
        fetchTopRatedPosterImage(refresh);
        fetchUpcomingPosterImage(refresh);
    }

    public Single<String> getFeaturedPosterURL(boolean refresh) {
        moviesCacheCheck();
        return backdropURLofFirst(cachedMovies.getFeaturedMovies(refresh));
    }

    public Single<String> getUpcomingPosterURL(boolean refresh) {
        moviesCacheCheck();
        return backdropURLofFirst(cachedMovies.getUpcomingMovies(refresh));
    }

    public Single<String> getTopRatedPosterURL(boolean refresh) {
        moviesCacheCheck();
        return backdropURLofFirst(cachedMovies.getTopRatedMovies(refresh));
    }

    public Single<String> getPopularPosterURL(boolean refresh) {
        moviesCacheCheck();
        return backdropURLofFirst(cachedMovies.getPopularMovies(refresh));
    }

    private void performRequest(Single<List<MovieItem>> movieListSingle,
                                ObservableField<String> urlObservable) {
        movieListSingle.observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> urlObservable.set(firstMoviePosterURL(movies)),
                        e -> Log.e(TAG, "error: ", e));
    }

    private Single<String> backdropURLofFirst(Single<List<MovieItem>> movies) {
        return movies.flatMap(list -> Single.just(firstMoviePosterURL(list)));
    }

    private String firstMoviePosterURL(List<MovieItem> movies) {
        if (movies != null && movies.size() > 0)
            return "http://image.tmdb.org/t/p/w300" + movies.get(0).getBackdropPosterURL();
        return "http://image.tmdb.org/t/p/w300/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg";
    }
}
