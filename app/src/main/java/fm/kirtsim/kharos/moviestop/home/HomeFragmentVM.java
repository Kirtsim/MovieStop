package fm.kirtsim.kharos.moviestop.home;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragmentVM extends ViewModel {

    private static final String TAG = ViewModel.class.getSimpleName();
    private final String posterBaseUrl;
    private final MovieRepository repository;
    private final ConnectivityManager connectivityManager;

    public final ObservableField<String> backDropPosterFeaturedUrl;
    public final ObservableField<String> backDropPosterPopularUrl;
    public final ObservableField<String> backDropPosterTopRatedUrl;
    public final ObservableField<String> backDropPosterUpcomingUrl;

    public HomeFragmentVM(MovieRepository repository, ConnectivityManager connectivityManager,
                          String posterBaseUrl) {
        this.repository = repository;
        this.posterBaseUrl = posterBaseUrl;
        this.connectivityManager = connectivityManager;
        backDropPosterFeaturedUrl = new ObservableField<>();
        backDropPosterPopularUrl = new ObservableField<>();
        backDropPosterTopRatedUrl = new ObservableField<>();
        backDropPosterUpcomingUrl = new ObservableField<>();
    }

    void fetchAllPosterImages(boolean refresh) {
        if (refresh)
            refresh = isThereInternetConnection();
        performRequest(repository.getFeaturedMovies(refresh), backDropPosterFeaturedUrl);
        performRequest(repository.getPopularMovies(refresh), backDropPosterPopularUrl);
        performRequest(repository.getTopRatedMovies(refresh), backDropPosterTopRatedUrl);
        performRequest(repository.getUpcomingMovies(refresh), backDropPosterUpcomingUrl);
    }

    private boolean isThereInternetConnection() {
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void performRequest(Single<List<Movie>> movieListSingle,
                                ObservableField<String> urlObservable) {
        movieListSingle
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> urlObservable.set(firstMoviePosterURL(movies)),
                        e -> { Log.e(TAG, "error: ", e); e.printStackTrace(); });
    }

    private String firstMoviePosterURL(List<Movie> movies) {
        if (movies != null && movies.size() > 0) {
            Log.d("DEBUG", movies.get(0).getTitle() + "  " + movies.get(0).getId());
            return posterBaseUrl + movies.get(0).getBackdropPath();
        }
        return "";
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
