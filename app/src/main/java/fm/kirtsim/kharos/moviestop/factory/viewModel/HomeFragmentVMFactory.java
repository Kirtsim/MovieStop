package fm.kirtsim.kharos.moviestop.factory.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.home.HomeFragmentVM;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;

/**
 * Created by kharos on 18/02/2018
 */

public class HomeFragmentVMFactory implements ViewModelProvider.Factory {

    private final MovieRepository repository;
    private final String posterBaseUrl;
    private final ConnectivityManager connectivityManager;

    public HomeFragmentVMFactory(MovieRepository repository, ConnectivityManager connectivityManager,
                                 String posterBaseUrl) {
        this.repository = repository;
        this.posterBaseUrl = posterBaseUrl;
        this.connectivityManager = connectivityManager;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(HomeFragmentVM.class))
            throw new IllegalArgumentException("Required view model class: " + HomeFragmentVM.class.getSimpleName());

        return (T) new HomeFragmentVM(repository, connectivityManager, posterBaseUrl);
    }
}
