package fm.kirtsim.kharos.moviestop.movielist.viewmodel;

import android.arch.lifecycle.ViewModel;

import fm.kirtsim.kharos.moviestop.movielist.adapter.MovieListAdapter;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;

/**
 * Created by kharos on 24/03/2018
 */

public final class MovieListVM extends ViewModel {


    private final MovieRepository movieRepo;

    private final MovieListAdapter adapter;

    public MovieListVM(MovieRepository repo, MovieListAdapter adapter) {
        this.movieRepo = repo;
        this.adapter = adapter;
    }

    public MovieListAdapter getAdapter() {
        return adapter;
    }
}
