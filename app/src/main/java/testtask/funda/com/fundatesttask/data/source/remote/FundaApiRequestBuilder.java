package testtask.funda.com.fundatesttask.data.source.remote;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Class to easily build string url to request data
 */

public class FundaApiRequestBuilder {

	public static final String TYPE_BUY = "koop";

	private static final String API_KEY = "005e7c1d6f6c4f9bacac16760286e3cd";
	private static final String API_BASE_URL = "http://partnerapi.funda.nl/feeds/Aanbod.svc/json/" + API_KEY;

	private Request.Builder _requestBuilder;
	private HttpUrl.Builder _urlBuilder;

	public FundaApiRequestBuilder() {
		_requestBuilder = new Request.Builder();
		_urlBuilder = new HttpUrl.Builder()
				.scheme("http")
				.host("partnerapi.funda.nl")
				.addPathSegment("feeds")
				.addPathSegment("Aanbod.svc/json")
				.addPathSegment(API_KEY);
	}

	public FundaApiRequestBuilder type(String type) {
		_urlBuilder.addQueryParameter("type", type);
		return this;
	}

	public FundaApiRequestBuilder searchQuery(String query) {
		_urlBuilder.addQueryParameter("zo", query);
		return this;
	}

	public FundaApiRequestBuilder page(int page) {
		_urlBuilder.addQueryParameter("page", Integer.toString(page));
		return this;
	}

	public FundaApiRequestBuilder pageSize(int pageSize) {
		_urlBuilder.addQueryParameter("pageSize", Integer.toString(pageSize));
		return this;
	}

	public Request build() {
		_requestBuilder.url(_urlBuilder.build());
		return _requestBuilder.build();
	}
}
