package com.tencent.qcloud.tuikit.tuipusher.demo;

import android.net.Uri;
import android.text.TextUtils;

import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuikit.tuipusher.demo.debug.GenerateTestUserSig;

import java.io.File;

/**
 * MLVB 移动直播地址生成
 * 详情请参考：「https://cloud.tencent.com/document/product/454/7915」
 * <p>
 * <p>
 * Generating Streaming URLs
 * See [https://cloud.tencent.com/document/product/454/7915].
 */
public class URLUtils {

    public static final String WEBRTC      = "webrtc://";
    public static final String RTMP        = "rtmp://";
    public static final String HTTP        = "http://";
    public static final String TRTC        = "trtc://";
    public static final String TRTC_DOMAIN = "cloud.tencent.com";
    public static final String APP_NAME    = "live";

    /**
     * 生成推流地址
     * Generating Publishing URLs
     *
     * @param streamId
     * @param type
     * @return
     */
    public static String generatePushUrl(String streamId, PushType type) {
        String pushUrl = "";
        if (type == PushType.RTC) {
            pushUrl = TRTC + TRTC_DOMAIN + "/push/" + streamId + "?sdkappid=" + TUILogin.getSdkAppId() + "&userid=" + TUILogin.getUserId() + "&usersig=" + TUILogin.getUserSig();
        } else if (type == PushType.RTMP) {
            pushUrl = RTMP + GenerateTestUserSig.PUSH_DOMAIN + File.separator + APP_NAME + File.separator + streamId + GenerateTestUserSig.getSafeUrl(streamId);
        }
        return pushUrl;
    }

    /**
     * 生成拉流地址
     * Generating Playback URLs
     *
     * @param streamId
     * @param type
     * @return
     */
    public static String generatePlayUrl(String streamId, PlayType type) {
        String playUrl = "";
        if (type == PlayType.RTC) {
            playUrl = "trtc://cloud.tencent.com/play/" + streamId + "?sdkappid=" + TUILogin.getSdkAppId() + "&userid=" + TUILogin.getUserId() + "&usersig=" + TUILogin.getUserSig();
        } else if (type == PlayType.RTMP) {
            playUrl = HTTP + GenerateTestUserSig.PLAY_DOMAIN + File.separator + APP_NAME + File.separator + streamId + ".flv";
        } else if (type == PlayType.WEBRTC) {
            playUrl = WEBRTC + GenerateTestUserSig.PLAY_DOMAIN + File.separator + APP_NAME + File.separator + streamId;
        }
        return playUrl;
    }

    /**
     * 获取URL中的 streamId 字段
     *
     * @param url
     * @return
     */
    public static String getStreamIdByPushUrl(String url) {
        Uri uri = Uri.parse(url);
        if (uri == null) {
            return "";
        }
        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        if (!path.contains("/")) {
            return "";
        }
        String[] pathArray = path.split("/");
        if (pathArray != null && pathArray.length > 0) {
            return pathArray[pathArray.length - 1];
        }
        return "";
    }

    public static boolean checkPushURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri pushURL = Uri.parse(url);

        return true;
    }

    public enum PushType {
        RTC,
        RTMP
    }


    public enum PlayType {
        RTC,
        RTMP,
        WEBRTC
    }
}
