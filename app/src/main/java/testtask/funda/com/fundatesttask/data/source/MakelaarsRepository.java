package testtask.funda.com.fundatesttask.data.source;

import android.support.annotation.NonNull;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsRepository implements MakelaarsDataSource {

	private static MakelaarsRepository _instance = null;

	private final MakelaarsDataSource _makelaarsRemoteDataSource;

	private MakelaarsRepository(@NonNull MakelaarsDataSource makelaarsRemoteDataSource) {
		_makelaarsRemoteDataSource = makelaarsRemoteDataSource;
	}

	public static MakelaarsRepository getInstance(MakelaarsDataSource makelaarsRemoteDataSource) {
		if (_instance == null) {
			_instance = new MakelaarsRepository(makelaarsRemoteDataSource);
		}
		return _instance;
	}

	@Override
	public void loadObjectsForSale(@NonNull LoadObjectsForSaleCallback callback) {
		_makelaarsRemoteDataSource.loadObjectsForSale(callback);
	}
}
