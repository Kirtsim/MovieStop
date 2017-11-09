package fm.kirtsim.kharos.moviestop.mvcview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kharos on 09/11/2017
 */

public abstract class BaseViewMvc<Listener> implements ViewMvc {

    protected Listener listener;
    protected View rootView;

    private BaseViewMvc() {}

    public BaseViewMvc(ViewGroup container) {
        final Context context = container.getContext();
        rootView = LayoutInflater.from(context).inflate(getLayoutResourceId(), container, false);
    }

    protected abstract @LayoutRes int getLayoutResourceId();

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void dismissListener() {
        listener = null;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Bundle getState() {
        final Bundle state = new Bundle();
        saveStateTo(state);
        return state;
    }
}
