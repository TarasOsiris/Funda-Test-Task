package testtask.funda.com.fundatesttask.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.QueryResponse;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class GsonUtils {

	private static Gson _gson = createGson();

	private static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ObjectForSale.class, new ObjectForSale.ObjectForSaleDeserializer());
		return gsonBuilder.create();
	}

	public static QueryResponse parseResponse(String json) {
		return _gson.fromJson(json, QueryResponse.class);
	}
}
