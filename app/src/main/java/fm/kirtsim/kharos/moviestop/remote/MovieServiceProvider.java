package fm.kirtsim.kharos.moviestop.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kharos on 09/02/2018
 */

public class MovieServiceProvider {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static MovieListService service;

    public MovieListService getMovieListService() {
        if (service == null)
            service = createService();
        return service;
    }

    private MovieListService createService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(createClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MovieListService.class);
    }

    private OkHttpClient createClient() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

}
