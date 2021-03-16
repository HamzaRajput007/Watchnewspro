package pro.watchnews.watchnewspro;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import pro.watchnews.watchnewspro.helper.LruBitmapCache;


//@ReportsCrashes(formKey = "", // will not be used
//mailTo = "salmanashraf.12@gmail.com", // my email here
//mode = ReportingInteractionMode.TOAST,
//resToastText = R.string.toast_crash)

public class MainApplication extends Application {
	private static final String APP_ID = "";
	private static final String APP_NAMESPACE = "";

	public static final String TAG = MainApplication.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	
	private static MainApplication mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		//ACRA.init(this);
	}
	public static synchronized MainApplication getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}



}