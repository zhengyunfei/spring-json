package org.zlex.json.weixin.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2016/5/27.
 */
public class Config {
    private String propertyFileName;
    private ResourceBundle resourceBundle;
    public Config() {
        propertyFileName = "config";
        resourceBundle = ResourceBundle.getBundle(propertyFileName);
    }
    public String getString(String key) {
        if (key == null || key.equals("") || key.equals("null")) {
            return "";
        }
        String result = "";
        try {
            result = resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return result;
    }
}
