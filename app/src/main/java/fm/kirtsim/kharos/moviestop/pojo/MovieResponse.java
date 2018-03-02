package fm.kirtsim.kharos.moviestop.pojo;


import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kharos on 09/02/2018
 */

public class MovieResponse {

    @SerializedName("results")
    public List<Movie> results = null;

    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int totalResults;

    @SerializedName("dates")
    public MovieResponseDates responseDates;

    @SerializedName("total_pages")
    public int totalPages;

}
