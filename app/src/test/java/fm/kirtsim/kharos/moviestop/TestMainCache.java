package fm.kirtsim.kharos.moviestop;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fm.kirtsim.kharos.moviestop.cache.MainCache;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.Movie;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by kharos on 21/02/2018
 */

@RunWith(MockitoJUnitRunner.class)
public final class TestMainCache {

    @Mock
    MovieListService mockMovieService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private static final List<TwoItems
            <BiFunction<MoviesCache, Boolean, Single<List<MovieItem>>>, String>> cacheCalls =
            new ArrayList<>(4);

    private TestScheduler scheduler;
    private MoviesCache cache;

    @BeforeClass
    public static void beforeAll() {
        cacheCalls.add(new TwoItems<>(MoviesCache::getUpcomingMovies, "getUpcomingMovies"));
        cacheCalls.add(new TwoItems<>(MoviesCache::getFeaturedMovies, "getFeaturedMovies"));
        cacheCalls.add(new TwoItems<>(MoviesCache::getTopRatedMovies, "getTopRatedMovies"));
        cacheCalls.add(new TwoItems<>(MoviesCache::getPopularMovies, "getPopularMovies"));
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scheduler = new TestScheduler();
        cache = new MainCache(mockMovieService, "1234", () -> scheduler);
    }

    @Test
    public void test_getMovieListNotCachedNoRefresh() {
        final String[] TITLES = new String[] { "Title1", "Title2", "Title3" };
        final MovieResponse mockResponse = createMockResponseFromTitles(TITLES);
        final List<MovieItem> expected = createMovieItemListFromTitles(TITLES);

        mockCacheCallsWithResponse(mockResponse);

        testMovieCacheRequests(false, expected);
    }

    @Test
    public void test_getMovieListCachedNoRefresh() {
        final String[] TITLES = new String[]{"Title1", "Title2", "Title3"};
        final List<MovieItem> cached = createMovieItemListFromTitles(TITLES);
        final List<MovieItem> expected = createMovieItemListFromTitles(TITLES);

        cacheMoviesToAllCategories(cached);

        testMovieCacheRequests(false, expected);
    }

    @Test
    public void test_getMovieListCachedWithRefresh() {
        final String[] NEW_TITLES = new String[] {"Title1", "Title2", "Title3"};
        final String[] CACHED_TITLES = new String[] {"TitleOld1,", "TitleOld2"};
        final MovieResponse mockResponse = createMockResponseFromTitles(NEW_TITLES);
        final List<MovieItem> cached = createMovieItemListFromTitles(CACHED_TITLES);
        final List<MovieItem> expected = createMovieItemListFromTitles(NEW_TITLES);

        cacheMoviesToAllCategories(cached);
        mockCacheCallsWithResponse(mockResponse);

        testMovieCacheRequests(true, expected);
    }

    @Test
    public void test_getMovieListNotCachedWithRefresh() {
        final String[] TITLES = new String[] {"Title1", "Title2", "Title3"};
        final MovieResponse mockResponse = createMockResponseFromTitles(TITLES);
        final List<MovieItem> expected = createMovieItemListFromTitles(TITLES);

        mockCacheCallsWithResponse(mockResponse);

        testMovieCacheRequests(true, expected);
    }

    private MovieResponse createMockResponseFromTitles(String[] titles) {
        final MovieResponse response = new MovieResponse();
        List<Movie> list;
        if (titles == null) {
            list = Collections.emptyList();
        } else {
            final Movie.Builder builder = new Movie.Builder();
            list = Stream.of(titles).map(title -> builder.title(title).build())
                    .collect(Collectors.toList());
        }
        response.results = list;
        return response;
    }

    private List<MovieItem> createMovieItemListFromTitles(String[] titles) {
        return Stream.of(titles).map(this::titleToMovieItem).collect(Collectors.toList());
    }

    private MovieItem titleToMovieItem(String title) {
        final MovieItem movie = new MovieItem();
        movie.setTitle(title);
        return movie;
    }

    private void mockCacheCallsWithResponse(MovieResponse response) {
        when(mockMovieService.listFeaturedMovies(anyString())).thenReturn(Single.just(response));
        when(mockMovieService.listPopularMovies(anyString())).thenReturn(Single.just(response));
        when(mockMovieService.listTopRatedMovies(anyString())).thenReturn(Single.just(response));
        when(mockMovieService.listUpcomingMovies(anyString())).thenReturn(Single.just(response));
    }

    private void cacheMoviesToAllCategories(List<MovieItem> movies) {
        cache.setFeaturedMovies(movies);
        cache.setUpcomingMovies(movies);
        cache.setTopRatedMovies(movies);
        cache.setPopularMovies(movies);
    }

    private void testMovieCacheRequests(boolean refresh, List<MovieItem> expected) {
        for (TwoItems<BiFunction<MoviesCache, Boolean, Single<List<MovieItem>>>, String> items : cacheCalls) {
            System.out.print(items.second + ": ");
            TestObserver<List<MovieItem>> testObserver = items.first.apply(cache, refresh)
                    .observeOn(scheduler)
                    .test();

            scheduler.triggerActions();
            testObserver.awaitTerminalEvent(300, TimeUnit.MILLISECONDS);
            testObserver.assertNoErrors()
                .assertValue(expected);
            System.out.println("success");
        }
    }
}
