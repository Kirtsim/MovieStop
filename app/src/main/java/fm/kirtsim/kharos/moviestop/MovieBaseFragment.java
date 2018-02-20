package fm.kirtsim.kharos.moviestop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;

/**
 * Created by kharos on 03/02/2018
 */

public abstract class MovieBaseFragment extends Fragment {

    private BaseFragmentListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof BaseFragmentListener))
            throw new IllegalArgumentException("activity must implement " +
                    BaseFragmentListener.class.getSimpleName());
        listener = (BaseFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    protected void startFragment(int fragmentID, Bundle args, boolean addToBackStack) {
        listener.startFragment(fragmentID, args, addToBackStack);
    }

    protected void finish() {
        listener.popLastFragment();
    }

    protected TMDBApiComponent getTMDBApiComponent() {
        final App app = (App) getActivity().getApplication();
        return app.getTmdbApiComponent();
    }

}
