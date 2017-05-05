package com.yangkai.androiduikj.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.yangkai.androiduikj.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTools {

	/**
	 * 获取当前程序的版本号
	 */
	public static int getVersionCode() {
		Context mContext = BaseApplication.getInstance();
		int versionCode = 0;
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo ;
		try {
			packInfo = packageManager.getPackageInfo(mContext.getPackageName(),
					0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	// 获取ApiKey
	public static String getMetaValue(String metaKey) {
		if (TextUtils.isEmpty(metaKey)) {
			return null;
		}
		ApplicationInfo ai;
		try {
			Context mContext = BaseApplication.getInstance();
			ai = mContext.getPackageManager().getApplicationInfo(
					mContext.getPackageName(), PackageManager.GET_META_DATA);

		} catch (NameNotFoundException e) {
			ai = null;
		}
		String apiKey = null;
		if (null != ai) {
			Bundle metaData = ai.metaData;
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		}
		return apiKey;
	}

	public static String doubleToString(double s) {
		DecimalFormat ss = new DecimalFormat("##0.00");
		return ss.format(s);
	}


	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;

	}

	/**
	 * 半角转为全角,可以避免由于占位导致的排版混乱问题了。
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static boolean isLegalFormat(String str) {
		Pattern p = Pattern.compile("/^[0-9A-Za-z]{6,32}$/");
		// Pattern p =
		// Pattern.compile("/^(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,}$/");

		return p.matcher(str).find();
	}

	/**
	 * 判断传入的字符串是否是IP地址字符串,格式如: 10.10.10.10
	 * 
	 * @author gzs
	 * @param ipStr
	 * @return
	 */
	public static boolean isIPAddress(String ipStr) {
		if (UtilTools.isEmpty(ipStr)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^(0|1\\d{0,2}|2([0-4]\\d)?|25[0-5]|\\d{1,2})\\.(0|1\\d{0,2}|2([0-4]\\d)?|25[0-5]|\\d{1,2})\\.(0|1\\d{0,2}|2([0-4]\\d)?|25[0-5]|\\d{1,2})\\.(0|1\\d{0,2}|2([0-4]\\d)?|25[0-5]|\\d{1,2})$");
		return p.matcher(ipStr).find();
	}

	/**
	 * 判断传入的字符串是否是全部数字字符串
	 * 
	 * @param numStr
	 * @return
	 */
	public static boolean isDigitsOnly(String numStr) {
		if (isEmpty(numStr)) {
			return false;
		}
		for (int i = 0; i < numStr.length(); i++) {
			if (!Character.isDigit(numStr.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否是非空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	/**
	 * 判断str是否为null、空串或者只有空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isWhiteSpaceOnly(String str) {
		return isEmpty(str) || str.trim().length() == 0;
	}

	/**
	 * 判断当前Wfif数据网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiDataEnable(Context context) {
		return ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
	}

	/**
	 * 比较两个字符串大小
	 * 
	 * @param str1
	 * @param str2
	 * @return 如果str1小于str2则返回-1，如果两个字符串相等则返回0，如果str1大于str2则返回1
	 */
	public static int compare(String str1, String str2) {
		if (str1 == null && str2 == null) {
			return 0;
		}

		if (str1 == null)
			return -1;

		if (str2 == null)
			return 1;

		int cmpResult = str1.compareTo(str2);
		return cmpResult == 0 ? 0 : (cmpResult > 0 ? 1 : -1);
	}

	/**
	 * 去除字符串中的所有空白字符
	 * 
	 * @param str
	 *            待处理字符串
	 * @return
	 */
	public static String delSpaces(String str) {
		if (isEmpty(str)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			if (Character.isSpaceChar(sb.charAt(i))) {
				sb.deleteCharAt(i);
				i--;
			}
		}
		return sb.toString();
	}

	/**
	 * 判断给定的字符串是否是手机号码
	 * 
	 * @deprecated
	 * @param pNumber
	 * @return
	 */
	@Deprecated
	public static boolean isMPhoneNum2(String pNumber) {
		// 合法的手机号码是11位且首字母为1
		if (isEmpty(pNumber) || pNumber.length() != 11
				|| pNumber.charAt(0) != '1')
			return false;
		// 如果传入字符串不是纯数字字符串则返回false
		return isDigitsOnly(pNumber);
	}

	/**
	 * 获得当前应用的名称
	 * 
	 * @return
	 */
	public static String getApplicationName(Context context) {
		ApplicationInfo applicationInfo;
		try {
			PackageManager packageManager = context.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
			return (String) packageManager.getApplicationLabel(applicationInfo);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 打开APK程序
	 * 
	 * @param file
	 */
	public static void openApk(Context context, Uri data) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(data, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	/**
	 * 初始化下载路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean initDownPath(String path) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			return file.exists() || file.mkdirs();
		}
		return false;
	}

	/**
	 * 去除字符串中的标点符号、空白字符、括弧（包括中文及英文括弧）、单/双引号、反斜线（\）等
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String clearPunct(String srcStr) {
		return clearAssignedChars(srcStr, "[\"\\?!:'？。\\s，！!；;()（）\\[\\]]");
	}

	/**
	 * 在源字符串src中清除，clearChars中含有的字符，clearChars支持正则表达式方式
	 * 
	 * @param src
	 *            待清理的源字符串
	 * @param clearChars
	 *            需要被清除的字符数组
	 * @return
	 */
	public static String clearAssignedChars(String src, String clearChars) {
		if (UtilTools.isEmpty(src) || UtilTools.isEmpty(clearChars)) {
			return "";
		}
		if (!clearChars.startsWith("[")) {
			clearChars = "[" + clearChars;
		}
		if (!clearChars.endsWith("]")) {
			clearChars += "]";
		}
		Pattern p = Pattern.compile(clearChars);// 增加对应的标点
		Matcher m = p.matcher(src);
		return m.replaceAll("");
	}

	/**
	 * 格式化组装字符串
	 * 
	 * @param formatStr
	 * @param formatArgs
	 * @example sprintf("aaaa%sbbbb%sss","ccc",123) = "aaaacccbbbb123ss"
	 * @return
	 */
	public static String sprintf(String formatStr, Object... formatArgs) {
		String tmpStr = formatStr;
		Pattern p = Pattern.compile("%s");
		Matcher m ;
		for (int i = 0; i < formatArgs.length; i++) {
			m = p.matcher(tmpStr);
			tmpStr = m.replaceFirst(formatArgs[i].toString());
		}

		return tmpStr;
	}

	/**
	 * 将异常消息对象转换成字符串信息
	 * 
	 * @param ex
	 * @return
	 */
	public static String throwableToString(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		return writer.toString();
	}

	/**
	 * 字符编码转换
	 * 
	 * @param str
	 *            需转换的字符
	 * @param newCharset
	 *            目标编码
	 * @return 如字符为空，或字符编码不支持返回null，否则返回新编码的字符
	 */
	public static String changeCharset(String str, String newCharset) {
		if (str != null) {
			try {
				// 用默认的字符编码解码字符串
				byte[] bs = str.getBytes();
				// 用新的字符编码生成字符串
				return new String(bs, newCharset);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
		return null;
	}

	// 校验Tag Alias只能是数字,英文字母和中文
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	// 取得版本号
	public static String getVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}


	/**
	 * 获取IMEI 手机唯一标识
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId(); // 本机唯一标识;
	}

	/**
	 * 获取SIM 卡唯一标识
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);//
		String str = tm.getSubscriberId();
		return str;
	}

	/**
	 * 邮箱验证
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "^[a-zA-Z0-9]*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	/**
	 * 手机号码验证
	 * 
	 * @param
	 * @return
	 */
	public static boolean isMPhoneNum(String strPhoneNum) {
		Pattern p = Pattern.compile("1[0-9]{10}");
		Matcher m = p.matcher(strPhoneNum);
		return m.matches();
	}

	/**
	 * 身份证号码验证
	 * 
	 * @param
	 * @return
	 */
	public static boolean isIdentity(String strIdentity) {
		Pattern p = Pattern
				.compile("^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$");
		Matcher m = p.matcher(strIdentity);
		if (m.matches()) {
			return m.matches();
		} else {// 非闰年
			p = Pattern
					.compile("^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-8]))[0-9]{3}[0-9Xx]$");
			m = p.matcher(strIdentity);
			return m.matches();
		}
	}

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 *            上下文
	 * @param onlyKillMy
	 *            true，表示只结束当前进程；false 表示结束所有相关进程。
	 */
	public static void exit(Context context, boolean onlyKillMy) {
		if (!onlyKillMy) {
			ActivityManager manager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			manager.killBackgroundProcesses("com.hj.hca.frame");
			manager.killBackgroundProcesses(context.getPackageName());
		}
		Process.killProcess(Process.myPid());
	}

	/**
	 * 关闭软键盘（当EidtText无焦点（focusable=false）时阻止输入法弹出） 等同方法：
	 * getWindow().setSoftInputMode
	 * (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	 * 
	 * @param context
	 * @param edt
	 */
	public static void foldInput(Context context, View edt) {
		/**
		 * hideSoftInputFromWindow(IBinder, int)中的Flag标志: HIDE_IMPLICIT_ONLY 1
		 * 表示如果用户未显式地显示软键盘窗口，则隐藏窗口。 HIDE_NOT_ALWAYS 2
		 * 表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
		 * 
		 */
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(edt.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘 boolean showSoftInput(View view, int flags, ResultReceiver
	 * resultReceiver) boolean showSoftInput(View view, int flags) void
	 * showSoftInputFromInputMethod(IBinder token, int flags)
	 * 
	 * @param context
	 * @param edt
	 *            视图
	 */
	public static void showInput(Context context, View edt) {
		/**
		 * showSoftInput(View, int)标志: SHOW_FORCED 2 (0x00000002)
		 * 表示用户强制打开输入法（如长按菜单键），一直保持打开直至只有显式关闭。 　　 SHOW_IMPLICIT 1 (0x00000001)
		 * 表示隐式显示输入窗口，非用户直接要求。窗口可能不显示。
		 */
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
				edt, 0);
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @param format
	 * @param quality
	 * @return
	 */
	public static Bitmap comprocess(Bitmap image, CompressFormat format,
			int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩成功
		if (image.compress(format, quality, baos)) {
			if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
				baos.reset();// 重置baos即清空baos
				image.compress(format, quality / 2, baos);// 这里按原先压缩的50%，把压缩后的数据存放到baos中
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(
					baos.toByteArray());
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
			newOpts.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(isBm, null, newOpts);
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
			float hh = 800f;// 这里设置高度为800f
			float ww = 480f;// 这里设置宽度为480f
			// 缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
			int be = 1;// be=1表示不缩放
			if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;// 设置缩放比例
			newOpts.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
			// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			isBm = new ByteArrayInputStream(baos.toByteArray());
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
			try {
				baos.close();
				isBm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;// 压缩好比例大小后再进行质量压缩
		}
		return image;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = BaseApplication.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue) {
		final float scale = BaseApplication.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = BaseApplication.getInstance().getResources()
				.getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = BaseApplication.getInstance().getResources()
				.getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
