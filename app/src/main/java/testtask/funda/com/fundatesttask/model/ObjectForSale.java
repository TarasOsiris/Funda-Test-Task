package testtask.funda.com.fundatesttask.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class ObjectForSale {

	private final RealEstateAgent _realEstateAgent;

	public ObjectForSale(RealEstateAgent agent) {
		_realEstateAgent = agent;
	}

	public static class ObjectForSaleDeserializer implements JsonDeserializer<ObjectForSale> {
		@Override
		public ObjectForSale deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

			JsonObject jobject = (JsonObject) json;

			int id = jobject.get("MakelaarId").getAsInt();
			String name = jobject.get("MakelaarNaam").getAsString();

			RealEstateAgent agent = new RealEstateAgent(id, name);
			return new ObjectForSale(agent);
		}
	}
}
