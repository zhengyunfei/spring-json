package org.zlex.json.weixin.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.zlex.json.weixin.config.Config;
import org.zlex.json.weixin.utils.GetAccessTokenUtil;
import org.zlex.json.weixin.utils.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/7/10.
 * 使用方法，需要把菜单的url换成如下
 * url:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb868e44443143338&redirect_uri=域名/oauth/do.html&response_type=code&scope=snsapi_base&state=index1#wechat_redirect
 * 根据state的值确定页面转向
 */
@Controller
public class Oauth2Servlet {

    private static final long serialVersionUID = -644518508267758016L;
    @RequestMapping(value = "/oauth/do.html", method = RequestMethod.GET)
    public ModelAndView oauth(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

         String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=APPID" +
                "&secret=SECRET&" +
                "code=CODE&grant_type=authorization_code";
         String get_userinfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        String page="";
        ModelAndView mv = new ModelAndView();
        try {
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            // xml请求解析
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");
            //判断页面跳转
            String key=request.getParameter("state");
            String appid= Config.APPID;
            String appsecret=Config.SECRET;
            get_access_token_url = get_access_token_url.replace("APPID", appid);
            get_access_token_url = get_access_token_url.replace("SECRET", appsecret);
            get_access_token_url = get_access_token_url.replace("CODE", code);

            String json = HttpUtil.getUrl(get_access_token_url);
            String openid="";
            JSONObject jsonObject = JSONObject.fromObject(json);
            if(jsonObject.has("openid")){
            	 openid = jsonObject.getString("openid");
            }

            if(key.equals("index1")){

               page="";//跳转到index1页面
            }
            if(key.equals("index2")){//洗车工登陆
                page="";//跳转到index2页面
            }
            mv.setViewName(page);
            mv.addObject("openId",openid);
            System.out.println("第一个用户oid========="+openid);
                //用户基本信息
                    String access_token = GetAccessTokenUtil.getAccess_token(Config.APPID, Config.SECRET);
                  get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
                  get_userinfo = get_userinfo.replace("OPENID", openid);
                  String userInfoJson = HttpUtil.getUrl(get_userinfo);
                  JSONObject userInfoJO = JSONObject.fromObject(userInfoJson);

                  if(userInfoJO.has("nickname")){
                      String user_nickname = userInfoJO.getString("nickname");
                      user_nickname=  new String(user_nickname.getBytes("iso-8859-1"),"utf-8");
                      System.out.println("昵称==========================="+user_nickname);
                  }
                  if(userInfoJO.has("headimgurl")){
                      String user_headimgurl = userInfoJO.getString("headimgurl");
                      System.out.println("头像==========================="+user_headimgurl);
                  }
            response.setContentType("text/html; charset=utf-8");

        } catch (Exception e) {
             e.printStackTrace();
        }
        return mv;
    }




}
