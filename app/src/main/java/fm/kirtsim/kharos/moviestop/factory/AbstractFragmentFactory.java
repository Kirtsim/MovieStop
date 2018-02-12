package fm.kirtsim.kharos.moviestop.factory;

import android.os.Bundle;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;

/**
 * Created by kharos on 03/02/2018
 */

public interface AbstractFragmentFactory {

    MovieBaseFragment create(Bundle args);
}
