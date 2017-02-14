package testtask.funda.com.fundatesttask.makelaars;

import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.RealEstateAgent;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;
import testtask.funda.com.fundatesttask.data.source.MakelaarsRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsPresenter implements MakelaarsContract.Presenter {

	private final MakelaarsRepository _makelaarsRepository;

	private final MakelaarsContract.View _makelaarsView;

	public MakelaarsPresenter(@NonNull MakelaarsRepository makelaarsRepository, @NonNull MakelaarsContract.View makelaarsView) {
		_makelaarsRepository = checkNotNull(makelaarsRepository, "makelaarsRepository can't be null");
		_makelaarsView = checkNotNull(makelaarsView, "_makelaarsView cannot be null");

		makelaarsView.setPresenter(this);
	}

	@Override
	public void loadTopRealEstateAgents() {
		loadRealEstateAgents(true);
	}

	private void loadRealEstateAgents(final boolean showLoadingUi) {
		if (showLoadingUi) {
			_makelaarsView.showLoadingUi();
		}

		_makelaarsRepository.loadObjectsForSale(new MakelaarsDataSource.LoadObjectsForSaleCallback() {
			@Override
			public void onObjectsLoaded(List<ObjectForSale> objectsForSale) {
				List<RealEstateAgent> topAgentsToShow = new ArrayList<RealEstateAgent>();

				for (ObjectForSale objectForSale : objectsForSale) {
					// TODO Filter and get top agents
				}

				// The view may not be able to handle UI updates anymore
				if (!_makelaarsView.isActive()) {
					return;
				}

				if (showLoadingUi) {
					_makelaarsView.setLoadingIndicator(false);
				}

				processTopAgents(topAgentsToShow);
			}

			@Override
			public void onDataNotAvailable() {
				// The view may not be able to handle UI updates anymore
				if (!_makelaarsView.isActive()) {
					return;
				}

				_makelaarsView.showLoadingSalesAgentsError();
			}
		});
	}

	private void processTopAgents(List<RealEstateAgent> topAgentsToShow) {
		if (topAgentsToShow.isEmpty()) {
			_makelaarsView.showNoRealEstateAgents();
		} else {
			_makelaarsView.showTopAgents(topAgentsToShow);
		}
	}

	@Override
	public void start() {
		loadTopRealEstateAgents();
	}
}
