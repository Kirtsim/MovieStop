package fm.kirtsim.kharos.moviestop.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import javax.inject.Inject;

import fm.kirtsim.kharos.moviestop.App;
import fm.kirtsim.kharos.moviestop.R;
import fm.kirtsim.kharos.moviestop.db.MovieDao;
import fm.kirtsim.kharos.moviestop.db.MovieStatusDao;
import fm.kirtsim.kharos.moviestop.di.component.TMDBApiComponent;

/**
 * Created by kharos on 15/03/2018
 */

public final class DebugActivity extends AppCompatActivity {

    @Inject
    MovieDao movieDao;

    @Inject
    MovieStatusDao statusDao;

    private CheckBox refreshCbx;

    private Spinner statusSpinner;

    private Button showMoviesBtn;
    private Button showStatusesBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getApiComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_db_activity);
        initViews();
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


}
