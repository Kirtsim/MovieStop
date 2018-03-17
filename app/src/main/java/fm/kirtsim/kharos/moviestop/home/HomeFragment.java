package fm.kirtsim.kharos.moviestop.home;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.MovieBaseFragment;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.databinding.FragmentHomeBinding;
import fm.kirtsim.kharos.moviestop.factory.viewModel.HomeFragmentVMFactory;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;

/**
 * Created by kharos on 05/02/2018
 */

public class HomeFragment extends MovieBaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeFragmentVM postersVM;
    @Inject MovieRepository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTMDBApiComponent().inject(this);
        super.onCreate(savedInstanceState);
        String baseUrl = getString(R.string.POSTER_BASE_URL);

        final HomeFragmentVMFactory factory = new HomeFragmentVMFactory(repository,
                getConnectivityManager(getActivity()), baseUrl);
        postersVM = ViewModelProviders.of(this, factory).get(HomeFragmentVM.class);
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
        if (savedInstanceState != null)
            loadImages();
        else
            refresh();
    }

    private ConnectivityManager getConnectivityManager(Activity activity) {
        if (activity != null) {
            return (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return null;
    }

    private void loadImages() {
        postersVM.fetchAllPosterImages(false);
    }

    private void refresh() {
        postersVM.fetchAllPosterImages(true);
    }
}
