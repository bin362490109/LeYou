package com.umeng.soexample.utils;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.soexample.commons.Constants;

import java.util.Map;

/**
 * Created by abin on 2015/10/29.
 */
public abstract class AccountUtils {
    // 整个平台的Controller, 负责管理整个SDK的配置、操作等处理
    public static UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);

    /**
     * 授权。如果授权成功，则获取用户信息</br>
     */
    public static void login(final Activity activity, final SHARE_MEDIA platform, final AccountInfoListener accountInfoListener) {
        mController.doOauthVerify(activity, platform, new SocializeListeners.UMAuthListener() {

            @Override
            public void onStart(SHARE_MEDIA platform) {
                if (platform == SHARE_MEDIA.WEIXIN) {
                    Toast.makeText(activity, "微信暂时不弄", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "start", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(activity, "onComplete", Toast.LENGTH_SHORT).show();
                String uid = value.getString("uid");
                if (!TextUtils.isEmpty(uid)) {
                    getUserInfo(activity, platform,uid, accountInfoListener);
                } else {
                    Toast.makeText(activity, "授权失败...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
            }
        });
    }


    /**
     * 注销本次登录</br>
     */
    public static void logout(final Activity activity, final SHARE_MEDIA platform) {
        mController.deleteOauth(activity, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取授权平台的用户信息</br>
     */
    private static void getUserInfo(final Activity activity, final SHARE_MEDIA platform, final String uid ,final AccountInfoListener accountInfoListener) {
        mController.getPlatformInfo(activity, platform, new SocializeListeners.UMDataListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (info != null) {
                    Toast.makeText(activity, info.toString(), Toast.LENGTH_SHORT).show();
                    if (platform==SHARE_MEDIA.WEIXIN){
                        info.put("shareusertype","1");
                    }else if(platform==SHARE_MEDIA.QQ){
                        info.put("shareusertype","2");
                    }else if (platform==SHARE_MEDIA.SINA){
                        info.put("shareusertype","3");
                    }else{
                        info.put("shareusertype","0");
                    }
                    info.put("shareuserid",uid);
                    accountInfoListener.getAccountInfo(info);
                }
            }
        });
    }

    //QQ免登录分享
    public static void addQZoneQQPlatform(final Activity activity) {
        String appId = "100424468";
        String appKey = "c7394704798a158208a74ab60104f0ba";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    //新浪
    public static void addSinaPlatform() {
        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    //微信
    public static void addUMWXPlatform(final Activity activity) {
        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
// 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appID, appSecret);
        wxHandler.addToSocialSDK();
// 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    public  interface  AccountInfoListener{
           public void getAccountInfo( Map<String, Object> info);
    }
}
