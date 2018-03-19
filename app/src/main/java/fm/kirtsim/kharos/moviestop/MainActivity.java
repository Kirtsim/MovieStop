package fm.kirtsim.kharos.moviestop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fm.kirtsim.kharos.moviestop.debug.db.DebugActivity;
import fm.kirtsim.kharos.moviestop.factory.fragment.AbstractFragmentFactory;
import fm.kirtsim.kharos.moviestop.factory.fragment.FragmentFactory;

public class MainActivity extends AppCompatActivity implements BaseFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initialize();
        startFragment(FragmentFactory.ID_MOVIE_HOME_FRAGMENT, null, true);
    }

    private void initialize() {
        setSupportActionBar(findViewById(R.id.toolbar));
        initializeDrawerLayout();
        initNavigationView();
    }

    private void initializeDrawerLayout() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = initActionBarDrawerToggle(drawer, toolbar);
        drawer.addDrawerListener(toggle);
    }

    private ActionBarDrawerToggle initActionBarDrawerToggle(DrawerLayout drawer, Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        return toggle;
    }

    private void initNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_debug_db) {
            startActivity(new Intent(getApplicationContext(), DebugActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startFragment(int fragmentID, Bundle args, boolean addToBackStack) {
        AbstractFragmentFactory factory = new FragmentFactory().getFragmentFactory(fragmentID);
        MovieBaseFragment fragment = factory.create(args);
        replaceFragment(fragment, addToBackStack);
    }

    private void replaceFragment(MovieBaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
        txn.replace(R.id.fragment_container, fragment, null);
        if (addToBackStack)
            txn.addToBackStack(null);
        txn.commit();
    }

    @Override
    public void popLastFragment() {
        getSupportFragmentManager().popBackStack();
    }

}
