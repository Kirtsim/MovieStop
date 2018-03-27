package fm.kirtsim.kharos.moviestop.movielist.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fm.kirtsim.kharos.moviestop.movielist.adapter.viewHolder.AMovieItemVH;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 24/03/2018
 */

public abstract class MovieListAdapter extends RecyclerView.Adapter<AMovieItemVH> {

    protected final List<Movie> movies;

    protected MovieListAdapter() {
        movies = initializeMovieList();
    }

    @Override
    public AMovieItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieItemView = inflater.inflate(movieItemLayout(), parent, false);
        return createViewHolder(movieItemView);
    }

    @Override
    public void onBindViewHolder(AMovieItemVH holder, int position) {
        holder.assignMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    protected abstract List<Movie> initializeMovieList();

    @LayoutRes
    protected abstract int movieItemLayout();

    protected abstract AMovieItemVH createViewHolder(View view);

    public abstract void newMovies(List<Movie> newMovies);
}
