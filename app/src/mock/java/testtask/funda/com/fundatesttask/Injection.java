package testtask.funda.com.fundatesttask;

import android.content.Context;
import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.data.FakeMakelaarsDataSource;
import testtask.funda.com.fundatesttask.data.source.MakelaarsRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource} at compile time.
 */
public class Injection {
	public static MakelaarsRepository provideMakelaarsRepository(@NonNull Context context) {
		checkNotNull(context);
		return MakelaarsRepository.getInstance(FakeMakelaarsDataSource.getInstance(context));
	}
}
