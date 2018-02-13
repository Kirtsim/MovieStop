package fm.kirtsim.kharos.moviestop.binding;

import android.databinding.BindingAdapter;

import fm.kirtsim.kharos.moviestop.customComponent.ThumbnailTab;

/**
 * Created by kharos on 13/02/2018
 */

public class ThumbnailTabBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ThumbnailTab tab, String imageUrl) {
        tab.setImageUrl(imageUrl);
    }
}
