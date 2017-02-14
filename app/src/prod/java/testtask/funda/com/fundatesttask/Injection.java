package testtask.funda.com.fundatesttask;

import android.content.Context;
import testtask.funda.com.fundatesttask.data.source.MakelaarsRepository;
import testtask.funda.com.fundatesttask.data.source.remote.MakelaarsRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class Injection {
	public static MakelaarsRepository provideMakelaarsRepository(Context applicationContext) {
		checkNotNull(applicationContext);
		return MakelaarsRepository.getInstance(MakelaarsRemoteDataSource.getInstance(applicationContext));
	}
}
