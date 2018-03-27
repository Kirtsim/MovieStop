package fm.kirtsim.kharos.moviestop.movielist.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 24/03/2018
 */

public final class MovieItemVH extends AMovieItemVH {

    private TextView movieTitle;

    public MovieItemVH(View itemView) {
        super(itemView);
        movieTitle = itemView.findViewById(R.id.movie_title);
    }

    @Override
    public void assignMovie(Movie movie) {
        movieTitle.setText(movie.getTitle());
    }
}
