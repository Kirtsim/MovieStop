package fm.kirtsim.kharos.moviestop.di;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fm.kirtsim.kharos.moviestop.cache.MainCacheVM;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kharos on 17/02/2018
 */

@Module
public final class TMDBApiModule {

    private String baseUrl;

    public TMDBApiModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public MovieListService provideMovieListService(Retrofit retrofit) {
        return retrofit.create(MovieListService.class);
    }

}
