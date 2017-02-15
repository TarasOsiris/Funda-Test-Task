package testtask.funda.com.fundatesttask.data.model;

/**
 * Created by tarasleskiv on 15/02/2017.
 */

public class RealEstateAgentListViewModel {
	private final RealEstateAgent _agent;

	private Integer _numberOfObjectsOwned;

	public RealEstateAgentListViewModel(RealEstateAgent agent) {
		_agent = agent;
		// If agent exists he owns at least one object
		_numberOfObjectsOwned = 1;
	}

	public Integer getNumberOfObjectsOwned() {
		return _numberOfObjectsOwned;
	}

	public void setNumberOfObjectsOwned(Integer numberOfObjectsOwned) {
		_numberOfObjectsOwned = numberOfObjectsOwned;
	}

	public RealEstateAgent getAgent() {
		return _agent;
	}

	@Override
	public int hashCode() {
		int result = _agent != null ? _agent.hashCode() : 0;
		result = 31 * result + _numberOfObjectsOwned;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RealEstateAgentListViewModel that = (RealEstateAgentListViewModel) o;

		if (_numberOfObjectsOwned != that._numberOfObjectsOwned) {
			return false;
		}
		return _agent != null ? _agent.equals(that._agent) : that._agent == null;

	}
}
