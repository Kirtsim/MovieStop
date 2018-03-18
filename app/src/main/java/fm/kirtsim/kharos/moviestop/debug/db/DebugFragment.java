package fm.kirtsim.kharos.moviestop.debug.db;

import android.app.Activity;
import android.support.v4.app.Fragment;

import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;

/**
 * Created by kharos on 17/03/2018
 */

abstract class DebugFragment extends Fragment {

    TMDBApiComponent getComponent() {
        return ((App) getActivity().getApplication()).getTmdbApiComponent();
    }
}
