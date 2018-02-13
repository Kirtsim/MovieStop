package fm.kirtsim.kharos.moviestop.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.customComponent.ThumbnailTab;
import io.reactivex.Single;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragment extends MovieBaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeFragmentVM postersVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postersVM = ViewModelProviders.of(this).get(HomeFragmentVM.class);
        postersVM.setCachedMovies(getCachedMovies());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
//        final View root = getView();
//        if (root != null) {
//            loadImage(postersVM.getFeaturedPosterURL(false), root.findViewById(R.id.tab_featured));
//            loadImage(postersVM.getPopularPosterURL(false),  root.findViewById(R.id.tab_popular));
//            loadImage(postersVM.getTopRatedPosterURL(false), root.findViewById(R.id.tab_top_rated));
//            loadImage(postersVM.getUpcomingPosterURL(false), root.findViewById(R.id.tab_upcoming));
//        }
        postersVM.fetchAllPosterImages(false);
    }

    private void loadImage(Single<String> urlSingle, ThumbnailTab tab) {
        urlSingle.subscribe(
                url -> Glide.with(getContext()).load(url).into((ImageView) tab.findViewById(R.id.image)),
                Throwable::printStackTrace
        );
    }
}
