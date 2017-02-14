package testtask.funda.com.fundatesttask.makelaars;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.communication.FundaApiRequestBuilder;
import testtask.funda.com.fundatesttask.model.ObjectForSale;
import testtask.funda.com.fundatesttask.model.QueryResponse;

import java.io.IOException;

public class MakelaarsActivity extends AppCompatActivity {

	private static final String TAG = AppCompatActivity.class.getSimpleName();

	Button _button;

	OkHttpClient _client = new OkHttpClient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_button = (Button) findViewById(R.id.testButton);
		_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "test");
				try {
					run();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
