package testtask.funda.com.fundatesttask.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class RealEstateAgent {

	public RealEstateAgent(int id, String name) {
		_realEstateAgentId = id;
		_realEstateAgentName = name;
	}

	private final int _realEstateAgentId;
	private final String _realEstateAgentName;

	public String getRealEstateAgentName() {
		return _realEstateAgentName;
	}

	public int getRealEstateAgentId() {
		return _realEstateAgentId;
	}
}
