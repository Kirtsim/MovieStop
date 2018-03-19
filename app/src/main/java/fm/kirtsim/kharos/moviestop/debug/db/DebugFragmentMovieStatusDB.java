package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.debug.db.adapter.StatusAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kharos on 18/03/2018
 */

public final class DebugFragmentMovieStatusDB extends DebugFragment {
    private StatusAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StatusAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        getStatusRequestFromArgs(getArguments())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::newStatuses, e -> Log.e("DEBUG", "err: ", e));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.debug_fragment_movie;
    }

    @Override
    protected void onCreateView(View root) {
        super.onCreateView(root);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
