package org.zlex.json.weixin.msg;

/**
 * Created by Administrator on 2015/7/17.
 */
public class ImageMessage extends BaseMessage {
   public Image Image;

    public org.zlex.json.weixin.msg.Image getImage() {
        return Image;
    }

    public void setImage(org.zlex.json.weixin.msg.Image image) {
        Image = image;
    }
}
