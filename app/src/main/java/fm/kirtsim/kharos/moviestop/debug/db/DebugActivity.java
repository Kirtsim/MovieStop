package fm.kirtsim.kharos.moviestop.debug.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import fm.kirtsim.kharos.moviestop.R;

import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_REFRESH;
import static fm.kirtsim.kharos.moviestop.debug.db.DebugFragmentArguments.ARG_STATUS;

/**
 * Created by kharos on 15/03/2018
 */

public final class DebugActivity extends AppCompatActivity {

    private ViewGroup toolContainer;

    private CheckBox refreshCbx;

    private Spinner statusSpinner;

    private Button showMoviesBtn;
    private Button showStatusesBtn;

    private String[] statusCodes;
    private String status;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_db_activity);
        initStatusCodes();
        initViews();

        showMoviesBtn.setOnClickListener(ev -> showFragment(new DebugFragmentMovieDB()));
        showStatusesBtn.setOnClickListener(ev -> showFragment(new DebugFragmentMovieStatusDB()));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = statusCodes[position];
                Log.d("DEBUG", "status code: " + status);
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
