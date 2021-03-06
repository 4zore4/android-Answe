package com.example.answer.http.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	private static final String TAG = Util.class.getSimpleName();



	/**
	 * ??????IMEI???????????????????????????
	 * @param context
	 * @return
	 */
	public static String getImei(Context context) {
		String result = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (null != tm) {
//				result = tm.getDeviceId();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ??????build serial??????????????????
	 * @param context
	 * @return
	 */
	public static String getBuildSerial(Context context) {
		String result = null;
		try {
			result = Build.SERIAL;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * ANDROID_ID???Android???????????????????????????????????????64bit???16BYTES??????
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String result = "";
		try {
			result = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * ???????????????????????????
	 */
	public static String getAppVersionName(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * ????????????
	 *
	 * @return
	 */
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 * ????????????
	 *
	 * @return
	 */
	public static String getPhoneModel() {
		return Build.MODEL;
	}

	/**
	 * ???????????????????????????
	 */
	public static String getAppVersionCode(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			return String.valueOf(pi.versionCode);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * ?????????????????????
	 */
	public static String getAppLabel(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			return String.valueOf(pm.getApplicationLabel(context.getApplicationInfo()));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}




	/**
	 * ??????????????????
	 *
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		try {
			if (null == context || null == context.getApplicationContext()) {
				return false;
			}
			ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(
					Context.CONNECTIVITY_SERVICE);
			if (manager == null) {
				return false;
			}
			NetworkInfo networkinfo = manager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {
				return false;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return true;
	}





	/**
	 * ??????????????????
	 * NET_NO???????????????
	 * NET_2G:2g??????
	 * NET_3G???3g??????
	 * NET_4G???4g??????
	 * NET_WIFI???wifi
	 * NET_UNKNOWN???????????????
	 */
	public interface NetState{
		Integer NET_NO = 0;
		Integer NET_WIFI = 1;
		Integer NET_2G = 2;
		Integer NET_3G = 3;
		Integer NET_4G = 4;
		Integer NET_UNKNOWN = 5;}

	/**
	 * ??????????????????????????????
	 * @param context
	 * @return ?????????
	 */
	public static int isConnected(Context context) {
		int stateCode = NetState.NET_NO;
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null && ni.isConnectedOrConnecting()) {
				switch (ni.getType()) {
					case ConnectivityManager.TYPE_WIFI:
						stateCode = NetState.NET_WIFI;
						break;
					case ConnectivityManager.TYPE_MOBILE:
						switch (ni.getSubtype()) {
							case TelephonyManager.NETWORK_TYPE_GPRS: //??????2g
							case TelephonyManager.NETWORK_TYPE_CDMA: //??????2g
							case TelephonyManager.NETWORK_TYPE_EDGE: //??????2g
							case TelephonyManager.NETWORK_TYPE_1xRTT:
							case TelephonyManager. NETWORK_TYPE_IDEN:
								stateCode = NetState.NET_2G;
								break;
							case TelephonyManager.NETWORK_TYPE_EVDO_A: //??????3g
							case TelephonyManager.NETWORK_TYPE_UMTS:
							case TelephonyManager.NETWORK_TYPE_EVDO_0:
							case TelephonyManager.NETWORK_TYPE_HSDPA:
							case TelephonyManager.NETWORK_TYPE_HSUPA:
							case TelephonyManager.NETWORK_TYPE_HSPA:
							case TelephonyManager.NETWORK_TYPE_EVDO_B:
							case TelephonyManager.NETWORK_TYPE_EHRPD:
							case TelephonyManager.NETWORK_TYPE_HSPAP:
								stateCode = NetState.NET_3G;
								break;
							case TelephonyManager.NETWORK_TYPE_LTE:
								stateCode = NetState.NET_4G;
								break;
							default:
								stateCode = NetState.NET_UNKNOWN;
						}
						break;
					default:
						stateCode = NetState.NET_UNKNOWN;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return stateCode;
	}

    public static boolean isNotWifi(Context context) {
        int netState = isConnected(context);
        return NetState.NET_2G.equals(netState)
                || NetState.NET_3G.equals(netState)
                || NetState.NET_4G.equals(netState);

    }


	public static String getOSVersionString(){
		return Build.VERSION.RELEASE;
	}


}