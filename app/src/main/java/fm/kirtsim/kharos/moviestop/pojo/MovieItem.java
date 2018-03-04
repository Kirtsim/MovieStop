package fm.kirtsim.kharos.moviestop.pojo;

import android.graphics.Movie;

/**
 * Created by kharos on 05/02/2018
 */

public class MovieItem {

    private String title;
    private String backdropPosterURL;


    public MovieItem() {
        this.title = "";
        this.backdropPosterURL = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBackdropPosterURL(String url) {
        if (url == null)
            url = "";
        this.backdropPosterURL = url;
    }

    public String getBackdropPosterURL() {
        return backdropPosterURL;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof MovieItem)) return false;

        final MovieItem other = (MovieItem) obj;
        return other.backdropPosterURL == this.backdropPosterURL &&
                other.title == this.title;
    }
}
