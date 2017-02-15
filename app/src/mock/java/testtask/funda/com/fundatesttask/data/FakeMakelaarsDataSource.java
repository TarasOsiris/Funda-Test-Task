package testtask.funda.com.fundatesttask.data;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.data.model.FundaApiQueryResponse;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;
import testtask.funda.com.fundatesttask.utils.GsonUtils;
import testtask.funda.com.fundatesttask.utils.TextUtils;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class FakeMakelaarsDataSource implements MakelaarsDataSource {

	private static final long SERVICE_LATENCY_IN_MILLIS = 5000;

	private static FakeMakelaarsDataSource INSTANCE;

	private final Context _context;

	private FakeMakelaarsDataSource(Context context) {
		_context = context;
	}

	public static FakeMakelaarsDataSource getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new FakeMakelaarsDataSource(context);
		}
		return INSTANCE;
	}

	@Override
	public void loadObjectsForSale(String location, boolean hasYard, @NonNull final LoadObjectsForSaleCallback callback) {
		// Simulate network by delaying the execution.
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Read the data from local file for test purposes not to spam server with requests for testing
				String json = TextUtils.readRawTextFile(_context, R.raw.mock_reponse);
				FundaApiQueryResponse response = GsonUtils.parseResponse(json);
				callback.onObjectsLoaded(response.getObjectsForSale());
			}
		}, SERVICE_LATENCY_IN_MILLIS);
	}
}
