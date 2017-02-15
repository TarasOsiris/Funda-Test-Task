package testtask.funda.com.fundatesttask.data.model;

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

	// Real estate agent that is selling this object
	private final RealEstateAgent _realEstateAgent;

	private ObjectForSale(RealEstateAgent agent) {
		_realEstateAgent = agent;
	}

	public RealEstateAgent getRealEstateAgent() {
		return _realEstateAgent;
	}

	public static class ObjectForSaleDeserializer implements JsonDeserializer<ObjectForSale> {

		private static final String MAKELAAR_ID = "MakelaarId";

		private static final String MAKELAAR_NAAM = "MakelaarNaam";

		@Override
		public ObjectForSale deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

			JsonObject jobject = (JsonObject) json;

			int id = jobject.get(MAKELAAR_ID).getAsInt();
			String name = jobject.get(MAKELAAR_NAAM).isJsonNull() ? "[NO NAME]" : jobject.get(MAKELAAR_NAAM).getAsString();

			RealEstateAgent agent = new RealEstateAgent(id, name);
			return new ObjectForSale(agent);
		}
	}
}
