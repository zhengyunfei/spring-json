package org.zlex.json.weixin.utils;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/7/9.
 */
public class GetAccessTokenUtil {
    /**
     * 获得ACCESS_TOKEN
     *
     * @Title: getAccess_token
     * @Description: 获得ACCESS_TOKEN
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String getAccess_token(String appid,String secret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appid+ "&secret=" + secret;
        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = new JSONObject(message);
            accessToken = demoJson.getString("access_token");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
    public static void main(String args[]){
        String token=getAccess_token("wxf9aab63ad5f52164","70e89fdcbfdfccf5e77cdb2dd053c57c");
        System.out.println(token);
    }
}
