package org.zlex.json.weixin.utils;

/**
 * Created by Administrator on 2016/10/23.
 */
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.HttpStatus;
import org.zlex.json.weixin.config.Config;

import java.io.File;

public class Test
{
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access
    public static final String UPLOAD_IMAGE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";// 上传多媒体文件
    public static final String APP_ID = "wxa549b28c24cf341e";
    public static final String SECRET = "78d8a8cd7a4fa700142d06b96bf44a37";

    /**
     * 上传多媒体文件
     *
     * @param url
     *            访问url
     * @param access_token
     *            access_token
     * @param type
     *            文件类型
     * @param file
     *            文件对象
     * @return
     */
    public static String uploadImage(String url, String access_token,
                                     String type, File file)
    {
        org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();
        String uploadurl = String.format("%s?access_token=%s&type=%s", url,
                access_token, type);
        PostMethod post = new PostMethod(uploadurl);
        post
                .setRequestHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Host", "file.api.weixin.qq.com");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        String result = null;
        try
        {
            if (file != null && file.exists())
            {
                FilePart filepart = new FilePart("media", file, "image/jpeg",
                        "UTF-8");
                Part[] parts = new Part[] { filepart };
                MultipartRequestEntity entity = new MultipartRequestEntity(
                        parts,

                        post.getParams());
                post.setRequestEntity(entity);
                int status = client.executeMethod(post);
                if (status == HttpStatus.SC_OK)
                {
                    String responseContent = post.getResponseBodyAsString();
                    JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
                    JsonObject json = jsonparer.parse(responseContent)
                            .getAsJsonObject();
                    if (json.get("errcode") == null)// {"errcode":40004,"errmsg":"invalid media type"}
                    { // 上传成功  {"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
                        result = json.get("media_id").getAsString();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return result;
        }
    }

    public static void main(String[] args) throws Exception
    {
        String accessToken = GetAccessTokenUtil.getAccess_token(Config.APPID, Config.SECRET);

        if (accessToken != null)// token成功获取
        {
            System.out.println(accessToken);
            org.zlex.json.weixin.utils.Config config=new org.zlex.json.weixin.utils.Config();
            String localPath=config.getString("imageUrl");
            System.out.println("localPath========="+localPath);
           // File file = new File("f:" + File.separator + "167580248.jpg"); // 获取本地文件
           // String id = uploadImage(UPLOAD_IMAGE_URL, accessToken, "image",
               //     file);// 上传文件
           // if (id != null)
               // System.out.println(id);
        }
    }

}
