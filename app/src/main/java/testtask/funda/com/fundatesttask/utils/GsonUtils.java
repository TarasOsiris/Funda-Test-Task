package testtask.funda.com.fundatesttask.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class GsonUtils {
	public static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ObjectForSale.class, new ObjectForSale.ObjectForSaleDeserializer());
		return gsonBuilder.create();
	}
}
