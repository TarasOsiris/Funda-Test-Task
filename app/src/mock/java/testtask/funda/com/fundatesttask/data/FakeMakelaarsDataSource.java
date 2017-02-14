package testtask.funda.com.fundatesttask.data;

import android.content.Context;
import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.data.model.QueryResponse;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;
import testtask.funda.com.fundatesttask.utils.GsonUtils;
import testtask.funda.com.fundatesttask.utils.TextUtils;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class FakeMakelaarsDataSource implements MakelaarsDataSource {

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
	public void loadObjectsForSale(@NonNull LoadObjectsForSaleCallback callback) {
		String json = TextUtils.readRawTextFile(_context, R.raw.mock_reponse);
		QueryResponse response = GsonUtils.createGson().fromJson(json, QueryResponse.class);
		callback.onObjectsLoaded(response.getObjectsForSale());
	}
}
