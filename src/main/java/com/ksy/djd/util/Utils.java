package com.ksy.djd.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.sky.djd.R;


/**
 * @author Administrator
 */
@SuppressLint("NewApi")
public class Utils {
	private static NLProgressDialog progressDialog;
	private static PopupWindow popupWindow;
	public final static long sdcardCacheMinSize = 1024 * 1024 * 8L; // 8M

	/**
	 * @param anchor
	 * @param view
	 * @param location
	 *            top,right,bottom默认center
	 */
	public static void showPopWindow(View anchor,View view,String location){
		if(popupWindow == null){
			popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			// popupWindow.setFocusable(true); //其他区域消失
			// popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
		}
		int[] locate = new int[2];
		anchor.getLocationOnScreen(locate);

		if("top".equals(location)){
			popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, locate[0], locate[1] - popupWindow.getHeight());
		}else if("right".equals(location)){
			popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, locate[0] + anchor.getWidth(), locate[1]);
		}else if("left".equals(location)){
			popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, locate[0] - popupWindow.getWidth(), locate[1]);
		}else if("bottom".equals(location)){
			popupWindow.showAsDropDown(anchor);
		}else{
			popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}

	public static void dismissPopWindow(){
		if(popupWindow != null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
	}

	/**
	 * 获取手机imsi的卡号类型
	 * 
	 * @param context
	 * @return 0:非中国地区号或者是模拟器号码, 1:移动,2:联通,3:电信
	 */
	public static int getImsiType(Context context){
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId();
		int num = 0;
		if(imsi != null){
			if(imsi.startsWith("46000") || imsi.startsWith("46002")){
				// 中国移动,因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
				num = 2;
			}else if(imsi.startsWith("46001")){
				// 中国联通
				num = 1;
			}else if(imsi.startsWith("46003")){
				// 中国电信
				num = 3;
			}
		}
		return num;
	}

	/**
	 * pix转dip
	 * 
	 * @param context
	 * @param pix
	 * @return
	 */
	public static int pix2dip(Context context,int pix){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return (int) (dm.density * pix);
	}

	/** dp转px */
	public static int dp2pix(Context context,int dip){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return (int) (dip / (dm.density));
	}

	public static boolean SDisExist(){
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}

	// 新建一个文件夹
	public static File newFolder(String folderPath){

		if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			System.out.println("sd卡不存在");
		}else{
			File myFilePath = null;
			try{
				String filePath = folderPath;
				myFilePath = new File(filePath);
				if(!myFilePath.exists()){
					myFilePath.mkdir();
				}
			}catch(Exception e){
				System.out.println("新建文件夹操作出错");
				e.printStackTrace();
			}

			return myFilePath;
		}
		return null;
	}

	// static ProgressDialog progressDialog;

	/**
	 * @param activity
	 * @return
	 */
	public static String getCachePath(Activity activity){
		File fileDir = activity.getCacheDir();

		return fileDir.getParent();
	}

	/**
	 * @param activity
	 * @return
	 */
	public static String getLocalPath(Activity activity){

		File fileDir = activity.getFilesDir();

		return fileDir.getParent();
	}

	public static void copyFile(File targetFile,File file){
		if(targetFile.exists()){
			System.out.println("文件" + targetFile.getAbsolutePath() + "已经存在，跳过该文件！");
			return;
		}else{
			createFile(targetFile, true);
		}
		System.out.println("复制文件" + file.getAbsolutePath() + "到" + targetFile.getAbsolutePath());
		try{
			InputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while(is.read(buffer) != -1){
				fos.write(buffer);
			}
			is.close();
			fos.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void createFile(File file,boolean isFile){
		if(!file.exists()){
			if(!file.getParentFile().exists()){
				createFile(file.getParentFile(), false);
			}else{
				if(isFile){
					try{
						file.createNewFile();
					}catch(IOException e){
						e.printStackTrace();
					}
				}else{
					file.mkdir();
				}
			}
		}
	}

	// 删除文件夹（包括该文件下的所有文件和文件夹）
	public static void deleteFile(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else if(file.isDirectory()){
				File files[] = file.listFiles();
				for(int i = 0;i < files.length;i++){
					deleteFile(files[i]);
				}
			}
			file.delete();
		}else{
			System.out.println("所删除的文件不存在！" + '\n');
		}
	}

	public static int computeSampleSize(BitmapFactory.Options options,

	int minSideLength,int maxNumOfPixels){

		int initialSize = computeInitialSampleSize(options, minSideLength,

		maxNumOfPixels);

		int roundedSize;

		if(initialSize <= 8){

			roundedSize = 1;

			while(roundedSize < initialSize){

				roundedSize <<= 1;

			}

		}else{

			roundedSize = (initialSize + 7) / 8 * 8;

		}

		return roundedSize;

	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,

	int minSideLength,int maxNumOfPixels){

		double w = options.outWidth;

		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 :

		(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 :

		(int) Math.min(Math.floor(w / minSideLength),

		Math.floor(h / minSideLength));

		if(upperBound < lowerBound){

			// return the larger one when there is no overlapping zone.

			return lowerBound;

		}

		if((maxNumOfPixels == -1) &&

		(minSideLength == -1)){

			return 1;

		}else if(minSideLength == -1){

			return lowerBound;

		}else{

			return upperBound;

		}

	}

	/**
	 * @param activity
	 * @param phoneNum
	 */
	public static void sms(Activity activity,String phoneNum){
		Uri uri = Uri.parse("smsto:" + phoneNum);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		// it.putExtra("sms_body", "102");
		activity.startActivity(it);
	}

	/**
	 * @param activity
	 * @param phoneNum
	 */
	public static void call(Activity activity,String phoneNum){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));

		activity.startActivity(intent);
	}

	public static void email(Context cntext,String emailAddress){
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		String[] recipients = new String[] { emailAddress };
		emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
		cntext.startActivity(emailIntent);
	}

	/**
	 * 通过类名跳转
	 * 
	 * @param context
	 * @param className
	 */
	public static void startActivityForClassName(Context context,String className){
		try{
			context.startActivity(new Intent(context, Class.forName(className)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 关闭程序
	 * 
	 * @param activity
	 */
	public static void restartPackage(Activity activity){
		// ActivityManager activityMgr = (ActivityManager) activity
		// .getSystemService(activity.ACTIVITY_SERVICE);
		//
		// activityMgr.restartPackage(activity.getPackageName());
		activity.finish();
		System.exit(0);
	}

	/**
	 * 取得号码
	 * 
	 * @param activity
	 * @return
	 */
	public static String getMobile(Activity activity){
		TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 隐藏标题
	 * 
	 * @param activity
	 */
	public static void hideTitle(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * 全屏
	 * 
	 * @param activity
	 */
	public static void fullScreen(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * @param activity
	 * @param content
	 */
	public static void showToast(Activity activity,String content){
		// Toast.makeText(activity, content, Toast.LENGTH_SHORT).show();
		showToast_1(activity, content);
	}

	public static void showToast(Context context,String content){
		// Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
		showToast_1(context, content);
	}

	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	private static void showToast_1(Context context,String s){
		if(toast == null){
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		}else{
			twoTime = System.currentTimeMillis();
			if(s.equals(oldMsg)){
				if(twoTime - oneTime > Toast.LENGTH_SHORT){
					toast.show();
				}
			}else{
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	/**
	 * @param context
	 * @param title
	 * @param msg
	 */

	/**
	 * @param activity
	 * @param name
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Activity activity,String name){
		return activity.getSharedPreferences(name, 0);
	}

	public static void killProcess(){
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void a(Activity activity){

	}

	/**
	 * 处理单位/部门选择 返回单位/部门名字字符串
	 * */
	public static String listToDepartment(List<Map<String, String>> list){
		String tmp = "";
		if(list.size() != 0){
			for(int i = 0;i < list.size();i++){
				tmp += list.get(i).get("depName") + ",";
			}
			return tmp.substring(0, tmp.lastIndexOf(","));
		}
		return tmp;
	}

	/**
	 * 处理单位/部门选择 返回单位/部门ID字符串
	 * */
	public static String listToDepartmentIds(List<Map<String, String>> list){
		String tmp = "";
		if(list.size() != 0){
			for(int i = 0;i < list.size();i++){
				tmp += list.get(i).get("depId") + ",";
			}
			return tmp.substring(0, tmp.lastIndexOf(","));
		}
		return tmp;
	}

	private static boolean isShowLog = false;

	/**
	 * 设置调试信息开关
	 * */
	public static void setupLog(boolean isOpen){
		isShowLog = isOpen;
	}

	public static void showLog(String tag,String content){
		if(isShowLog){
			Log.d(tag, content);
		}
	}

	public static String fromServer(String string){
		if(string != null){
			return string.replace("\r\n", "\n");
		}
		return "";
	}

	/**
	 * 保存 SharedPreferences中的value
	 * 
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 */
	public static void putSPValue(Context context,String spName,String key,int value){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		sp.edit().putInt(key, value).commit();
	}

	public static int getSPInt(Context context,String spName,String key){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		int s = sp.getInt(key, 0);// 如果该字段没对应值，则取出字符串0
		return s;
	}

	public static void putSPValue(Context context,String spName,String key,String value){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		sp.edit().putString(key, value).commit();
	}

	public static String getSPString(Context context,String spName,String key){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		String s = sp.getString(key, "");// 如果该字段没对应值，则取出字符串""
		return s;
	}

	public static void putSPValue(Context context,String spName,String key,boolean value){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	public static boolean getSPBoolean(Context context,String spName,String key){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		boolean s = sp.getBoolean(key, false);// 如果该字段没对应值，则取出字符串""
		return s;
	}

	public static void putSPValue(Context context,String spName,String key,long value){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		sp.edit().putLong(key, value).commit();
	}

	public static long getSPLong(Context context,String spName,String key){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		long s = sp.getLong(key, 0);// 如果该字段没对应值，则取出字符串""
		return s;
	}

	public static boolean putSPClean(Context context,String spName){
		SharedPreferences sp = (SharedPreferences) context.getSharedPreferences(spName, 0);
		// sp.edit().clear();
		return sp.edit().clear().commit();
	}

	public static boolean hasNetwork(Context context){
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conn.getActiveNetworkInfo();
		if(net != null && net.isConnected()){
			return true;
		}
		return false;
	}

	/**
	 * 根据URL获取Bitmap
	 * */
	public static Bitmap getNetBitmap(String imgUrl){
		Bitmap bitmap = null;
		try{
			URL imageUrl = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return bitmap;
	}

	// /**
	// * 根据路径获取本地bitmap
	// * */
	// public static Bitmap getLocalBitmap(String path) {
	// Bitmap bitmap = null;
	// try {
	//
	// // InputStream is = new FileInputStream(new File(path));
	// // bitmap = BitmapFactory.decodeStream(is);
	// // is.close();
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// InputStream is = new FileInputStream(new File(path));
	// BitmapFactory.decodeStream(is, null, options);
	// int height = options.outHeight * 48 / options.outWidth;
	// options.outWidth = 48;
	// options.outHeight = height;
	//
	// options.inJustDecodeBounds = false;
	// options.inPreferredConfig = Bitmap.Config.ARGB_4444;
	// options.inSampleSize = 4;
	// bitmap = BitmapFactory.decodeStream(is,
	// null, options);
	// is.close();
	// return bitmap;
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return bitmap;
	// }

	/**
	 * 根据文件处理图片
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getBitmapFormFile(String filePath){
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);
		newOpts.inPreferredConfig = Config.RGB_565;
		newOpts.inJustDecodeBounds = false;
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;
		// 现在主流手机比较多是854*480分辨率，所以高和宽我们设置为
		float reqHeight = 854f;
		float reqWidth = 480f;
		int inSampleSize = 1;
		if(width > height && width > reqWidth){
			// 如果宽度大的话根据宽度固定大小缩放
			inSampleSize = (int) (newOpts.outWidth / reqWidth);
		}else if(width < height && height > reqHeight){
			// 如果高度高的话根据高度固定大小缩放
			inSampleSize = (int) (newOpts.outHeight / reqHeight);
		}
		if(inSampleSize <= 0)
			inSampleSize = 1;
		newOpts.inSampleSize = inSampleSize;
		bitmap = BitmapFactory.decodeFile(filePath, newOpts);
		return compressBitmap(bitmap);
	}

	/**
	 * 压缩图片
	 * 
	 * @param bitmap
	 * @return
	 */
	private static Bitmap compressBitmap(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 90;
		while(baos.toByteArray().length / 1024 > 60){
			baos.reset();
			options -= 10;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap mBitmap = BitmapFactory.decodeStream(isBm, null, null);
		return mBitmap;
	}

	/**
	 * 将bitmap保存到SD中
	 * 
	 * @param bitmap
	 * @param folderPath
	 *            格式如 "/sdcard/nenglong"
	 * @param bitName
	 */
	public static void saveBitmapToSD(Bitmap bitmap,String folderPath,String bitName){

		if(!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			System.out.println("sd卡不存在");

		}else{
			File myFilePath = null;
			try{
				String filePath = folderPath;
				myFilePath = new File(filePath);
				if(!myFilePath.exists()){
					myFilePath.mkdir();
				}
			}catch(Exception e){
				System.out.println("新建文件夹操作出错");
				e.printStackTrace();
			}
		}
		File file = new File(folderPath + "/" + bitName);
		FileOutputStream os = null;
		try{
			file.createNewFile();
			os = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			os.flush();
			os.close();

		}catch(IOException e1){
			e1.printStackTrace();
		}
	}

	/**
	 * 图片圆角处理
	 * 
	 * @param bitmap
	 *            源图像
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap,int round){
		if(bitmap == null)
			return null;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, round, round, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static void imageRecycled(Bitmap bitmap){
		if(bitmap != null && !bitmap.isRecycled())
			bitmap.recycle();
		bitmap = null;
	}

	
	private static LayoutInflater inflater = null;

	public static LayoutInflater getLayoutInflater(Context context){
		if(inflater == null){
			inflater = LayoutInflater.from(context);
		}
		return inflater;
	}

	private static SimpleDateFormat format = null;

	public static SimpleDateFormat getSimpleDateFormat(){
		if(format == null){
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		return format;
	}

	public static SimpleDateFormat getSimpleDateFormat(String type){
		if(format == null){
			format = new SimpleDateFormat(type);
		}
		return format;
	}

	/*
	 * 采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过 appInfo.publicSourceDir =
	 * apkPath;来修正这个问题，详情参见:
	 * http://code.google.com/p/android/issues/detail?id=9151
	 */
	public static Drawable getApkIcon(Context context,String apkPath){
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if(info != null){
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try{
				return pm.getApplicationIcon(appInfo);
				// return appInfo.loadIcon(pm);
			}catch(OutOfMemoryError e){
				Log.e("ApkIconLoader", e.toString());
			}
		}
		return null;
	}

	// ------------------------------------------------
	public interface OnPositiveClick {
		public void onClick(DialogInterface dialog,int which);
	}

	public interface OnNegativeClick {
		public void onClick(DialogInterface dialog,int which);
	}


	// UI线程中运行
	public static void runOnUiThread(Runnable run){
		new Handler(Looper.getMainLooper()).post(run);
	}

	public static void runOnUiThread(Runnable run,long delayMillis){
		if(delayMillis < 100)
			runOnUiThread(run);
		else
			new Handler(Looper.getMainLooper()).postDelayed(run, delayMillis);
	}

	/**
	 * 是否为4.0以上
	 */
	@SuppressLint("NewApi")
	public static boolean isSDK4_0_More(){
		return android.os.Build.VERSION.SDK_INT > 13;
	}

	/**
	 * 防止控件被重复点击
	 * 
	 * @param distance
	 *            间隔 默认500毫秒
	 * @return
	 */
	private static long lastClickTime = System.currentTimeMillis();

	public static boolean isFastDoubleClick(int distance){
		long time = System.currentTimeMillis();
		long timeD = ((long) time - lastClickTime);
		if(0 < timeD && timeD < (long) distance){
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 直接调用短信接口发短信
	 * 
	 * @param phone
	 *            手机号
	 * @param msg
	 *            消息
	 * @param bReceiver
	 *            广播
	 */
	@SuppressLint("NewApi")
	public static void sendSMS(Context context,String phone,String msg,BroadcastReceiver bReceiver){
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);

		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);

		SmsManager smsManager = SmsManager.getDefault();
		List<String> divideContents = smsManager.divideMessage(msg);
		for(String text:divideContents){
			smsManager.sendTextMessage(phone, null, text, sentPI, deliverPI);
		}
		// 短信发送后的状态
		if(bReceiver != null){
			context.registerReceiver(bReceiver, new IntentFilter(SENT_SMS_ACTION));
		}
	}
	
	/**
	 * 获取对话框
	 * @param context
	 * @return
	 */
	public static Dialog getDialog(final Context context, final int layoutId, final Runnable runYes, final Runnable runNo) {
		final Dialog dialog = new Dialog(context, R.style.pop_dialog_choose);
		dialog.setContentView(layoutId);
		dialog.setCanceledOnTouchOutside(true);
		
		if(dialog.findViewById(R.id.btn_yes) != null){
			dialog.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(runYes!=null)
						runYes.run();
					dialog.dismiss();
				}
			});
		}
		
		if(dialog.findViewById(R.id.btn_no) != null){
			dialog.findViewById(R.id.btn_no).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(runNo!=null)
						runNo.run();
					dialog.dismiss();
				}
			});
		}
		
		return dialog;
	}
    
	/**
	 * 获取对话框
	 * @param context
	 * @return
	 */
	public static Dialog getDialog(final Context context, final Runnable runYes, final Runnable runNo) {
		return getDialog(context, R.layout.pop_confirem, runYes, runNo);
	}
	
	public static Dialog getContainerDialog(final Context context, final Runnable runYes, final Runnable runNo) {
		return getDialog(context, R.layout.pop_confirem_2, runYes, runNo);
	}
    
	/**
	 * 显示删除的确认框对话框
	 * @param context
	 * @return
	 */
	public static void deleteConfiremDialog(final Context context, Runnable runYes, Runnable runNo) {
		deleteConfiremDialog(context, null, runYes, runNo);
	}
    
	/**
	 * 显示删除的确认框对话框
	 * @param context
	 * @return
	 */
	public static void deleteConfiremDialog(final Context context, String content, Runnable runYes, Runnable runNo) {
		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", "您确定要删除吗？");
		map.put("btn_yes", "删除");
		map.put("btn_no", "取消");
		if(!TextUtils.isEmpty(content))
			map.put("content", content);
		confiremDialog(context, runYes, runNo, map);
	}

	/**
	 * 显示确认框对话框
	 * @param version
	 */
	public static void confiremDialog(final Context context, final Runnable runYes, final Runnable runNo, final HashMap<String, String> map) {
		getConfiremDialog(context, runYes, runNo, map).show();
	}

	/**
	 * 获取确认框对话框
	 * @param version
	 */
	public static Dialog getConfiremDialog(final Context context, final Runnable runYes, final Runnable runNo, final HashMap<String, String> map) {
		final Dialog dialog = getDialog(context, runYes, runNo);
		if(!TextUtils.isEmpty(map.get("title")))
			((TextView)(dialog.findViewById(R.id.tv_title))).setText(map.get("title"));
		
		if(!TextUtils.isEmpty(map.get("btn_yes")))
			((TextView)(dialog.findViewById(R.id.btn_yes))).setText(map.get("btn_yes"));
		
		if(!TextUtils.isEmpty(map.get("btn_no")))
			((TextView)(dialog.findViewById(R.id.btn_no))).setText(map.get("btn_no"));
		
		
		
		return dialog;
	}
	
	public static void showProgressDialog(final Activity context, final String title, final String msg) {
		showProgressDialog(context, title, msg, true);
	}

	/**
	 * @param context
	 * @param title
	 * @param msg
	 */
	public static void showProgressDialog(final Activity context, final String title, final String msg, final boolean cancelable) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (progressDialog != null && progressDialog.isShowing() == true) {
						progressDialog.setMsg(msg);
						return;
					}
					
					progressDialog = NLProgressDialog.createDialog(context, msg, cancelable);
					
					// 解决4.0以后，触摸屏幕就消失了问题
					if(isSDK4_0_More())
						progressDialog.setCanceledOnTouchOutside(false);
					
					progressDialog.show();
				} catch (Exception e) {
					Log.e("Utils", e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * @param context
	 * @param title
	 * @param msg
	 */
	public static void showSubmitProgressDialog(final Activity context) {
		showProgressDialog(context, "请等候"," 提交数据中...");
	}

	/**
	 * @param context
	 * @param title
	 * @param msg
	 */
	public static void showProgressDialog(Activity context, int titleId,
			int msgId) {

		showProgressDialog(context, context.getString(titleId),
				context.getString(msgId));
	}

	/**
	 * 
	 */
	public static void dismissProgressDialog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (progressDialog != null)
						progressDialog.dismiss();
				} catch (Exception e) {
					Log.e("Utils", e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * 
	 */
	public static void dismissProgressDialog(long delayedTime) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (progressDialog != null)
						progressDialog.dismiss();
				} catch (Exception e) {
					Log.e("Utils", e.getMessage(), e);
				}
			}
		}, delayedTime);
	}

	public static void showProgressDialog(Activity context){
		showProgressDialog(context, true);
	}

	public static void showProgressDialog(Activity context, long cancelableTime){
		showProgressDialog(context, "", cancelableTime);
	}

	public static void showProgressDialog(Activity context, String msg, long cancelableTime){
		showProgressDialog(context, "", msg, false);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (progressDialog != null)
						progressDialog.setCancelable(true);
				} catch (Exception e) {
					Log.e("Utils", e.getMessage(), e);
				}
			}
		}, cancelableTime);
	}

	public static void showProgressDialog(Activity context, boolean cancelable){
		showProgressDialog(context, "", "", cancelable);
	}
}
