package com.weishi.yiye.bean.eventbus;

import com.weishi.yiye.bean.SelectShopTypeBean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/5
 * @Description：区域选择事件
 * @Version:v1.0.0
 *****************************/
public class SelectShopTypeEvent {
    public SelectShopTypeBean model; //换成最后地址的model
    public String code;
    public String name;
    public int type = 0;

    public void setModel(SelectShopTypeBean model) {
        this.model = model;
    }
}
