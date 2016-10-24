package org.zlex.json.weixin.menu;

/**
 * Created by Administrator on 2016/10/22.
 */

import net.sf.json.JSONObject;
import org.zlex.json.weixin.config.Config;
import org.zlex.json.weixin.token.WeixinUtil;
import org.zlex.json.weixin.utils.GetAccessTokenUtil;
import org.zlex.json.weixin.utils.MenuUtil;

public class CreateMenu {
    // 菜单创建（POST） 限100（次/天）
    public static String MENU_CREATE = MenuUtil.URL_MENU_CREATE;
    public static String appId= Config.APPID;
    public static String appSecret=Config.SECRET;
    public static String token = GetAccessTokenUtil.getAccess_token(appId, appSecret);
    public String CreateMenu(String jsonMenu) {

        String resultStr = "";
        // 调用接口获取token
        System.out.println("Token========="+token);
        if (token != null) {
            // 调用接口创建菜单
            int result = createMenu(jsonMenu, token);
            // 判断菜单创建结果
            if (0 == result) {
                resultStr = "菜单创建成功";
            } else {
                resultStr = "菜单创建失败，错误码：" + result;
            }
            System.out.println(resultStr);
        }

        return resultStr;
    }


    /**
     * 创建菜单
     *
     * @param jsonMenu
     *            菜单的json格式
     * @param accessToken
     *            有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(String jsonMenu, String accessToken) {

        int result = 0;
        // 拼装创建菜单的url
        String url = MENU_CREATE+"?access_token="+accessToken;
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
            }
        }

        return result;
    }
    public static int deleteMenu(String jsonMenu) {

        int result = 0;
        // 拼装创建菜单的url
        String url = MenuUtil.URL_MENU_DELETE+"?access_token="+token;
        // 调用接口创建菜单
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
            }
        }

        return result;
    }
    public static void main(String[] args) {
        // 这是一个符合菜单的json格式，“\”是转义符
        String jsonMenu = "{\"button\":[" +
                "{\"name\":\"助手服务\",\"sub_button\":[" +
                "{\"name\":\"发送图片\",\"key\":\"2\",\"type\":\"click\"}" +
                "]}]}";
        System.out.println(jsonMenu);
        CreateMenu impl = new CreateMenu();
        // impl.deleteMenu(jsonMenu);
        impl.CreateMenu(jsonMenu);
    }
}
