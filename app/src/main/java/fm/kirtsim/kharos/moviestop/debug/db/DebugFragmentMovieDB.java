package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.debug.db.adapter.MovieAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kharos on 17/03/2018
 */

public final class DebugFragmentMovieDB extends DebugFragment {

    @Inject
    MovieDao movieDao;

    private MovieAdapter adapter;
    private String status;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
        adapter = new MovieAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.debug_fragment_movie, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        movieDao.selectMovies(status).observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::newMovies, e -> Log.e("DEBUG", "error:", e));
    }


}
