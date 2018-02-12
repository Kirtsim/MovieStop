package fm.kirtsim.kharos.moviestop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import fm.kirtsim.kharos.moviestop.cache.MovieCacheProvider;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;

/**
 * Created by kharos on 03/02/2018
 */

public abstract class MovieBaseFragment extends Fragment {

    private BaseFragmentListener listener;
    private MovieCacheProvider cachedMoviesProvider;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof BaseFragmentListener))
            throw new IllegalArgumentException("activity must implement " +
                    BaseFragmentListener.class.getSimpleName());
        listener = (BaseFragmentListener) context;
        cachedMoviesProvider = listener.getCachedMoviesProvider();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        cachedMoviesProvider = null;
    }

    protected void startFragment(int fragmentID, Bundle args, boolean addToBackStack) {
        listener.startFragment(fragmentID, args, addToBackStack);
    }

    protected void finish() {
        listener.popLastFragment();
    }

    protected MoviesCache getCachedMovies() {
        return cachedMoviesProvider.getMoviesCache();
    }

}
