package com.weishi.yiye.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.qbw.customview.RefreshLoadMoreLayout;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/3/22
 * @Description：自定义下拉刷新控件，解决与轮播图控件冲突
 * @Version:v1.0.0
 *****************************/
public class CusRefreshLoadMoreLayout extends RefreshLoadMoreLayout {
    public CusRefreshLoadMoreLayout(Context context) {
        super(context);
        initGesture();
    }

    public CusRefreshLoadMoreLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGesture();
    }

    private GestureDetector detector;
    private boolean mIsDisallowIntercept = false;

    private void initGesture() {
        detector = new GestureDetector(getContext(), gestureListener);
    }

    private boolean mIsHorizontalMode = false;
    private boolean isFirst = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            isFirst = true;
            mIsHorizontalMode = false;
            mIsDisallowIntercept = false;
            return super.dispatchTouchEvent(e);
        }
        if (detector.onTouchEvent(e) && mIsDisallowIntercept && mIsHorizontalMode) {
            return super.dispatchTouchEvent(e);
        }
        if (mIsHorizontalMode) {
            return super.dispatchTouchEvent(e);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float disX, disY;
            if (distanceX < 0) {
                disX = -distanceX;
            } else {
                disX = distanceX;
            }
            if (distanceY < 0) {
                disY = -distanceY;
            } else {
                disY = distanceY;
            }

            if (disX > disY) {
                if (isFirst) {
                    mIsHorizontalMode = true;
                    isFirst = false;
                }
            } else {
                if (isFirst) {
                    mIsHorizontalMode = false;
                    isFirst = false;
                }
                //垂直滑动会返回false
                return false;
            }
            //水平滑动会返回true
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
}
