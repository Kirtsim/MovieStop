package fm.kirtsim.kharos.moviestop.di.component;

import dagger.Component;
import fm.kirtsim.kharos.moviestop.MainActivity;
import fm.kirtsim.kharos.moviestop.di.AppModule;
import fm.kirtsim.kharos.moviestop.di.TMDBApiModule;

/**
 * Created by kharos on 17/02/2018
 */

@Component(modules = {AppModule.class, TMDBApiModule.class})
public interface TMDBApiComponent {
    void inject(MainActivity mainActivity);
}
