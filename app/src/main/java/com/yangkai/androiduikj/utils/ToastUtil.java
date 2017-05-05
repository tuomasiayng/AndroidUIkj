/**
 * 
 */
package com.yangkai.androiduikj.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.yangkai.androiduikj.base.BaseApplication;


public class ToastUtil {

    private static Toast toast;
    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     * @see #
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     * @see #
     */
    public static final int LENGTH_LONG = 1;

    public static Toast makeText(Context context, CharSequence text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    text,
                    duration);
        } else {
            toast.setText(text);
        }
        return toast;
    }

    public static Toast makeText( Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    text,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        return toast;
    }

    public static Toast makeText( String content, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(),
                    content,
                    duration);
        } else {
            toast.setText(content);
        }
        return toast;
    }

    public static Toast makeText( String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        return toast;
    }

    public static Toast makeText(Context context, @StringRes int resId, int duration)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }
    public static void show(Context context, CharSequence text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    text,
                    duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show( Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    text,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void show( String content, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(),
                    content,
                    duration);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void show( String content) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
