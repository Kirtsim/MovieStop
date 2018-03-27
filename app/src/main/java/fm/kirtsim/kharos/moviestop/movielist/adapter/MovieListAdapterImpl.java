package fm.kirtsim.kharos.moviestop.movielist.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.movielist.adapter.viewHolder.AMovieItemVH;
import fm.kirtsim.kharos.moviestop.movielist.adapter.viewHolder.MovieItemVH;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 23/03/2018
 */

public final class MovieListAdapterImpl extends MovieListAdapter {

    @Override
    protected List<Movie> initializeMovieList() {
        return new ArrayList<>(10);
    }

    @Override
    protected int movieItemLayout() {
        return R.layout.movie_list_item;
    }

    @Override
    public void newMovies(List<Movie> newMovies) {
        movies.clear();
        if (newMovies != null)
            movies.addAll(newMovies);
    }

    @Override
    protected AMovieItemVH createViewHolder(View view) {
        return new MovieItemVH(view);
    }
}
