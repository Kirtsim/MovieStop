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
    private final String baseUrl;
    private final MoviesCache cachedMovies;

    public final ObservableField<String> backDropPosterFeaturedUrl;
    public final ObservableField<String> backDropPosterPopularUrl;
    public final ObservableField<String> backDropPosterTopRatedUrl;
    public final ObservableField<String> backDropPosterUpcomingUrl;

    public HomeFragmentVM(MoviesCache cachedMovies, String baseUrl) {
        this.cachedMovies = cachedMovies;
        this.baseUrl = baseUrl;
        backDropPosterFeaturedUrl = new ObservableField<>();
        backDropPosterPopularUrl = new ObservableField<>();
        backDropPosterTopRatedUrl = new ObservableField<>();
        backDropPosterUpcomingUrl = new ObservableField<>();
    }

    public void fetchAllPosterImages(boolean refresh) {
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
            return baseUrl + movies.get(0).getBackdropPosterURL();
        return "";
    }
}
