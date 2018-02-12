package fm.kirtsim.kharos.moviestop.factory;

import android.os.Bundle;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.home.HomeFragment;

/**
 * Created by kharos on 07/02/2018
 */

public class HomeFragmentFactory implements AbstractFragmentFactory {

    @Override
    public MovieBaseFragment create(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
