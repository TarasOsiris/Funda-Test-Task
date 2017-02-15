package testtask.funda.com.fundatesttask.data.source;

import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;

import java.util.List;

/**
 * Represents an entry point to get real estate data
 */

public interface MakelaarsDataSource {
	interface LoadObjectsForSaleCallback {
		void onObjectsLoaded(List<ObjectForSale> objectForSale);

		void onProgressChanged(int current, int total);

		void onError();
	}

	void loadObjectsForSale(String location, boolean hasYard, @NonNull LoadObjectsForSaleCallback callback);
}
