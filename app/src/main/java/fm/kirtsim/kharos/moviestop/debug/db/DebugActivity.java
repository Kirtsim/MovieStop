package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;

import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_STATUS;

/**
 * Created by kharos on 15/03/2018
 */

public final class DebugActivity extends AppCompatActivity {

    private CheckBox refreshCbx;

    private Spinner statusSpinner;

    private Button showMoviesBtn;
    private Button showStatusesBtn;

    private String[] statuses;
    private String status;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_db_activity);
        initViews();



        showMoviesBtn.setOnClickListener(ev -> startFragment(new DebugFragmentMovieDB()));
        showStatusesBtn.setOnClickListener(ev -> startFragment(new DebugFragmentMovieStatusDB()));


        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private TMDBApiComponent getApiComponent() {
        App app = (App) getApplication();
        return app.getTmdbApiComponent();
    }

    private void initViews() {
        refreshCbx = findViewById(R.id.cbx_refresh);
        statusSpinner = findViewById(R.id.status_spinner);
        showMoviesBtn = findViewById(R.id.btn_fetch_movies);
        showStatusesBtn = findViewById(R.id.btn_fetch_status);
    }

    private void startFragment(DebugFragment fragment) {
        final FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
        txn.replace(R.id.container, fragment, null);
        fragment.setArguments(bundleStatus(null));
        txn.commit();
    }

    private Bundle bundleStatus(Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.putString(ARG_STATUS, status);
        return bundle;
    }
}
