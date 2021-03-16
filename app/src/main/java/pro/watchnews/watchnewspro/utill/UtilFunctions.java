package pro.watchnews.watchnewspro.utill;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import pro.watchnews.watchnewspro.R;

import java.io.FileDescriptor;

public class UtilFunctions {
	static String LOG_CLASS = "UtilFunctions";

	/**
	 * Check if service is running or not
	 * @param serviceName
	 * @param context
	 * @return
	 */
	public static boolean isServiceRunning(String serviceName, Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for(RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if(serviceName.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Read the songs present in external storage
	 * @param context
	 * @return
	 */
//	public static ArrayList<MediaItem> listOfSongs(Context context){
//		ArrayList<MediaItem> listOfSongs = new ArrayList<MediaItem>();
//		MediaItem songData = new MediaItem();
//		songData.setTitle("Radio teen Taal");
//		songData.setAlbum("Flv");
//		songData.setArtist("http://playerservices.streamtheworld.com/api/livestream-redirect/ONEFM.flv");
//		songData.setDuration(111);
//		songData.setAlbumId(1);
//		songData.setComposer("Teen Taal");
//		songData.setPath("http://playerservices.streamtheworld.com/api/livestream-redirect/ONEFM.flv");
//
//		listOfSongs.add(songData);
//
//
//		MediaItem songData2 = new MediaItem();
//		songData2.setTitle("Apna Pakistan");
//		songData2.setAlbum("Radio");
//		songData2.setArtist("http://58.65.188.191:1089/fata");
//		songData2.setDuration(111);
//		songData2.setAlbumId(1);
//		songData2.setComposer("livestream");
//		songData2.setPath("http://58.65.188.191:1089/fata");
//		listOfSongs.add(songData2);
//
//		return listOfSongs;
//	}
//		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//		Cursor c = context.getContentResolver().query(uri, null, MediaStore.Audio.Media.IS_MUSIC + " != 0", null, null);
//		ArrayList<MediaItem> listOfSongs = new ArrayList<MediaItem>();
//		c.moveToFirst();
//		while(c.moveToNext()){
//			MediaItem songData = new MediaItem();
//			
//			String title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));
//			String artist = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//			String album = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//			long duration = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION));
//			String data = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
//			long albumId = c.getLong(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//			String composer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.COMPOSER));
//			
//			songData.setTitle(title);
//			songData.setAlbum(album);
//			songData.setArtist(artist);
//			songData.setDuration(duration);
//			songData.setPath(data);
//			songData.setAlbumId(albumId);
//			songData.setComposer(composer);
//			listOfSongs.add(songData);
//		}
//		c.close();
//		Log.d("SIZE", "SIZE: " + listOfSongs.size());
//		return listOfSongs;
//	}

	public static Bitmap getAlbumart(Context context,Long album_id){
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    try{
	        final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
	        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
	        ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
	        if (pfd != null){
	            FileDescriptor fd = pfd.getFileDescriptor();
	            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
	            pfd = null;
	            fd = null;
	        }
	    } catch(Error ee){}
	    catch (Exception e) {}
	    return bm;
	}

	/**
	 * @param context
	 * @return
	 */
	public static Bitmap getDefaultAlbumArt(Context context){
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
	    try{
	    	bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.whitelogo, options);
	    } catch(Error ee){}
	    catch (Exception e) {}
	    return bm;
	}
	/**
	 * Convert milliseconds into time hh:mm:ss
	 * @param milliseconds
	 * @return time in String
	 */
	public static String getDuration(long milliseconds) {
		long sec = (milliseconds / 1000) % 60;
		long min = (milliseconds / (60 * 1000))%60;
		long hour = milliseconds / (60 * 60 * 1000);

		String s = (sec < 10) ? "0" + sec : "" + sec;
		String m = (min < 10) ? "0" + min : "" + min;
		String h = "" + hour;
		
		String time = "";
		if(hour > 0) {
			time = h + ":" + m + ":" + s;
		} else {
			time = m + ":" + s;
		}
		return time;
	}
	
	public static boolean currentVersionSupportBigNotification() {
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if(sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
			return true;
		}
		return false;
	}
	
	public static boolean currentVersionSupportLockScreenControls() {
		int sdkVersion = android.os.Build.VERSION.SDK_INT;
		if(sdkVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			return true;
		}
		return false;
	}
}
