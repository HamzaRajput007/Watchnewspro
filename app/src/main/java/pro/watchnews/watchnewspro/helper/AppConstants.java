package pro.watchnews.watchnewspro.helper;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class AppConstants {

	public static final String BASE_URL = "http://app.newslive.com/newslive/api/all_channels";


	public static void printLogMsg(String string) {
		Log.d(AppConstants.class.getName(), string);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static String converttobytes(Bitmap bitmapOrg) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
		byte[] ba = bao.toByteArray();
		String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
		return ba1;
	}

}
