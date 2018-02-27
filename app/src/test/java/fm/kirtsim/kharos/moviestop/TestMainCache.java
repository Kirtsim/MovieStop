package fm.kirtsim.kharos.moviestop;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fm.kirtsim.kharos.moviestop.cache.MainCache;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.MovieItem;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieResult;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by kharos on 21/02/2018
 */

@RunWith(MockitoJUnitRunner.class)
public final class TestMainCache {

    @Mock
    MovieListService mockMovieService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private final String API_KEY = "12345678910";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_FeaturedMovieListNoCacheNoRefresh() {
        final String[] TITLES = new String[] { "Title1", "Title2", "Title3" };
        final MovieResponse mockResponse = createMockResponseFromTitles(TITLES);
        final TestScheduler scheduler = new TestScheduler();

        when(mockMovieService.listFeaturedMovies(anyString())).thenReturn(Single.just(mockResponse));
        MoviesCache cache = new MainCache(mockMovieService, API_KEY, () -> scheduler);

        TestObserver<List<MovieItem>> testObserver = cache.getFeaturedMovies(false)
                .observeOn(scheduler)
                .test();
        scheduler.triggerActions();

        testObserver.awaitTerminalEvent(300, TimeUnit.MILLISECONDS);
        testObserver.assertNoErrors()
                .assertValue(createMovieItemListFromTitles(TITLES));
    }


    @Test
    public void test_FeaturedMovieListCachedNoRefresh() {
        final String[] TITLES = new String[] { "Title1", "Title2", "Title3" };

        MoviesCache cache = new MainCache(mockMovieService, API_KEY, TestScheduler::new);
        ((MainCache) cache).setFeaturedMovies(createMovieItemListFromTitles(TITLES));
        TestObserver<List<MovieItem>> testObserver = cache.getFeaturedMovies(false)
                .test();
        testObserver.awaitTerminalEvent(300, TimeUnit.MILLISECONDS);
        testObserver.assertNoErrors()
                .assertValue(createMovieItemListFromTitles(TITLES));
    }

    private MovieResponse createMockResponseFromTitles(String[] titles) {
        final MovieResponse response = new MovieResponse();
        List<MovieResult> list;
        if (titles == null) {
            list = Collections.emptyList();
        } else {
            final MovieResult.Builder builder = new MovieResult.Builder();
            list = Stream.of(titles).map(title -> builder.title(title).build())
                    .collect(Collectors.toList());
        }
        response.setResults(list);
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
}
