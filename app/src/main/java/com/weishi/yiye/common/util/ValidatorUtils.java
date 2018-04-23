package com.weishi.yiye.common.util;

import com.weishi.yiye.common.StringConstants;

import java.lang.reflect.Array;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.Map;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/15
 * @Description：用于校验的公共基础工具类
 * @Version:v1.0.0
 *****************************/
public class ValidatorUtils
{

    /**
     * 校验是否为空字符串
     *
     * @param validateStr
     *            待校验的字符串
     * @return boolean
     */
    public static boolean isEmptyString(String validateStr)
    {
        return null == validateStr || StringConstants.STR_EMPTY.equals(validateStr);
    }

    /**
     * 校验是否不为空字符串
     *
     * @param validateStr
     *            待校验的字符串
     * @return boolean
     */
    public static boolean isNotEmptyString(String validateStr)
    {
        return !isEmptyString(validateStr);
    }

    /**
     * 校验: 为空对象或者空字符串
     *
     * @param validateObj
     *            待校验的对象
     * @return boolean
     */
    public static boolean isEmptyObjectOrString(Object validateObj)
    {
        return null == validateObj || StringConstants.STR_EMPTY.equals(validateObj.toString().trim());
    }

    /**
     * 校验: 不为为空对象或者空字符串
     *
     * @param validateObj
     *            待校验的对象
     * @return boolean
     */
    public static boolean isNotEmptyObjectOrString(Object validateObj)
    {
        return !isEmptyObjectOrString(validateObj);
    }

    /**
     * 校验是否为空collection
     *
     * @param validateColl
     *            待校验的collection
     * @return boolean
     */
    public static boolean isEmptyCollection(Collection<?> validateColl)
    {
        return null == validateColl || validateColl.size() == 0;
    }

    /**
     * 校验是否不为空collection
     *
     * @param validateColl
     *            待校验的collection
     * @return boolean
     */
    public static boolean isNotEmptyCollection(Collection<?> validateColl)
    {
        return !isEmptyCollection(validateColl);
    }

    /**
     * 校验：为空Map
     *
     * @param validateMap
     *            待校验的Map
     * @return boolean
     */
    public static boolean isEmptyMap(Map<?, ?> validateMap)
    {
        return null == validateMap || 0 == validateMap.entrySet().size();
    }

    /**
     * 校验: 不为空Map
     *
     * @param validateMap
     *            待校验的Map
     * @return boolean
     */
    public static boolean isNotEmptyMap(Map<?, ?> validateMap)
    {
        return !isEmptyMap(validateMap);
    }

    /**
     * 判断任何对象的数组是否为空数组
     *
     * @param arrayObj
     *            　数组对象
     * @return　boolean
     */
    public static boolean isEmptyArray(Object arrayObj)
    {
        return null == arrayObj || Array.getLength(arrayObj) == 0;
    }

    /**
     * 判断任何对象的数组是否不为空数组
     *
     * @param arrayObj
     *            　数组对象
     * @return　boolean
     */
    public static boolean isNotEmptyArray(Object arrayObj)
    {
        return !isEmptyArray(arrayObj);
    }

}