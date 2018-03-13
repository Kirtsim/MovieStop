package fm.kirtsim.kharos.moviestop;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyString;


/**
 * Created by kharos on 09/03/2018
 */

@RunWith(MockitoJUnitRunner.class)
public final class TestMovieRepository {

    @Mock
    private MovieCache cache;

    @Mock
    private MovieListService service;

    @Mock
    private MovieDao mockMovieDao;

    @Mock
    private MovieStatusDao mockMovieStatusDao;

    private TestScheduler scheduler;
    private MovieRepository repo;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scheduler = new TestScheduler();
        repo = new MovieRepository(cache, service, mockMovieDao, mockMovieStatusDao, () -> scheduler, "123");
    }

    @Test
    public void test_getFeaturedMoviesWithRefresh() {
        final boolean refresh = true;
        final List<Movie> expected = movieList("T1", "T2", "T3");

        Mockito.when(service.listFeaturedMovies(anyString())).thenReturn(Single.just(mockResponse(expected)));

        TestObserver<List<Movie>> result = repo.getFeaturedMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getUpcomingMoviesWithRefresh() {
        final boolean refresh = true;
        final List<Movie> expected = movieList("T1", "T2", "T3");

        Mockito.when(service.listUpcomingMovies(anyString())).thenReturn(Single.just(mockResponse(expected)));

        TestObserver<List<Movie>> result = repo.getUpcomingMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getTopRatedMoviesWithRefresh() {
        final boolean refresh = true;
        final List<Movie> expected = movieList("T1", "T2", "T3");

        Mockito.when(service.listTopRatedMovies(anyString())).thenReturn(Single.just(mockResponse(expected)));

        TestObserver<List<Movie>> result = repo.getTopRatedMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getPopularMoviesWithRefresh() {
        final boolean refresh = true;
        final List<Movie> expected = movieList("T1", "T2", "T3");

        Mockito.when(service.listPopularMovies(anyString())).thenReturn(Single.just(mockResponse(expected)));

        TestObserver<List<Movie>> result = repo.getPopularMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getFeaturedMoviesCachedNoDBWithoutRefresh() {
        final boolean refresh = false;
        final List<Movie> expected = movieList("T1", "T2");

        Mockito.when(cache.getFeaturedMovies()).thenReturn(expected);

        TestObserver<List<Movie>> result = repo.getFeaturedMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getUpcomingMoviesCachedNoDBWithoutRefresh() {
        final boolean refresh = false;
        final List<Movie> expected = movieList("T1", "T2");

        Mockito.when(cache.getUpcomingMovies()).thenReturn(expected);

        TestObserver<List<Movie>> result = repo.getUpcomingMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getPopularMoviesCachedNoDBWithoutRefresh() {
        final boolean refresh = false;
        final List<Movie> expected = movieList("T1", "T2");

        Mockito.when(cache.getPopularMovies()).thenReturn(expected);

        TestObserver<List<Movie>> result = repo.getPopularMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    @Test
    public void test_getTopRatedMoviesCachedNoDBWithoutRefresh() {
        final boolean refresh = false;
        final List<Movie> expected = movieList("T1", "T2");

        Mockito.when(cache.getTopRatedMovies()).thenReturn(expected);

        TestObserver<List<Movie>> result = repo.getTopRatedMovies(refresh).test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        result.assertNoErrors().assertValues(expected);
    }

    private static List<Movie> movieList(String... titles) {
        if (titles == null)
            titles = new String[0];
        List<Movie> list = new ArrayList<>(titles.length);
        for (String title : titles) {
            final Movie movie = new Movie();
            movie.setTitle(title);
            list.add(movie);
        }
        return list;
    }

    private MovieResponse mockResponse(List<Movie> movies) {
        final MovieResponse response = new MovieResponse();
        response.setResults(movies);
        return response;
    }
}
