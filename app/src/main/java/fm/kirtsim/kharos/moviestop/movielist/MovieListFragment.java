package fm.kirtsim.kharos.moviestop.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.movielist.viewmodel.MovieListVM;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;

/**
 * Created by kharos on 03/02/2018
 */

public class MovieListFragment extends MovieBaseFragment {

    @Inject
    MovieRepository repository;

    private MovieListVM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
