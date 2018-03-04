package fm.kirtsim.kharos.moviestop.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kharos on 09/02/2018
 */

class MovieResponseDates {

    @SerializedName("maximum")
    @Expose
    private String maximum;

    @SerializedName("minimum")
    @Expose private String minimum;

    public MovieResponseDates(String max, String min) {
        setMaximum(max);
        setMinimum(min);
    }

    public String getMaximum() {
        return maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMaximum(String maximum) {
        if (maximum == null)
            maximum = "";
        this.maximum = maximum;
    }

    public void setMinimum(String minimum) {
        if (minimum == null)
            minimum = "";
        this.minimum = minimum;
    }
}
