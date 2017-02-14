package testtask.funda.com.fundatesttask.makelaars;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.communication.FundaApiRequestBuilder;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.QueryResponse;

import java.io.IOException;

public class MakelaarsActivity extends AppCompatActivity {

	private static final String TAG = AppCompatActivity.class.getSimpleName();

	OkHttpClient _client = new OkHttpClient();

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

		// Create the presenter
//		_makelaarsPresenter = new MakelaarsPresenter();

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

	void run() throws IOException {
		Request request = getAmsterdamGardensRequest();

		_client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				Log.e(TAG, "Failed");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				} else {
					String json = response.body().string();

					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.registerTypeAdapter(ObjectForSale.class, new ObjectForSale.ObjectForSaleDeserializer());
					Gson gson = gsonBuilder.create();

					QueryResponse queryResponse = gson.fromJson(json, QueryResponse.class);
					Log.d(TAG, queryResponse.toString());
				}
			}
		});
	}

	private Request getAmsterdamGardensRequest() {
		return new FundaApiRequestBuilder()
				.type(FundaApiRequestBuilder.TYPE_BUY)
				.searchQuery("/amsterdam/tuin/")
				.page(1)
				.pageSize(25).build();
	}
}
