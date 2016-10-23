package org.zlex.json.weixin.msg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.zlex.json.weixin.utils.XStreamCDATA;

/**
 * Created by Administrator on 2015/7/17.
 */

public class MediaIdMessage {
    @XStreamAlias("MediaId")
    @XStreamCDATA
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

}
