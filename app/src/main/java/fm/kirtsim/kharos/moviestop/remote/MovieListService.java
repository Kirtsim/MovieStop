package fm.kirtsim.kharos.moviestop.remote;

import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kharos on 09/02/2018
 */



public interface MovieListService {

    @GET("now_playing?language=en&page=1")
    Single<MovieResponse> listFeaturedMovies(@Query("api_key") String apiKey);

    @GET("popular?language=en&page=1")
    Single<MovieResponse> listPopularMovies(@Query("api_key") String apiKey);

    @GET("upcoming?language=en&page=1")
    Single<MovieResponse> listUpcomingMovies(@Query("api_key") String apiKey);

    @GET("top_rated?language=en&page=1")
    Single<MovieResponse> listTopRatedMovies(@Query("api_key") String apiKey);

}
