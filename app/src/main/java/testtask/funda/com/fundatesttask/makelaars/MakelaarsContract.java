package testtask.funda.com.fundatesttask.makelaars;

import testtask.funda.com.fundatesttask.BasePresenter;
import testtask.funda.com.fundatesttask.BaseView;
import testtask.funda.com.fundatesttask.model.RealEstateAgent;

import java.util.List;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public interface MakelaarsContract {
	interface View extends BaseView<Presenter> {
		void setLoadingIndicator(boolean active);

		void showRealEstateAgentsList(List<RealEstateAgent> realEstateAgents);
	}

	interface Presenter extends BasePresenter {
		// Determine which makelaar's in Amsterdam have the most object listed for sale. Make a table of the top 10.
		void loadTopRealEstateAgents();
	}
}