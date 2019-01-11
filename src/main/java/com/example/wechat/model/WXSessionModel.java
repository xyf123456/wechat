package com.example.wechat.model;
/**
 * @ClassName: com.example.wechat.model.WXSessionModel
 * @Description:  通过凭证进而换取用户登录态信息，包括用户的唯一标识（openid）及本次登录的会话密钥（session_key）等。
 * @Author:      Administrator
 * @CreateDate: 2019/1/11 15:42
 * @UpdateUser:   Administrator 
 * @Version:        1.0
 **/
public class WXSessionModel {

    private String session_key;
    private String openid;

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
