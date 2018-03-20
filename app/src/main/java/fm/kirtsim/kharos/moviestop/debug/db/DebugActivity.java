package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.repository.MovieRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_REFRESH;
import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_STATUS;

/**
 * Created by kharos on 15/03/2018
 */

public final class DebugActivity extends AppCompatActivity {

    @Inject
    MovieRepository repo;

    private ViewGroup toolContainer;

    private CheckBox refreshCbx;

    private Spinner statusSpinner;

    private Button showMoviesBtn;
    private Button showStatusesBtn;
    private Button deleteAllBtn;

    private String[] statusCodes;
    private String status;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getApplication()).getTmdbApiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_db_activity);
        initStatusCodes();
        initViews();

        showMoviesBtn.setOnClickListener(ev -> showFragment(new DebugFragmentMovieDB()));
        showStatusesBtn.setOnClickListener(ev -> showFragment(new DebugFragmentMovieStatusDB()));
        deleteAllBtn.setOnClickListener(ev -> {
            repo.clearDatabase().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Snackbar.make(toolContainer,
                            "database cleared", Snackbar.LENGTH_SHORT).show(),
                            e -> Snackbar.make(toolContainer, "failed to clear the db", Snackbar.LENGTH_SHORT).show());
        });

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = statusCodes[position];
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            toolContainer.setVisibility(View.VISIBLE);
    }

    private void initStatusCodes() {
        final String[] statuses = getResources().getStringArray(R.array.movie_status);

        statusCodes = new String[statuses.length];
        for (int i = 0; i < statuses.length; ++i)
            statusCodes[i] = statuses[i].substring(0, 1);
    }

    private void initViews() {
        toolContainer = findViewById(R.id.tool_container);
        refreshCbx = findViewById(R.id.cbx_refresh);
        statusSpinner = findViewById(R.id.status_spinner);
        showMoviesBtn = findViewById(R.id.btn_fetch_movies);
        showStatusesBtn = findViewById(R.id.btn_fetch_status);
        deleteAllBtn = findViewById(R.id.btn_delete);
    }

    private void showFragment(DebugFragment fragment) {
        toolContainer.setVisibility(View.INVISIBLE);
        startFragment(fragment);
    }

    private void startFragment(DebugFragment fragment) {
        final FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
        txn.replace(R.id.container, fragment, null);
        txn.addToBackStack(null);
        Bundle bundle = bundleStatus(null);
        fragment.setArguments(bundleRefreshFlag(bundle));
        txn.commit();
    }

    private Bundle bundleStatus(Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.putString(ARG_STATUS, status);
        return bundle;
    }

    private Bundle bundleRefreshFlag(Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle(1);
        bundle.putBoolean(ARG_REFRESH, refreshCbx.isChecked());
        return bundle;
    }
}
