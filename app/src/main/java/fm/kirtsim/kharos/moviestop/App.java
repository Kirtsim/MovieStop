package fm.kirtsim.kharos.moviestop;

import android.app.Application;

import fm.kirtsim.kharos.moviestop.di.AppModule;
import fm.kirtsim.kharos.moviestop.di.TMDBApiModule;
import fm.kirtsim.kharos.moviestop.di.component.DaggerTMDBApiComponent;
import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;

/**
 * Created by kharos on 17/02/2018
 */

public final class App extends Application {

    private TMDBApiComponent tmdbApiComponent;

    public TMDBApiComponent getTmdbApiComponent() {
        if (tmdbApiComponent == null)
            tmdbApiComponent = createTMDBApiComponent();
        return tmdbApiComponent;
    }

    private TMDBApiComponent createTMDBApiComponent() {
        return DaggerTMDBApiComponent.builder()
                .appModule(new AppModule(this))
                .tMDBApiModule(new TMDBApiModule(getApiKey(), getMovieBaseUrl()))
                .build();
    }

    private String getMovieBaseUrl() {
        return getString(R.string.MOVIE_LIST_BASE_URL);
    }

    private String getApiKey() {
        return getString(R.string.api_key);
    }

}
