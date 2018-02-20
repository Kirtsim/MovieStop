package fm.kirtsim.kharos.moviestop.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.cache.MoviesCache;
import fm.kirtsim.kharos.moviestop.databinding.FragmentHomeBinding;
import fm.kirtsim.kharos.moviestop.factory.viewModel.HomeFragmentVMFactory;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragment extends MovieBaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeFragmentVM postersVM;
    @Inject MoviesCache cachedMovies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTMDBApiComponent().inject(this);
        super.onCreate(savedInstanceState);
        String baseUrl = getString(R.string.POSTER_BASE_URL);
        postersVM = ViewModelProviders.of(this, new HomeFragmentVMFactory(cachedMovies, baseUrl))
                .get(HomeFragmentVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        binding.setViewModel(postersVM);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadImages();
    }

    private void loadImages() {
        postersVM.fetchAllPosterImages(false);
    }

    private void refresh() {
        postersVM.fetchAllPosterImages(true);
    }
}
