package fm.kirtsim.kharos.moviestop.home;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import io.reactivex.Single;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragmentVM extends ViewModel {

    private MoviesCache cachedMovies;

    public final ObservableField<Drawable> backDropPosterFeatured;
    public final ObservableField<Drawable> backDropPosterPopular;
    public final ObservableField<Drawable> backDropPosterTopRated;
    public final ObservableField<Drawable> backDropPosterUpcoming;

    public HomeFragmentVM() {
        backDropPosterFeatured = new ObservableField<>();
        backDropPosterPopular = new ObservableField<>();
        backDropPosterTopRated = new ObservableField<>();
        backDropPosterUpcoming = new ObservableField<>();
    }

    public void setCachedMovies(MoviesCache cache) {
        this.cachedMovies = cache;
        moviesCacheCheck();
    }

    private void moviesCacheCheck() {
        if (cachedMovies == null)
            throw new IllegalStateException(MoviesCache.class.getSimpleName() + " is null and must be set");
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

    private Single<String> backdropURLofFirst(Single<List<MovieItem>> movies) {
        return movies.flatMap(list -> Single.just(firstMoviePosterURL(list)));
    }

    private String firstMoviePosterURL(List<MovieItem> movies) {
        if (movies.size() > 0)
            return "http://image.tmdb.org/t/p/w300" + movies.get(0).getBackdropPosterURL();
        return "http://image.tmdb.org/t/p/w300/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg";
    }
}
