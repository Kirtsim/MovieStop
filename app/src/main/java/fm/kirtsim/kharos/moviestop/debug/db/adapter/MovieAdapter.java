package fm.kirtsim.kharos.moviestop.debug.db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 15/03/2018
 */

public final class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private final List<Movie> movies = new ArrayList<>();

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.debug_status_item, parent, false);
        return new MovieHolder(root);

    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.assignMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void newMovies(List<Movie> newMovies) {
        movies.clear();
        if (newMovies != null)
            movies.addAll(newMovies);
    }

    static class MovieHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView title;

        MovieHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.movie_id);
            title = itemView.findViewById(R.id.movie_title);
        }

        void assignMovie(Movie movie) {
            if (movie != null) {
                id.setText(String.valueOf(movie.getId()));
                title.setText(movie.getTitle());
            }
        }
    }
}
