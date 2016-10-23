package org.zlex.json.weixin.msg;

/**
 * Created by Administrator on 2015/7/16.
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
