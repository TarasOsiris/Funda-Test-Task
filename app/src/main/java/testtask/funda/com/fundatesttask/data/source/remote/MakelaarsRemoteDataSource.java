package testtask.funda.com.fundatesttask.data.source.remote;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import testtask.funda.com.fundatesttask.data.model.FundaApiQueryResponse;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.PagingInfo;
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

	private static final int DELAY_BETWEEN_REQUESTS_MILLIS = 500;

	private final OkHttpClient _client;

	private MakelaarsRemoteDataSource() {
		_client = new OkHttpClient();
	}

	public static MakelaarsRemoteDataSource getInstance() {
		return new MakelaarsRemoteDataSource();
	}

	@Override
	public void loadObjectsForSale(final String location, final boolean hasYard, @NonNull final LoadObjectsForSaleCallback callback) {
		// This list accumulates all the objects for sale obtained via interval pagination requests
		final List<ObjectForSale> result = new ArrayList<>();

		// Load the first page to get the information about paging
		Request initialRequest = getRequest(location, hasYard, INITIAL_PAGE_NUMBER);
		_client.newCall(initialRequest).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				callback.onError();
			}

			@Override
			public void onResponse(Call call, Response initialResponse) throws IOException {
				if (!initialResponse.isSuccessful()) {
					Log.e(TAG, "Unexpected code " + initialResponse);
					callback.onError();
				} else {
					final FundaApiQueryResponse initialResponseParsed = GsonUtils.parseResponse(initialResponse.body().string());
					PagingInfo pagingInfo = initialResponseParsed.getPagingInfo();

					// Accumulate object from initial request
					result.addAll(initialResponseParsed.getObjectsForSale());

					// Passing params like this does not look very nice but for the sake of simplicity left like this
					accumulateObjectsForSale(location, hasYard, pagingInfo, callback, result);

					// All objects are retrieved
					callback.onObjectsLoaded(result);
				}
			}
		});
	}

	private void accumulateObjectsForSale(String location, boolean hasYard, PagingInfo firstPageInfo,
										  @NonNull LoadObjectsForSaleCallback callback,
										  List<ObjectForSale> accumulatedObjectsForSale) throws IOException {

		int totalPageCount = firstPageInfo.getTotalPages();
		PagingInfo currentPageInfo = firstPageInfo;

		while (currentPageInfo.getNextUrl() != null) {

			// Wait between subsequent requests so we don't get rejected requests
			try {
				Thread.sleep(DELAY_BETWEEN_REQUESTS_MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Call the API for the next page
			int nextPage = currentPageInfo.getCurrentPageNumber() + 1;
			Response response = _client.newCall(getRequest(location, hasYard, nextPage)).execute();
			if (!response.isSuccessful()) {
				Log.e(TAG, "Unexpected code " + response);
				callback.onError();
				return;
			}

			final FundaApiQueryResponse subsequentPageResponseParsed = GsonUtils.parseResponse(response.body().string());
			currentPageInfo = subsequentPageResponseParsed.getPagingInfo();

			// Report progress for UI
			callback.onProgressChanged(currentPageInfo.getCurrentPageNumber(), totalPageCount);

			accumulatedObjectsForSale.addAll(subsequentPageResponseParsed.getObjectsForSale());
		}
	}

	private Request getRequest(String location, boolean hasYard, int page) {
		String searchQuery = "/" + location;
		if (hasYard) {
			searchQuery += "/tuin/";
		}
		FundaApiRequestBuilder requestBuilder = new FundaApiRequestBuilder()
				.type(FundaApiRequestBuilder.TYPE_BUY)
				.searchQuery(searchQuery)
				.page(page)
				.pageSize(25);
		return requestBuilder.build();
	}
}
