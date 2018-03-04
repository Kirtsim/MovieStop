package fm.kirtsim.kharos.moviestop.pojo;


import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kharos on 09/02/2018
 */

public class MovieResponse {

    @SerializedName("results")
    @Expose private List<Movie> results = null;

    @SerializedName("page")
    @Expose private int page;

    @SerializedName("total_results")
    @Expose private int totalResults;

    @SerializedName("dates")
    @Expose
    private MovieResponseDates responseDates;

    @SerializedName("total_pages")
    @Expose private int totalPages;


    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        if (results == null)
            results = Collections.emptyList();
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public MovieResponseDates getResponseDates() {
        return responseDates;
    }

    public void setResponseDates(MovieResponseDates responseDates) {
        if (responseDates == null)
            responseDates = new MovieResponseDates("", "");
        this.responseDates = responseDates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
