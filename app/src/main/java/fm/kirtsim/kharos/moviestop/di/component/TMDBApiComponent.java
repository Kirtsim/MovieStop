package fm.kirtsim.kharos.moviestop.di.component;

import javax.inject.Singleton;

import dagger.Component;
import fm.kirtsim.kharos.moviestop.debug.db.DebugFragment;
import fm.kirtsim.kharos.moviestop.di.AppModule;
import fm.kirtsim.kharos.moviestop.di.TMDBApiModule;
import fm.kirtsim.kharos.moviestop.home.HomeFragment;

/**
 * Created by kharos on 17/02/2018
 */

@Singleton
@Component(modules = {AppModule.class, TMDBApiModule.class})
public interface TMDBApiComponent {

    void inject(HomeFragment homeFragment);

    void inject(DebugFragment debugFragment);
}
