package fm.kirtsim.kharos.moviestop.cache;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import fm.kirtsim.kharos.moviestop.remote.MovieListService;

/**
 * Created by kharos on 17/02/2018
 */

public final class MainCacheVMFactory implements ViewModelProvider.Factory {

    private String apiKey;
    private MovieListService service;

    public MainCacheVMFactory(String apiKey, MovieListService service) {
        this.apiKey = apiKey;
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(MainCacheVM.class))
            throw new IllegalArgumentException(modelClass.getSimpleName() + " is not " + MainCacheVM.class.getSimpleName());
        return (T) new MainCacheVM(apiKey, service);
    }
}
