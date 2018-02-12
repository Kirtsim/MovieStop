package fm.kirtsim.kharos.moviestop.pojo;

/**
 * Created by kharos on 05/02/2018
 */

public class MovieItem {

    private String backdropPosterURL;


    public MovieItem() {
        this.backdropPosterURL = "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg";
    }

    public void setBackdropPosterURL(String url) {
        this.backdropPosterURL = url;
    }

    public String getBackdropPosterURL() {
        return backdropPosterURL;
    }
}
