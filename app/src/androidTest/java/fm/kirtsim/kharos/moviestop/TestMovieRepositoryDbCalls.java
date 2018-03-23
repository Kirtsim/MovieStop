package fm.kirtsim.kharos.moviestop;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.kirtsim.kharos.moviestop.cache.MovieCache;
import fm.kirtsim.kharos.moviestop.db.MovieDB;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieStatus;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static fm.kirtsim.kharos.moviestop.pojo.MovieStatus.STATUS_FEATURED;
import static fm.kirtsim.kharos.moviestop.pojo.MovieStatus.STATUS_POPULAR;
import static fm.kirtsim.kharos.moviestop.pojo.MovieStatus.STATUS_TOP_RATED;
import static fm.kirtsim.kharos.moviestop.pojo.MovieStatus.STATUS_UPCOMING;
import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;


/**
 * Created by kharos on 09/03/2018
 */

@SuppressWarnings("unchecked")
@RunWith(AndroidJUnit4.class)
public class TestMovieRepositoryDbCalls {
    private final boolean NO_REFRESH = false;
    private final boolean DO_REFRESH = true;

    @Mock
    private MovieListService movieApi;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private MovieDB movieDB;
    private MovieDao movieDao;
    private MovieStatusDao movieStatusDao;

    private TestScheduler scheduler;
    private MovieRepository repo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initDatabase();
        scheduler = new TestScheduler();
        final MovieCache cache = new MovieCache();
        repo = new MovieRepository(cache, movieApi, movieDao, movieStatusDao, () -> scheduler, "123");
    }

    private void initDatabase() {
        Context context = InstrumentationRegistry.getTargetContext();
        movieDB = Room.inMemoryDatabaseBuilder(context, MovieDB.class).build();
        movieDao = movieDB.getMovieDao();
        movieStatusDao = movieDB.getMovieStatusDao();
    }

    @After
    public void closeDb() throws IOException {
        movieDB.close();
    }

    @Test
    public void test_getFeaturedMoviesInDbAssertTrue() {
        testGetMoviesInDbNoRefreshWithStatusAssertTrue(STATUS_FEATURED,
                () -> repo.getFeaturedMovies(NO_REFRESH));
    }

    @Test
    public void test_getPopularMoviesInDbAssertTrue() {
        testGetMoviesInDbNoRefreshWithStatusAssertTrue(STATUS_POPULAR,
                () -> repo.getPopularMovies(NO_REFRESH));
    }

    @Test
    public void test_getUpcomingMoviesInDbAssertTrue() {
        testGetMoviesInDbNoRefreshWithStatusAssertTrue(STATUS_UPCOMING, 
                () -> repo.getUpcomingMovies(NO_REFRESH));
    }

    @Test
    public void test_getTopRatedMoviesInDbAssertTrue() {
        testGetMoviesInDbNoRefreshWithStatusAssertTrue(STATUS_TOP_RATED,
                () -> repo.getTopRatedMovies(NO_REFRESH));
    }
    
    private void testGetMoviesInDbNoRefreshWithStatusAssertTrue(String status, RepoCall call) {
        final List<Movie> expected = movieList(1, "T1", "T2");
        final List<MovieStatus> statuses = statusList(expected, status);

        movieDao.insertAll(expected);
        movieStatusDao.insert(statuses);

        performTest(call).assertNoErrors().assertValue(expected);
    }

    @Test
    public void test_insertMoviesWithMoreStatusesAssertTrue() {
        final List<Movie> featured = movieList(1, "T1", "TS");
        final List<Movie> popular = movieList(2, "TS", "T3");

        Mockito.when(movieApi.listFeaturedMovies(anyString()))
                .thenReturn(Single.just(mockResponse(featured)));
        Mockito.when(movieApi.listPopularMovies(anyString()))
                .thenReturn(Single.just(mockResponse(popular)));

        repo.getFeaturedMovies(DO_REFRESH).subscribeOn(scheduler).subscribe();
        repo.getPopularMovies(DO_REFRESH).subscribeOn(scheduler).subscribe();
        scheduler.triggerActions();

        performTest(() -> movieDao.selectMovies(STATUS_FEATURED)).assertNoErrors()
                .assertValues(featured);
        performTest(() -> movieDao.selectMovies(STATUS_POPULAR)).assertNoErrors()
                .assertValues(popular);
        performTest(() -> movieDao.selectAll()).assertNoErrors().assertValue(list -> list.size() == 3);
        assertEquals("number of statuses", 4, movieStatusDao.selectAll().size());
    }

    @Test
    public void test_queryMoviesWithMoreStatusesAssertTrue() {
        final List<Movie> upcoming = movieList(1, "T1", "TS");
        final List<Movie> popular = movieList(2, "TS", "T3");
        final List<MovieStatus> statuses = statusList(upcoming, STATUS_UPCOMING);
        statuses.addAll(statusList(popular, STATUS_POPULAR));

        movieDao.insertAll(upcoming);
        movieDao.insertAll(popular);
        movieStatusDao.insert(statuses);

        performTest(() -> movieDao.selectMovies(STATUS_UPCOMING)).assertNoErrors()
                .assertValues(upcoming);
        performTest(() -> movieDao.selectMovies(STATUS_POPULAR)).assertNoErrors()
                .assertValues(popular);
        performTest(() -> movieDao.selectAll()).assertNoErrors().assertValue(ret -> ret.size() == 3);
        assertEquals("number of statuses", 4, movieStatusDao.selectAll().size());
    }

    private static List<MovieStatus> statusList(List<Movie> movies, String statusCode) {
        if (movies == null)
            movies = Collections.emptyList();
        final List<MovieStatus> statuses = new ArrayList<>(movies.size());
        for (Movie movie : movies)
            statuses.add(new MovieStatus(movie.getId(), statusCode));
        return statuses;
    }

    private static List<Movie> movieList(int startId, String... titles) {
        if (titles == null)
            titles = new String[0];
        int id = startId;
        List<Movie> list = new ArrayList<>(titles.length);
        for (String title : titles) {
            final Movie movie = new Movie();
            movie.setId(id++);
            movie.setTitle(title);
            list.add(movie);
        }
        return list;
    }
    
    private TestObserver<List<Movie>> performTest(RepoCall call) {
        TestObserver<List<Movie>> result = call.call().test();
        scheduler.triggerActions();
        result.awaitTerminalEvent();
        return result;
    }

    private MovieResponse mockResponse(List<Movie> movies) {
        final MovieResponse response = new MovieResponse();
        response.setResults(movies);
        return response;
    }

    private interface RepoCall {
        Single<List<Movie>> call();
    }
}
