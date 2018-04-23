package com.weishi.yiye.view;


import android.widget.ScrollView;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：监听ScrollView滑动距离接口
 * @Version:v1.0.0
 *****************************/
public interface ScrollChangedListener {
    void onScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt);
}
