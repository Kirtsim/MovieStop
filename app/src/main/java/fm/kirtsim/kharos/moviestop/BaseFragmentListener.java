package fm.kirtsim.kharos.moviestop;

import android.os.Bundle;

import fm.kirtsim.kharos.moviestop.cache.MovieCacheProvider;

/**
 * Created by kharos on 03/02/2018
 */

public interface BaseFragmentListener {

    MovieCacheProvider getCachedMoviesProvider();

    void startFragment(int fragmentID, Bundle args, boolean addToBackStack);

    void popLastFragment();

}
