package fm.kirtsim.kharos.moviestop.repository;

import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import io.reactivex.Single;

/**
 * Created by kharos on 05/03/2018
 */

final class MoviesLoader {

    private MoviesAssigner moviesAssigner;
    private ServiceRequester serviceRequester;
    private MoviesGetter moviesGetter;
    private MoviesDBCaller dbCall;
    private boolean forcedRefresh;

    MoviesLoader moviesAssigner(MoviesAssigner moviesAssigner) {
        this.moviesAssigner = moviesAssigner;
        return this;
    }

    MoviesLoader serviceRequester(ServiceRequester serviceRequester) {
        this.serviceRequester = serviceRequester;
        return this;
    }

    MoviesLoader moviesGetter(MoviesGetter moviesGetter) {
        this.moviesGetter = moviesGetter;
        return this;
    }

    MoviesLoader dbCall(MoviesDBCaller dbCall) {
        this.dbCall = dbCall;
        return this;
    }

    MoviesLoader forceRefresh(boolean forcedRefresh) {
        this.forcedRefresh = forcedRefresh;
        return this;
    }

    Single<List<Movie>> loadMovies() {
//        if (moviesGetter.get() == null && !forcedRefresh)
//            dbCall.dbCall().subscribe(moviesAssigner::assign, )
//            moviesAssigner.assign(findInDatabase());
//        if (!refresh && moviesGetter.get() != null) {
//            return Single.just(moviesGetter.get());
//        }
//
//        return serviceRequester.request()
//                .subscribeOn(subscriptionSchedulerProvider.newScheduler())
//                .map(response -> {
//                    moviesAssigner.assign(response.getResults());
//                    return response.getResults();
//                });
        return Single.just(Collections.emptyList());
    }


    /**
     * Interface MoviesAssigner
     *
     * - functional interface used for assigning fetched and translated movies to their
     * corresponding attribute in the MainCache class
     */

    interface MoviesAssigner {

        void assign(List<Movie> movieItems);

    }

    interface ServiceRequester {

        Single<MovieResponse> request();

    }

    interface MoviesGetter {

        List<Movie> get();

    }

    interface MoviesDBCaller {
        Single<List<Movie>> dbCall();
    }
}
