package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_REFRESH;
import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_STATUS;

/**
 * Created by kharos on 17/03/2018
 */

public abstract class DebugFragment extends Fragment {

    @Inject
    MovieDao movieDao;

    @Inject
    MovieStatusDao statusDao;

    @Inject
    MovieRepository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResource(), container, false);
        onCreateView(root);
        return root;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected void onCreateView(View root) {}

    TMDBApiComponent getComponent() {
        return ((App) getActivity().getApplication()).getTmdbApiComponent();
    }

    protected Single<List<Movie>> getMovieRequestFromArgs(Bundle args) {
        String status = args.getString(ARG_STATUS);
        final boolean refresh = args.getBoolean(ARG_REFRESH);
        if (!refresh)
            return movieDao.selectMovies(status).subscribeOn(Schedulers.io());
        switch (status) {
            case "F": return repository.getFeaturedMovies(refresh);
            case "U": return repository.getUpcomingMovies(refresh);
            case "T": return repository.getTopRatedMovies(refresh);
            case "P": return repository.getPopularMovies(refresh);
            default: throw new IllegalArgumentException("Invalid status code: "+ status);
        }
    }

    protected Single<List<MovieStatus>> getStatusRequestFromArgs(Bundle args) {
        String status = args.getString(ARG_STATUS);
        return statusDao.selectByStatusReturnSingle(status).subscribeOn(Schedulers.io());
    }
}
