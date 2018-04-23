package com.weishi.yiye.bean.eventbus;

import com.weishi.yiye.bean.SelectAddressBean;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/2/5
 * @Description：区域选择
 * @Version:v1.0.0
 *****************************/
public class SelectAddressEvent {
    public SelectAddressBean model; //换成最后地址的model
    public String code;
    public String name;
    public int type=0;

    public void setModel(SelectAddressBean model) {
        this.model = model;
    }
}
