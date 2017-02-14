package testtask.funda.com.fundatesttask.makelaars;

import android.app.Fragment;
import testtask.funda.com.fundatesttask.data.model.RealEstateAgent;

import java.util.List;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsFragment extends Fragment implements MakelaarsContract.View {
	@Override
	public void setLoadingIndicator(boolean active) {
		
	}

	@Override
	public void showLoadingUi() {

	}

	@Override
	public void showLoadingSalesAgentsError() {

	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void showNoRealEstateAgents() {

	}

	@Override
	public void showTopAgents(List<RealEstateAgent> topAgentsToShow) {

	}

	@Override
	public void setPresenter(MakelaarsContract.Presenter presenter) {

	}
}
