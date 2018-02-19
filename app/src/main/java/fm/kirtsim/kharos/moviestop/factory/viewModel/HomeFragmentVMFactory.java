package fm.kirtsim.kharos.moviestop.factory.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.home.HomeFragmentVM;

/**
 * Created by kharos on 18/02/2018
 */

public class HomeFragmentVMFactory implements ViewModelProvider.Factory {

    private final MoviesCache moviesCache;
    private final String posterBaseUrl;

    public HomeFragmentVMFactory(MoviesCache movieCache, String posterBaseUrl) {
        this.moviesCache = movieCache;
        this.posterBaseUrl = posterBaseUrl;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(HomeFragmentVM.class))
            throw new IllegalArgumentException("Required view model class: " + HomeFragmentVM.class.getSimpleName());

        return (T) new HomeFragmentVM(moviesCache, posterBaseUrl);
    }
}
