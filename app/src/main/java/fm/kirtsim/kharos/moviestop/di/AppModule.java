package fm.kirtsim.kharos.moviestop.di;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.db.MovieDB;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;

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

    @Singleton @Provides
    public MovieDB provideMovieDB() {
        return Room.databaseBuilder(application, MovieDB.class, "movie.db").build();
    }

    @Singleton @Provides
    public MovieDao provideMovieDao(MovieDB movieDatabase) {
        return movieDatabase.getMovieDao();
    }

    @Singleton @Provides
    public MovieStatusDao provideMovieStatusDao(MovieDB movieDatabase) {
        return movieDatabase.getMovieStatusDao();
    }

}
