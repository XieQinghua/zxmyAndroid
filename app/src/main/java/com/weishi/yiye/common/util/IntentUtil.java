package com.weishi.yiye.common.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Intent跳转类
 */
public class IntentUtil {

    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> cls, int flags) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        activity.startActivity(intent);
    }
    public static void startActivity(Activity activity, Class<?> cls, int flags, String key, String value) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        intent.putExtra(key, value);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> cls, int flags, String key, String value, String key1, String value1) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        intent.putExtra(key, value);
        intent.putExtra(key1, value1);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> cls, int flags, String key, String value, String key1, String value1, String key2, String value2) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        intent.putExtra(key, value);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        activity.startActivity(intent);
    }
    public static void startActivity(Activity activity, Class<?> cls, int flags, String key, String value, String key1, String value1, String key2, String value2, String key3, String value3) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        intent.putExtra(key, value);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        intent.putExtra(key3, value3);
        activity.startActivity(intent);
    }
    public static void startActivity(Activity activity, Class<?> cls, int flags, String key, String value, String key1, String value1, String key2, String value2, String key3, String value3, String key4, String value4, String key5, String value5) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.setFlags(flags);
        intent.putExtra(key, value);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        intent.putExtra(key3, value3);
        intent.putExtra(key4, value4);
        intent.putExtra(key5, value5);
        activity.startActivity(intent);
    }
}
