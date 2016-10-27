package org.zlex.json.weixin.action;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zlex.json.weixin.config.Config;
import org.zlex.json.weixin.msg.*;
import org.zlex.json.weixin.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.zlex.json.weixin.utils.Test.UPLOAD_IMAGE_URL;

/**
 * 请求处理的核心类
 *
 */
@Controller
public class CoreServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * 确认请求来自微信服务器
	 */
	@RequestMapping(value = "/myToken20161022", method = RequestMethod.GET)
	@ResponseBody
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@RequestMapping(value = "/myToken20161022", method = RequestMethod.POST)
	@ResponseBody
	public void doPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String respMessage = "";

		try {
			// 默认返回的文本消息内容
			String respContent = "";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String content = requestMap.get("Content");
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			ImageMessage imageMessage = new ImageMessage();
			imageMessage.setToUserName(fromUserName);
			imageMessage.setFromUserName(toUserName);
			imageMessage.setCreateTime(new Date().getTime());
			imageMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
			//imageMessage.setPicUrl("http://www.pestreet.cn/c/freemarker/upload/img/20150618/20150618142923banner.jpg");
			imageMessage.setMsgId(Long.parseLong(System.currentTimeMillis() + "00"));

			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent += "您发送的是文本消息！";

			}
			// 图片消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent += "您发送的是图片消息！";
				//respContent="";

			}
			// 地理位置消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				//respContent += "您发送的是地理位置消息！";
			}
			// 链接消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				// respContent += "您发送的是链接消息！";
			}
			// 音频消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				//respContent += "您发送的是音频消息！";
			}
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注";
				}

				// 自定义菜单点击事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					System.out.println("eventKey=========================" + eventKey);
					if (eventKey.equals("2")) {
						String access_token = GetAccessTokenUtil.getAccess_token(Config.APPID, Config.SECRET);
						String openId=fromUserName;
						String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
						String userInfoJson = HttpUtil.getUrl(url);
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

						// 将图文消息对象转换成xml字符串
						//File file = new File("f:" + File.separator + "167580248.jpg"); // 获取本地文件
						getUser(request,response);
						org.zlex.json.weixin.utils.Config config = new org.zlex.json.weixin.utils.Config();
						String imagePath = config.getString("imageUrl");
						System.out.println("imageURl=========================" + imagePath);
						File file = new File(imagePath); // 获取本地文件
						String accessToken = GetAccessTokenUtil.getAccess_token(Config.APPID, Config.SECRET);

						String id = Test.uploadImage(UPLOAD_IMAGE_URL, accessToken, "image",
								file);// 上传文件
						System.out.println("获取mediaId==============================" + id);
						Image image = new Image();
						image.setMediaId(id);
						imageMessage.setImage(image);
						respMessage = MessageUtil.imageMessageToXml(imageMessage);
						//respMessage = MessageUtil.textMessageToXml(textMessage);
						System.out.println("respMessage=======" + respMessage);
					}
					if (eventKey.equals("3")) {
						// 将图文消息对象转换成xml字符串
						Article article = new Article();
						//article.setPicUrl(imageMessage.getPicUrl());
						//	article.setUrl(imageMessage.getPicUrl());
						newsMessage.setArticleCount(1);
						List<Article> list = new ArrayList<Article>();
						list.add(article);
						newsMessage.setArticles(list);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						//imageMessage.setMediaId("1234567890123456");
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						//respMessage = MessageUtil.textMessageToXml(textMessage);
						System.out.println("respMessage=======" + respMessage);
					}
				}

			}
			if (!"".equals(respContent) && null != respContent) {
				System.out.println("回复文本消息=====================");

				textMessage.setContent(respContent);

				respMessage = MessageUtil.textMessageToXml(textMessage);
				PrintWriter out = response.getWriter();
				System.out.println("开始================================================" + respMessage);

				out.print(respMessage);
				out.close();
				System.out.println("完毕================================================");
			}
			if (!"".equals(respMessage) && null != respMessage) {
				PrintWriter out = response.getWriter();
				System.out.println("开始================================================" + respMessage);

				out.print(respMessage);
				out.close();
				System.out.println("完毕================================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 响应消息

	}

	/**
	 * 获取用户基本信息
	 * @param request
	 * @param response
     */
	public void getUser(HttpServletRequest request, HttpServletResponse response) {
		String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
				"appid=APPID" +
				"&secret=SECRET&" +
				"code=CODE&grant_type=authorization_code";
		String get_userinfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			// xml请求解析
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String code = request.getParameter("code");
			//判断页面跳转
			String key = request.getParameter("state");
			String appid = Config.APPID;
			String appsecret = Config.SECRET;
			get_access_token_url = get_access_token_url.replace("APPID", appid);
			get_access_token_url = get_access_token_url.replace("SECRET", appsecret);
			get_access_token_url = get_access_token_url.replace("CODE", code);

			String json = HttpUtil.getUrl(get_access_token_url);
			String openid = "";
			JSONObject jsonObject = JSONObject.fromObject(json);
			if (jsonObject.has("openid")) {
				openid = jsonObject.getString("openid");
			}


			System.out.println("第一个用户oid=========" + openid);
			//用户基本信息
			String access_token = GetAccessTokenUtil.getAccess_token(Config.APPID, Config.SECRET);
			get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
			get_userinfo = get_userinfo.replace("OPENID", openid);
			String userInfoJson = HttpUtil.getUrl(get_userinfo);
			JSONObject userInfoJO = JSONObject.fromObject(userInfoJson);
			if (userInfoJO.has("nickname")) {
				String user_nickname = userInfoJO.getString("nickname");
				user_nickname = new String(user_nickname.getBytes("iso-8859-1"), "utf-8");
				System.out.println("昵称===========================" + user_nickname);
			}
			if (userInfoJO.has("headimgurl")) {
				String user_headimgurl = userInfoJO.getString("headimgurl");
				System.out.println("头像===========================" + user_headimgurl);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
