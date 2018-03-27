package fm.kirtsim.kharos.moviestop.movielist.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 26/03/2018
 */

public abstract class AMovieItemVH extends RecyclerView.ViewHolder {

    AMovieItemVH(View view) {
        super(view);
    }

    public abstract void assignMovie(Movie movie);
}
