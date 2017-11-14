package fm.kirtsim.kharos.moviestop.mvcview.movieList;

import fm.kirtsim.kharos.moviestop.mvcview.ViewMvc;

/**
 * Created by kharos on 09/11/2017
 */

public interface MovieListViewMvc extends ViewMvc {

    public interface MovieListViewListener {
        void onMovieStarred(int position, boolean startState);
        void onMovieItemClicked(int position);
    }

}
