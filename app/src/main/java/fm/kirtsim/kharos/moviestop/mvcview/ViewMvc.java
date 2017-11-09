package fm.kirtsim.kharos.moviestop.mvcview;

import android.os.Bundle;
import android.view.View;

/**
 * Created by kharos on 09/11/2017
 */

public interface ViewMvc {

    View getRootView();

    void saveStateTo(Bundle bundle);
    Bundle getState();
}
