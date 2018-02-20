package fm.kirtsim.kharos.moviestop;

import android.os.Bundle;

/**
 * Created by kharos on 03/02/2018
 */

public interface BaseFragmentListener {

    void startFragment(int fragmentID, Bundle args, boolean addToBackStack);

    void popLastFragment();

}
