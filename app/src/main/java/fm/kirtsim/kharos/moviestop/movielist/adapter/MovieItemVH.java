package fm.kirtsim.kharos.moviestop.movielist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 23/03/2018
 */

final class MovieItemVH extends RecyclerView.ViewHolder {

    private TextView title;

    public MovieItemVH(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_title);
    }

    public void assignMovie(Movie movie) {
        if (movie == null)
            return;
        title.setText(movie.getTitle());
    }
}
