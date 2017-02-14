package testtask.funda.com.fundatesttask.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

public class QueryResponse {

	@SerializedName("Objects")
	private List<ObjectForSale> _objectsForSale;

	@SerializedName("Paging")
	private PagingInfo _pagingInfo;

	@SerializedName("TotaalAantalObjecten")
	private int _totalObjectsForSale;

	private QueryResponse() {
	}

	public List<ObjectForSale> getObjectsForSale() {
		return _objectsForSale;
	}

	public PagingInfo getPagingInfo() {
		return _pagingInfo;
	}

	public int getTotalObjectsForSale() {
		return _totalObjectsForSale;
	}
}
