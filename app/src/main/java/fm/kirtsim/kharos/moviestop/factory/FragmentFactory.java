package fm.kirtsim.kharos.moviestop.factory;

/**
 * Created by kharos on 03/02/2018
 */

public class FragmentFactory {

    public static final int ID_MOVIE_HOME_FRAGMENT = 1;
    public static final int ID_MOVIE_LIST_FRAGMENT = 2;
    public static final int ID_MOVIE_DETAIL_FRAGMENT = 3;

    public AbstractFragmentFactory getFragmentFactory(int fragmentID) {
        switch (fragmentID) {
            case ID_MOVIE_HOME_FRAGMENT: return new HomeFragmentFactory();
            case ID_MOVIE_LIST_FRAGMENT: return null;
            case ID_MOVIE_DETAIL_FRAGMENT: return null;
            default: throw new IllegalArgumentException("not existing fragment ID: " + fragmentID);
        }
    }

}
