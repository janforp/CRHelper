package com.janita.plugin.demo.replacechinenschar;

import com.intellij.ide.util.PropertiesComponent;

/**
 * 类说明：工具
 *
 * @author zhucj
 * @since 2020/3/8 - 下午1:08
 */
public class CacheUtils {

    public static void setCacheValue(String value) {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(ReplaceChineseCharConstants.CACHE_KEY, value);
    }

    public static String getCacheValue() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        return propertiesComponent.getValue(ReplaceChineseCharConstants.CACHE_KEY, ReplaceChineseCharConstants.DEFAULT_CACHE_VALUE);
    }
}