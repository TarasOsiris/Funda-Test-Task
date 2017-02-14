package testtask.funda.com.fundatesttask.data.model;

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
