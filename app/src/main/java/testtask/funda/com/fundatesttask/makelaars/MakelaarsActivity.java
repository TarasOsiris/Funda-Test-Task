package testtask.funda.com.fundatesttask.makelaars;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import testtask.funda.com.fundatesttask.Injection;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.utils.ActivityUtils;

public class MakelaarsActivity extends AppCompatActivity {

	private DrawerLayout _drawerLayout;

	private MakelaarsPresenter _makelaarsPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Toolbar setup
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar ab = getSupportActionBar();
		ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(R.string.app_name);

		// Navigation drawer
		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null) {
			setupDrawerContent(navigationView);
		}

		// Create view (fragment that displays list)
		MakelaarsFragment makelaarsFragment =
				(MakelaarsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
		if (makelaarsFragment == null) {
			makelaarsFragment = MakelaarsFragment.newInstance();
			ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), makelaarsFragment, R.id.contentFrame);
		}

		// Create the presenter
		_makelaarsPresenter = new MakelaarsPresenter(Injection.provideMakelaarsRepository(getApplicationContext()),
				makelaarsFragment);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// Open the navigation drawer when the home icon is selected from the toolbar.
				_drawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				return true;
			}
		});
	}


}
