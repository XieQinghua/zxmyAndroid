package com.weishi.yiye.bean;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/3
 * @Description：图片上传bean
 * @Version:v1.0.0
 *****************************/
public class ImageUploadBean {

    /**
     * code : 200
     * message : 上传成功！
     * data : ["http://jiujie-pics.oss-cn-beijing.aliyuncs.com/419734ad4871404e967b924118df0a87"]
     */

    private String code;
    private String message;
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
