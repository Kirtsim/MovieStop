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
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w300";
    private static final String DEFAULT_URL = BASE_URL + "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg";
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

    public void fetchAllPosterImages(boolean refresh) {
        moviesCacheCheck();
        performRequest(cachedMovies.getFeaturedMovies(refresh), backDropPosterFeaturedUrl);
        performRequest(cachedMovies.getPopularMovies(refresh), backDropPosterPopularUrl);
        performRequest(cachedMovies.getTopRatedMovies(refresh), backDropPosterTopRatedUrl);
        performRequest(cachedMovies.getUpcomingMovies(refresh), backDropPosterUpcomingUrl);
    }

    private void performRequest(Single<List<MovieItem>> movieListSingle,
                                ObservableField<String> urlObservable) {
        movieListSingle
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> urlObservable.set(firstMoviePosterURL(movies)),
                        e -> Log.e(TAG, "error: ", e));
    }

    private String firstMoviePosterURL(List<MovieItem> movies) {
        if (movies != null && movies.size() > 0)
            return BASE_URL + movies.get(0).getBackdropPosterURL();
        return DEFAULT_URL;
    }
}
