package testtask.funda.com.fundatesttask.makelaars;

import android.support.annotation.NonNull;
import testtask.funda.com.fundatesttask.data.model.ObjectForSale;
import testtask.funda.com.fundatesttask.data.model.RealEstateAgentListViewModel;
import testtask.funda.com.fundatesttask.data.source.MakelaarsDataSource;
import testtask.funda.com.fundatesttask.data.source.MakelaarsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

class MakelaarsPresenter implements MakelaarsContract.Presenter {

	private final MakelaarsRepository _makelaarsRepository;

	private final MakelaarsContract.View _makelaarsView;

	MakelaarsPresenter(@NonNull MakelaarsRepository makelaarsRepository, @NonNull MakelaarsContract.View makelaarsView) {
		_makelaarsRepository = checkNotNull(makelaarsRepository, "makelaarsRepository can't be null");
		_makelaarsView = checkNotNull(makelaarsView, "makelaarsView cannot be null");

		makelaarsView.setPresenter(this);
	}

	@Override
	public void start() {
		_makelaarsView.showNoRealEstateAgents();
	}

	@Override
	public void loadTopRealEstateAgents(String city, boolean hasYard) {
		checkNotNull(city);
		loadRealEstateAgents(city, hasYard, true);
	}

	private void loadRealEstateAgents(String city, boolean hasYard, final boolean showLoadingUi) {
		if (showLoadingUi) {
			_makelaarsView.setLoadingProgressBar(true);
		}

		_makelaarsRepository.loadObjectsForSale(city, hasYard, new MakelaarsDataSource.LoadObjectsForSaleCallback() {
			@Override
			public void onObjectsLoaded(List<ObjectForSale> objectsForSale) {
				// The view may not be able to handle UI updates anymore
				if (!_makelaarsView.isActive()) {
					return;
				}

				if (showLoadingUi) {
					_makelaarsView.setLoadingProgressBar(false);
				}

				List<RealEstateAgentListViewModel> topAgents = calculateTopAgents(objectsForSale);
				processTopAgents(topAgents);
			}

			@Override
			public void onProgressChanged(int current, int total) {
				_makelaarsView.setLoadingProgress(current, total);
			}

			@Override
			public void onError() {
				// The view may not be able to handle UI updates anymore
				if (!_makelaarsView.isActive()) {
					return;
				}

				_makelaarsView.showLoadingSalesAgentsError();
			}
		});
	}


	private List<RealEstateAgentListViewModel> calculateTopAgents(List<ObjectForSale> objectsForSale) {
		// Maps real estate agent id to total number of objects
		Map<Integer, RealEstateAgentListViewModel> objectNumbersByMakelaarId = new HashMap<>();

		for (ObjectForSale objectForSale : objectsForSale) {
			// Accumulate objects owned by agent id
			int agentId = objectForSale.getRealEstateAgent().getId();
			if (objectNumbersByMakelaarId.containsKey(agentId)) {
				RealEstateAgentListViewModel current = objectNumbersByMakelaarId.get(agentId);
				int updatedCount = current.getNumberOfObjectsOwned() + 1;
				current.setNumberOfObjectsOwned(updatedCount);
			} else {
				RealEstateAgentListViewModel newAgent = new RealEstateAgentListViewModel(objectForSale.getRealEstateAgent());
				objectNumbersByMakelaarId.put(agentId, newAgent);
			}
		}

		List<RealEstateAgentListViewModel> topAgents = new ArrayList<>();
		topAgents.addAll(objectNumbersByMakelaarId.values());
		sort(topAgents);

		// Return only first 10 as in assignment
		final int numberToDisplay = 10;
		return topAgents.subList(0, Math.min(topAgents.size(), numberToDisplay));
	}

	private void sort(List<RealEstateAgentListViewModel> topAgents) {
		// Sort the list of agents and reverse to get descending
		Collections.sort(topAgents, new Comparator<RealEstateAgentListViewModel>() {
			@Override
			public int compare(RealEstateAgentListViewModel o1, RealEstateAgentListViewModel o2) {
				return o1.getNumberOfObjectsOwned().compareTo(o2.getNumberOfObjectsOwned());
			}
		});
		Collections.reverse(topAgents);
	}

	private void processTopAgents(List<RealEstateAgentListViewModel> topAgentsToShow) {
		if (topAgentsToShow.isEmpty()) {
			_makelaarsView.showNoRealEstateAgents();
		} else {
			_makelaarsView.showTopAgents(topAgentsToShow);
		}
	}
}
