package fm.kirtsim.kharos.moviestop;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import fm.kirtsim.kharos.moviestop.cache.MainCache;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.pojo.MovieResponse;
import fm.kirtsim.kharos.moviestop.pojo.MovieResult;
import fm.kirtsim.kharos.moviestop.remote.MovieListService;

import static org.mockito.Mockito.when;

/**
 * Created by kharos on 21/02/2018
 */

public final class TestMainCache {

    @Mock
    MovieListService mockMovieService;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private String api_key = "12345678910";

    @Test
    public void test_FeaturedMovieListNotNull() {
        MovieResponse mockResponse = new MovieResponse();
        mockResponse.setResults();
        MoviesCache cache = new MainCache(mockMovieService, api_key);
        when(mockMovieService.listFeaturedMovies(api_key))
        cache.getFeaturedMovies(false);
    }

    private List<MovieResult> createMockMovieResults(String ... titles) {
        if (titles == null) return Collections.emptyList();

        final MovieResult.Builder builder = new MovieResult.Builder();
        List<MovieResult> mockedResults = (Arrays.stream(titles).map(title -> builder.title(title).build()).toArray(MovieResult[]::new));
    }

}
