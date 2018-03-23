package fm.kirtsim.kharos.moviestop.movielist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 23/03/2018
 */

public final class MovieListAdapter extends RecyclerView.Adapter<MovieItemVH> {

    private final List<Movie> movies = new ArrayList<>(10);

    @Override
    public MovieItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieItemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieItemVH(movieItemView);
    }

    @Override
    public void onBindViewHolder(MovieItemVH holder, int position) {
        holder.assignMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
