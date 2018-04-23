package com.weishi.yiye.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.weishi.yiye.R;

import java.net.URISyntaxException;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/3
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class MapPopupWindow extends PopupWindow {
    private static final String TAG = MapPopupWindow.class.getSimpleName();

    public MapPopupWindow(final Context mContext, final String userLat, final String userLng, final String address, View parent) {
        View view = View.inflate(mContext, R.layout.popup_map_navagation, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //修改高度显示，解决被手机底部虚拟键挡住的问题
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //实例化一个ColorDrawable颜色为半透明
        setBackgroundDrawable(new ColorDrawable(0xb0000000));
        //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = view.findViewById(R.id.ll_popup).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        update();

        Button btn_baidu = (Button) view.findViewById(R.id.btn_baidu);
        Button btn_gaode = (Button) view.findViewById(R.id.btn_gaode);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isInstallApp("com.baidu.BaiduMap")) {
                    try {
                        Intent intent = Intent.getIntent("intent://map/direction?" +
                                "destination=latlng:" + userLat + "," + userLng + "|name:" + address +
                                "&mode=driving&" +
                                "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                        mContext.startActivity(intent);
                    } catch (URISyntaxException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {//未安装
                    //market为路径，id为包名
                    //显示手机上所有的market商店
                    Toast.makeText(mContext, "您尚未安装百度地图", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    }
                }

                dismiss();
            }
        });
        btn_gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isInstallApp("com.autonavi.minimap")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);

                    //将功能Scheme以URI的方式传入data
                    Uri uri = Uri.parse("androidamap://route?sourceApplication=appname&dlat=" +
                            userLat + "&dlon=" + userLng + "&dname=" + address + "&dev=0&t=0");
                    intent.setData(uri);

                    //启动该页面即可
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "您尚未安装高德地图", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    }
                }

                dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
