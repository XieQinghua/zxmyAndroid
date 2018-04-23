package com.weishi.yiye.common;

/**
 * Created by Administrator on 2016/10/11.
 */

public class AdapterInterface {
    public interface AdapterCallBack {
        /**
         *
         * @param position
         *            操作条目的位置
         * @param opertype
         *            自定义操作类型
         */
        public void callBack(int position, String opertype);
    }
}
