package fm.kirtsim.kharos.moviestop.mvcview.movieList;

import android.os.Bundle;
import android.view.ViewGroup;

import fm.kirtsim.kharos.moviestop.mvcview.BaseViewMvc;

/**
 * Created by kharos on 09/11/2017
 */

public class MovieListViewMvcImpl extends BaseViewMvc implements MovieListViewMvc {


    public MovieListViewMvcImpl(ViewGroup container) {
        super(container);
    }

    @Override
    public void saveStateTo(Bundle bundle) {

    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }
}
