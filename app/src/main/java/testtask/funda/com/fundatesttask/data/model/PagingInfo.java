package testtask.funda.com.fundatesttask.data.model;

/**
 * Created by tarasleskiv on 14/02/2017.
 */

import com.google.gson.annotations.SerializedName;

/**
 * Represents teh paging information for the response
 */
public class PagingInfo {
	@SerializedName("AantalPaginas")
	private int _totalPages;

	@SerializedName("HuidigePagina")
	private int _currentPage;

	@SerializedName("VolgendeUrl")
	private String _nextUrl;

	public String getPreviousUrl() {
		return _previousUrl;
	}

	public String getNextUrl() {
		return _nextUrl;
	}

	public int getCurrentPage() {
		return _currentPage;
	}

	public int getTotalPages() {
		return _totalPages;
	}

	@SerializedName("VorigeUrl")
	private String _previousUrl;
}
