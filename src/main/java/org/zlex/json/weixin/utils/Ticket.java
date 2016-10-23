package org.zlex.json.weixin.utils;
/**
 * Created by Administrator on 2015/12/15.
 */

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Ticket {
    public static String getTicket(String access_token) {
        String ticket = null;
        try {
            //String access_token = tokenThread.getToken(); //有效期为7200秒
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
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
            ticket = demoJson.getString("ticket");
            //System.out.println(ticket);
            is.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }

        return ticket;
    }
}
