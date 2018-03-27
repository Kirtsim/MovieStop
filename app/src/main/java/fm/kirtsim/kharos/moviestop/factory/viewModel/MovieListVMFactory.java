package fm.kirtsim.kharos.moviestop.factory.viewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import fm.kirtsim.kharos.moviestop.movielist.adapter.MovieListAdapter;
import fm.kirtsim.kharos.moviestop.movielist.viewmodel.MovieListVM;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;

/**
 * Created by kharos on 27/03/2018
 */

public final class MovieListVMFactory implements ViewModelProvider.Factory {

    private MovieRepository repository;
    private MovieListAdapter adapter;

    public MovieListVMFactory(MovieRepository repository, MovieListAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieListVM.class)) {
            return (T) new MovieListVM(repository, adapter);
        }
        throw new IllegalArgumentException("cannot create ViewModel of class: " + modelClass.getSimpleName());
    }
}
