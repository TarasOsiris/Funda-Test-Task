package testtask.funda.com.fundatesttask.data.source.remote;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.PagingInfo;
import testtask.funda.com.fundatesttask.data.model.QueryResponse;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;
import testtask.funda.com.fundatesttask.utils.GsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsRemoteDataSource implements MakelaarsDataSource {

	private static final String TAG = AppCompatActivity.class.getSimpleName();
	private static final int INITIAL_PAGE_NUMBER = 1;

	private final OkHttpClient _client;
	private final Handler _handler;

	// Prevent direct instantiation
	private MakelaarsRemoteDataSource() {
		_client = new OkHttpClient();
		_handler = new Handler(Looper.getMainLooper());
	}

	public static MakelaarsRemoteDataSource getInstance(Context applicationContext) {
		return new MakelaarsRemoteDataSource();
	}

	@Override
	public void loadObjectsForSale(@NonNull final LoadObjectsForSaleCallback callback) {
		// This list accumulates all the objects for sale obtained via interval pagination requests
		final List<ObjectForSale> accumulatedObjectsForSale = new ArrayList<>();

		final PagingInfo[] pagingInfo = new PagingInfo[1];

		// Load the first page to get the information about paging
		Request initialRequest = getAmsterdamGardensRequest(INITIAL_PAGE_NUMBER);
		_client.newCall(initialRequest).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				callback.onError();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (!response.isSuccessful()) {
					Log.e(TAG, "Unexpected code " + response);
					callback.onError();
				} else {
					final QueryResponse queryResponse = GsonUtils.parseResponse(response.body().string());
					pagingInfo[0] = queryResponse.getPagingInfo();
					accumulatedObjectsForSale.addAll(queryResponse.getObjectsForSale());



					postCallbackOnUiThread(queryResponse, callback);
				}
			}
		});

		// Accumulate data through pagination
//		PagingInfo currentPageInfo = pagingInfo[0];
//		while (currentPageInfo.getNextUrl() != null) {
//		}
	}

	private void postCallbackOnUiThread(final QueryResponse queryResponse, @NonNull final LoadObjectsForSaleCallback callback) {
		// Run on UI Thread
		_handler.post(new Runnable() {
			@Override
			public void run() {
				callback.onObjectsLoaded(queryResponse.getObjectsForSale());
			}
		});
	}

	private Request getAmsterdamGardensRequest(int page) {
		return new FundaApiRequestBuilder()
				.type(FundaApiRequestBuilder.TYPE_BUY)
				.searchQuery("/amsterdam/tuin/")
				.page(page)
				.pageSize(25).build();
	}
}
