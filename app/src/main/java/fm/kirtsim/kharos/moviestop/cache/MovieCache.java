package fm.kirtsim.kharos.moviestop.cache;

import java.util.ArrayList;
import java.util.List;

import fm.kirtsim.kharos.moviestop.pojo.Movie;

/**
 * Created by kharos on 04/03/2018
 */

public final class MovieCache {

    private List<Movie> featuredMovies;
    private List<Movie> popularMovies;
    private List<Movie> upcomingMovies;
    private List<Movie> topRatedMovies;

    public MovieCache() {
        this.featuredMovies = new ArrayList<>();
        this.popularMovies = new ArrayList<>();
        this.upcomingMovies = new ArrayList<>();
        this.topRatedMovies = new ArrayList<>();
    }

    public List<Movie> getFeaturedMovies() {
        return featuredMovies;
    }

    public List<Movie> getPopularMovies() {
        return popularMovies;
    }

    public List<Movie> getUpcomingMovies() {
        return upcomingMovies;
    }

    public List<Movie> getTopRatedMovies() {
        return topRatedMovies;
    }


    public void setFeaturedMovies(List<Movie> featuredMovies) {
        if (featuredMovies == null)
            featuredMovies = new ArrayList<>();
        this.featuredMovies = featuredMovies;
    }

    public void setPopularMovies(List<Movie> popularMovies) {
        if (popularMovies == null)
            popularMovies = new ArrayList<>();
        this.popularMovies = popularMovies;
    }

    public void setUpcomingMovies(List<Movie> upcomingMovies) {
        if (upcomingMovies == null)
            upcomingMovies = new ArrayList<>();
        this.upcomingMovies = upcomingMovies;
    }

    public void setTopRatedMovies(List<Movie> topRatedMovies) {
        if (topRatedMovies == null)
            topRatedMovies = new ArrayList<>();
        this.topRatedMovies = topRatedMovies;
    }
}
