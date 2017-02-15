package testtask.funda.com.fundatesttask.makelaars;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import testtask.funda.com.fundatesttask.R;
import testtask.funda.com.fundatesttask.data.model.RealEstateAgent;
import testtask.funda.com.fundatesttask.data.model.RealEstateAgentListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class MakelaarsFragment extends Fragment implements MakelaarsContract.View {

	private MakelaarsContract.Presenter _presenter;

	private MakelaarsAdapter _listAdapter;

	private ProgressBar _loadingIndicator;
	private TextView _noItemsTextView;

	public MakelaarsFragment() {
		// Requires empty public constructor
	}

	public static MakelaarsFragment newInstance() {
		return new MakelaarsFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_listAdapter = new MakelaarsAdapter(new ArrayList<RealEstateAgentListViewModel>(0));
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.makelaars_frag, container, false);

		// Setup list of real estate agents
		ListView listView = (ListView) root.findViewById(R.id.makelaars_list);
		listView.setAdapter(_listAdapter);

		// Loading indicator
		_loadingIndicator = (ProgressBar) root.findViewById(R.id.makelaars_list_loading_indicator);

		_noItemsTextView = (TextView) root.findViewById(R.id.no_makelaars_data_available_text);

		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		_presenter.start();
	}

	// region MakelaarsContract.View implementation

	@Override
	public void setPresenter(MakelaarsContract.Presenter presenter) {
		_presenter = checkNotNull(presenter);
	}

	@Override
	public void setLoadingIndicator(final boolean active) {
		if (getView() == null) {
			return;
		}

		getView().post(new Runnable() {
			@Override
			public void run() {
				int visibility = active ? View.VISIBLE : View.GONE;
				_loadingIndicator.setVisibility(visibility);
			}
		});
	}

	@Override
	public void setLoadingProgress(final int current, final int total) {
		if (getView() == null) {
			return;
		}

		getView().post(new Runnable() {
			@Override
			public void run() {
				_loadingIndicator.setMax(total);
				_loadingIndicator.setProgress(current);
			}
		});
	}

	@Override
	public void showLoadingSalesAgentsError() {
		Snackbar.make(getView(), "Failed loading data", Snackbar.LENGTH_LONG).show();
	}

	@Override
	public boolean isActive() {
		return isAdded();
	}

	@Override
	public void showNoRealEstateAgents() {
		_noItemsTextView.setVisibility(View.VISIBLE);
	}

	@Override
	public void showTopAgents(List<RealEstateAgentListViewModel> topAgentsToShow) {
		_noItemsTextView.setVisibility(View.GONE);
		_listAdapter.replaceData(topAgentsToShow);
	}

	// endregion

	private static class MakelaarsAdapter extends BaseAdapter {

		private List<RealEstateAgentListViewModel> _realEstateAgents;

		MakelaarsAdapter(List<RealEstateAgentListViewModel> realEstateAgents) {
			setList(realEstateAgents);
		}

		void replaceData(List<RealEstateAgentListViewModel> realEstateAgents) {
			setList(realEstateAgents);
			notifyDataSetChanged();
		}

		private void setList(List<RealEstateAgentListViewModel> realEstateAgents) {
			_realEstateAgents = checkNotNull(realEstateAgents);
		}

		@Override
		public int getCount() {
			return _realEstateAgents.size();
		}

		@Override
		public RealEstateAgentListViewModel getItem(int i) {
			return _realEstateAgents.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			View rowView = view;

			if (rowView == null) {
				LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
				rowView = inflater.inflate(R.layout.real_estate_agent_list_item, viewGroup, false);
			}

			RealEstateAgentListViewModel agent = getItem(i);

			TextView titleTV = (TextView) rowView.findViewById(R.id.title);
			titleTV.setText(agent.getAgent().getRealEstateAgentName());

			return rowView;
		}
	}
}
