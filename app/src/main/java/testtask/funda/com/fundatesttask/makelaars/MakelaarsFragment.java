package testtask.funda.com.fundatesttask.makelaars;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
		_listAdapter = new MakelaarsAdapter(new ArrayList<RealEstateAgent>(0));
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
	public void setLoadingProgress(int progress) {

	}

	@Override
	public void showLoadingSalesAgentsError() {
		// TODO Show error
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
	public void showTopAgents(List<RealEstateAgent> topAgentsToShow) {
		_noItemsTextView.setVisibility(View.GONE);
		_listAdapter.replaceData(topAgentsToShow);
	}

	// endregion

	private static class MakelaarsAdapter extends BaseAdapter {

		private List<RealEstateAgent> _realEstateAgents;

		MakelaarsAdapter(List<RealEstateAgent> realEstateAgents) {
			setList(realEstateAgents);
		}

		void replaceData(List<RealEstateAgent> realEstateAgents) {
			setList(realEstateAgents);
			notifyDataSetChanged();
		}

		private void setList(List<RealEstateAgent> realEstateAgents) {
			_realEstateAgents = checkNotNull(realEstateAgents);
		}

		@Override
		public int getCount() {
			return _realEstateAgents.size();
		}

		@Override
		public RealEstateAgent getItem(int i) {
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

			RealEstateAgent agent = getItem(i);

			TextView titleTV = (TextView) rowView.findViewById(R.id.title);
			titleTV.setText(agent.getRealEstateAgentName());

			return rowView;
		}
	}
}
