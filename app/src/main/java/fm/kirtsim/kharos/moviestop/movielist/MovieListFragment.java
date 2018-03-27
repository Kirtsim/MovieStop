package fm.kirtsim.kharos.moviestop.movielist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.factory.viewModel.MovieListVMFactory;
import fm.kirtsim.kharos.moviestop.movielist.adapter.MovieListAdapterImpl;
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
        getTMDBApiComponent().inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders
                .of(this, new MovieListVMFactory(repository, new MovieListAdapterImpl()))
                .get(MovieListVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_movie_list, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(viewModel.getAdapter());

        return root;
    }
}
