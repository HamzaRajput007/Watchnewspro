package pro.watchnews.watchnewspro.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import pro.watchnews.watchnewspro.MainApplication;

import pro.watchnews.watchnewspro.interfaces.HttpResponseCallback;

import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {

	private static String tag_string_req = "string_req";
	private static HttpResponseCallback httpResponseCallback;
	private static String url = "";

	public static void httpGetRequestWithParam(Context thisContext, final String requestUrl,
                                               final Map<String, String> params, final HttpResponseCallback httpResponseCallback) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String url = "";
		for (Entry<String, String> mapEntry : params.entrySet()) {
			String key = mapEntry.getKey();
			String value = mapEntry.getValue();
			stringBuilder.append(key + "=" + value + "&");
		}
		url = requestUrl + "?" + stringBuilder.toString();
		url = method(url);
		// url =
		StringRequest strReq = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						Log.d("Success", response);
						httpResponseCallback.onCompleteHttpResponse(requestUrl, response);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("Error", error.toString());
						httpResponseCallback.onCompleteHttpResponse(requestUrl, "");
					}
				}) {

//			@Override
//			protected Map<String, String> getParams() {
//				return params;
//			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

			//	Map<String, String> params = new HashMap<String, String>();
				params.put("Cache-Control", "no-cache, no-store, must-revalidate");
				params.put("Pragma", "no-cache");

				return params;
			}

		};
		
		int socketTimeout = 50000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		strReq.setRetryPolicy(policy);

		// Cache cache =
		// MainApplication.getInstance().getRequestQueue().getCache();
		// com.android.volley.Cache.Entry entry = cache.get(url);
		// if(entry != null){
		// try {
		// String data = new String(entry.data, "UTF-8");
		// httpResponseCallback.onCompleteHttpResponse(requestUrl,
		// data);
		// // handle data, like converting it to xml, json, bitmap etc.,
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// }else{
		// Adding request to request queue
		MainApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
		// }
	}

	public static void httpPostRequestWithParam(final String requestUrl,
			final Map<String, String> params,
			final HttpResponseCallback httpResponseCallback) {

		StringRequest strReq = new StringRequest(Method.POST, requestUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("error", response);
						httpResponseCallback.onCompleteHttpResponse(requestUrl,
								response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					//	Log.d("error", error.toString());
						httpResponseCallback.onCompleteHttpResponse(requestUrl,
								error.toString());
					}
				}) {

			@Override
			protected Map<String, String> getParams() {
				return params;
			}
			
			@Override
			public String getBodyContentType() {
				return "application/x-www-form-urlencoded; charset=UTF-8";
			}
		};

		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		strReq.setRetryPolicy(policy);

		MainApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
	}

	public static String method(String str) {
		if (str.length() > 0 && str.charAt(str.length() - 1) == '&') {
			str = str.substring(0, str.length() - 1);
		}

		return str;
	}


}