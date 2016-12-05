package com.golove.uitls;

import com.golove.BuildConfig;

/**
 * 日志打印
 */
public class XLog {

    public static void i(String tag, String msg) {
        if (BuildConfig.ISBUG)
            android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.ISBUG)
            android.util.Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.ISBUG)
            android.util.Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.ISBUG)
            android.util.Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.ISBUG)
            android.util.Log.w(tag, msg);
    }

}
