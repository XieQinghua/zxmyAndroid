package com.weishi.yiye.bean.eventbus;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/4/28
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class RemoveImgEvent {
    private List<LocalMedia> selectList;
    private int removeIndex;

    public RemoveImgEvent(List<LocalMedia> selectList, int removeIndex) {
        this.selectList = selectList;
        this.removeIndex = removeIndex;
    }

    public List<LocalMedia> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<LocalMedia> selectList) {
        this.selectList = selectList;
    }

    public int getRemoveIndex() {
        return removeIndex;
    }

    public void setRemoveIndex(int removeIndex) {
        this.removeIndex = removeIndex;
    }
}
