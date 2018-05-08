package cn.mageek.common.util;

import java.util.Properties;

/**
 * @author Mageek Chiu
 * @date 2018/5/8 0008:13:04
 */
public class PropertyLoader {

    /**
     * 先从环境变量读取属性，没有再从配置文件读取
     * @param pop 文件
     * @param key 属性key
     * @return 属性value
     */
    public static String load(Properties pop, String key)  {
        String value = System.getProperty(key);
        value = (value == null ? pop.getProperty(key) : value);
        return value;
    }

}
