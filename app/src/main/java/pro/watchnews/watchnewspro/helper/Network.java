package pro.watchnews.watchnewspro.helper;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**

  @author Salman Ashraf
  
 */
public class Network {
	private static Network instance;


	private Network() {
	}

	public static Network getInstance() {
		if (instance == null) {
			instance = new Network();
		}
		return instance;
	}
 
	public static boolean isNetworkAvailable(Context context) {
		boolean value = false;
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			value = true;
		}
		return value;
	}
 
 
	public static boolean isWiFiAvaiable(Context context) {
		boolean isWifiConnected = false;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (mWifi.isConnected()) {
			isWifiConnected = true;
		}
		return isWifiConnected;
	}

	 
	public static boolean is3GAvaiable(Context context) {
		boolean is3GConnected = false;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo Mobile3G = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (Mobile3G.isConnected())
			is3GConnected = true;
		return is3GConnected;
	}

	 
	public static boolean isGPSEnabled(Context context) {
		boolean GPSenabled = false;
		LocationManager GPSLocation = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (GPSLocation.isProviderEnabled(LocationManager.GPS_PROVIDER))
			GPSenabled = true;
		return GPSenabled;
	}

	public static boolean haveNetworkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			Log.i("Value", "Yes");
			return true;
		}
		Log.i("Value", "No");
		return false;
	}
	
	public static NetworkInfo getNetworkInfo(Context context){
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo();
	}
	public static boolean isConnectedMobile(Context context){
	    NetworkInfo info = Network.getNetworkInfo(context);
	    return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
	}

	/**
	 * Check if there is fast connectivity
	 * @param context
	 * @return
	 */
	public static boolean isConnectedFast(Context context){
	    NetworkInfo info = Network.getNetworkInfo(context);
	    return (info != null && info.isConnected() && Network.isConnectionFast(info.getType(),info.getSubtype()));
	}

	/**
	 * Check if the connection is fast
	 * @param type
	 * @param subType
	 * @return
	 */
	public static boolean isConnectionFast(int type, int subType){
	    if(type== ConnectivityManager.TYPE_WIFI){
	        return true;
	    }else if(type== ConnectivityManager.TYPE_MOBILE){
	        switch(subType){
	        case TelephonyManager.NETWORK_TYPE_1xRTT:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_CDMA:
	            return false; // ~ 14-64 kbps
	        case TelephonyManager.NETWORK_TYPE_EDGE:
	            return false; // ~ 50-100 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_0:
	            return true; // ~ 400-1000 kbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_A:
	            return true; // ~ 600-1400 kbps
	        case TelephonyManager.NETWORK_TYPE_GPRS:
	            return false; // ~ 100 kbps
	        case TelephonyManager.NETWORK_TYPE_HSDPA:
	            return true; // ~ 2-14 Mbps
	        case TelephonyManager.NETWORK_TYPE_HSPA:
	            return true; // ~ 700-1700 kbps
	        case TelephonyManager.NETWORK_TYPE_HSUPA:
	            return true; // ~ 1-23 Mbps
	        case TelephonyManager.NETWORK_TYPE_UMTS:
	            return true; // ~ 400-7000 kbps
	        /*
	         * Above API level 7, make sure to set android:targetSdkVersion 
	         * to appropriate level to use these
	         */
	        case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
	            return true; // ~ 1-2 Mbps
	        case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
	            return true; // ~ 5 Mbps
	        case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
	            return true; // ~ 10-20 Mbps
	        case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
	            return false; // ~25 kbps 
	        case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
	            return true; // ~ 10+ Mbps
	        // Unknown
	        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
	        default:
	            return false;
	        }
	    }else{
	        return false;
	    }
	}
}
