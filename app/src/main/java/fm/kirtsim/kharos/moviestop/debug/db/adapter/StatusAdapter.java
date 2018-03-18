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
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;

/**
 * Created by kharos on 15/03/2018
 */

public final class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    private final List<MovieStatus> statuses = new ArrayList<>();

    @Override
    public StatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.debug_status_item, parent, false);
        return new StatusHolder(root);
    }

    @Override
    public void onBindViewHolder(StatusHolder holder, int position) {
        holder.assignMovieStatus(statuses.get(position));
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void newStatuses(List<MovieStatus> newStatuses) {
        statuses.clear();
        if (newStatuses != null)
            statuses.addAll(newStatuses);
    }

    static class StatusHolder extends RecyclerView.ViewHolder {

        private TextView movieId;
        private TextView movieStatus;

        StatusHolder(View itemView) {
            super(itemView);
            movieId = itemView.findViewById(R.id.movie_id);
            movieStatus = itemView.findViewById(R.id.movie_status);
        }

        void assignMovieStatus(MovieStatus status) {
            movieId.setText(String.valueOf(status.getMovieId()));
            movieStatus.setText(status.getStatus());
        }
    }
}
