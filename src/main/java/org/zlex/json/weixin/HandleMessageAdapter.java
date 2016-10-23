package org.zlex.json.weixin;

import org.zlex.json.weixin.msg.*;

/**
 * 处理消息适配器(适配器模式)
 * @author marker
 * */
public class HandleMessageAdapter implements HandleMessageListener {


	/**
	 * 收到文本消息
	 *
	 * @param msg
	 */
	public void onTextMsg(Msg4Text msg) {

	}

	/**
	 * 收到图片消息
	 *
	 * @param msg
	 */
	public void onImageMsg(Msg4Image msg) {

	}

	/**
	 * 收到事件推送消息
	 *
	 * @param msg
	 */
	public void onEventMsg(Msg4Event msg) {

	}

	/**
	 * 收到链接消息
	 *
	 * @param msg
	 */
	public void onLinkMsg(Msg4Link msg) {

	}

	/**
	 * 收到地理位置消息
	 *
	 * @param msg
	 */
	public void onLocationMsg(Msg4Location msg) {

	}

	/**
	 * 语音识别消息
	 *
	 * @param msg
	 */
	public void onVoiceMsg(Msg4Voice msg) {

	}

	/**
	 * 错误消息
	 *
	 * @param errorCode
	 */
	public void onErrorMsg(int errorCode) {

	}

	/**
	 * 视频消息
	 *
	 * @param msg
	 */
	public void onVideoMsg(Msg4Video msg) {

	}
}
