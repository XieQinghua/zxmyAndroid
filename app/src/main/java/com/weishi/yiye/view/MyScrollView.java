package com.weishi.yiye.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by shejun on 2018/3/30.
 */

public class MyScrollView  extends ScrollView {
    /**
     * Runnable延迟执行的时间
     */
   // private long delayMillis = 100;

    /**
     * 上次滑动的时间
     */
   /* private long lastScrollUpdate = -1;
    private int valueY=0;
    private Runnable scrollerTask = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastScrollUpdate) > 100) {
                lastScrollUpdate = -1;
                if(onScrollListener != null) {
                    onScrollListener.onScrollEnd(valueY);
                }
            } else {
                postDelayed(this, delayMillis);
            }
        }
    };*/

    private OnScrollListener onScrollListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }

       /* if (lastScrollUpdate == -1) {
            onScrollStart();
            postDelayed(scrollerTask, delayMillis);
        }
        valueY=t;
        // 更新ScrollView的滑动时间
        lastScrollUpdate = System.currentTimeMillis();*/
    }

    /**
     * 滑动开始
     */
    private void onScrollStart() {
    }


    /**
     *
     * 滚动的回调接口
     *
     * @author xiaanming
     *
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        public void onScroll(int scrollY);

        /**
         * 滑动结束
         */
        public void onScrollEnd(int scrollY);
    }



}