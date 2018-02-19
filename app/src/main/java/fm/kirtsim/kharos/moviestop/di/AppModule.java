package fm.kirtsim.kharos.moviestop.di;

import dagger.Module;
import dagger.Provides;
import fm.kirtsim.kharos.moviestop.App;

/**
 * Created by kharos on 17/02/2018
 */

@Module
public final class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    public App providedApp() {
       return application;
    }

}
