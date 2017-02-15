package testtask.funda.com.fundatesttask.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import testtask.funda.com.fundatesttask.data.model.FundaApiQueryResponse;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;

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

	public static FundaApiQueryResponse parseResponse(String json) {
		return _gson.fromJson(json, FundaApiQueryResponse.class);
	}
}
