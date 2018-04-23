package com.weishi.yiye.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shejun on 2018/3/30.
 * s首页三级分类
 */

public class ThreeShopTypeBean implements Serializable{
    public String code;
    public String message;
    public List<ThreeShopTypeBean.DataBean> data;

    public static class DataBean  implements Serializable{
        public int id;//": 1,
        public String typeName;//"": "美容",
        public String parentId;//"": 0,
        public String createtime;//"": 1516948113000,
        public String status;//"": 1,
        public String icon;//"": "http://jiujie-pics.oss-cn-beijing.aliyuncs.com/icon_eat_zzc.png",
        public String fatherTypeName;//"": null,
        public String lineColors;//"": "ffc290",
        public String ombre;//"": "c089f3,877bf6",
        public int sortId;//"": 101,
        public List<TwoProperty> property;//""

        public static class TwoProperty  implements Serializable{
            public int id;//": 1,
            public String typeName;//"": "美容",
            public String parentId;//"": 0,
            public String createtime;//"": 1516948113000,
            public String status;//"": 1,
            public String icon;//"": "http://jiujie-pics.oss-cn-beijing.aliyuncs.com/icon_eat_zzc.png",
            public String fatherTypeName;//"": null,
            public String lineColors;//"": "ffc290",
            public String ombre;//"": "c089f3,877bf6",
            public int sortId;//"": 101,
            public List<ThreeProperty> property;//""

            public static class ThreeProperty  implements Serializable{
                public String id;//": 1,
                public String typeName;//"": "美容",
                public String parentId;//"": 0,
                public String createtime;//"": 1516948113000,
                public String status;//"": 1,
                public String icon;//"": "http://jiujie-pics.oss-cn-beijing.aliyuncs.com/icon_eat_zzc.png",
                public String fatherTypeName;//"": null,
                public String lineColors;//"": "ffc290",
                public String ombre;//"": "c089f3,877bf6",
                public int sortId;//"": 101,
                public List<ThreeProperty> property;//""
            }
        }
    }
}
