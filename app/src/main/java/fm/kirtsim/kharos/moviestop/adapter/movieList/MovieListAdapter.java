package fm.kirtsim.kharos.moviestop.adapter.movieList;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by kharos on 14/11/2017
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private MovieListCallback moviesCallback;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieViewHolder movieView = null;

        return movieView;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
