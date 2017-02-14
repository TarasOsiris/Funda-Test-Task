package testtask.funda.com.fundatesttask.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsRemoteDataSource implements MakelaarsDataSource {
	@Override
	public void loadObjectsForSale(@NonNull LoadObjectsForSaleCallback callback) {

	}

	public static MakelaarsRemoteDataSource getInstance(Context applicationContext) {
		return null;
	}
}
