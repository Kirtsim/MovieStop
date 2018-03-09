package fm.kirtsim.kharos.moviestop.di;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fm.kirtsim.kharos.moviestop.cache.MainCache;
import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.db.MovieDB;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kharos on 17/02/2018
 */

@Module
public final class TMDBApiModule {

    private String apiKey;
    private String baseUrl;

    public TMDBApiModule(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    @Provides @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides @Singleton
    public MovieListService provideMovieListService(Retrofit retrofit) {
        return retrofit.create(MovieListService.class);
    }

    @Provides @Singleton
    public MovieCache provideMovieCache() {
        return new MovieCache();
    }

    @Provides @Singleton
    public MovieRepository provideMovieRepository(MovieCache movieCache, MovieListService apiService,
                                                  MovieDao movieDao, MovieStatusDao movieStatusDao) {
        return new MovieRepository(movieCache, apiService, movieDao, movieStatusDao,
                Schedulers::io, apiKey);
    }
}
