package com.tencent.iot.explorer.device.broadcast.impl;

import com.tencent.trtc.TRTCCloudDef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BroadcastCallingDelegate {
    /**
     * sdk内部发生了错误
     * @param code 错误码
     * @param msg 错误消息
     */
    void onError(int code, String msg);

    /**
     * 被邀请通话回调
     * @param sponsor 邀请者
     * @param trtc_uidList 同时还被邀请的人
     * @param isFromGroup 是否IM群组邀请
     * @param callType 邀请类型 1-语音通话，2-视频通话
     */
    void onInvited(String sponsor, List<String> trtc_uidList, boolean isFromGroup, int callType);

    /**
     * 正在IM群组通话时，如果其他与会者邀请他人，会收到此回调
     * 例如 A-B-C 正在IM群组中，A邀请[D、E]进入通话，B、C会收到[D、E]的回调
     * 如果此时 A 再邀请 F 进入群聊，那么B、C会收到[D、E、F]的回调
     * @param trtc_uidList 邀请群组
     */
    void onGroupCallInviteeListUpdate(List<String> trtc_uidList);

    /**
     * 如果有用户同意进入通话，那么会收到此回调
     * @param trtc_uid 进入通话的用户
     */
    void onUserEnter(String trtc_uid);

    /**
     * 如果有用户同意离开通话，那么会收到此回调
     * @param trtc_uid 离开通话的用户
     */
    void onUserLeave(String trtc_uid);

    /**
     * 1. 在C2C通话中，只有发起方会收到拒绝回调
     * 例如 A 邀请 B、C 进入通话，B拒绝，A可以收到该回调，但C不行
     *
     * 2. 在IM群组通话中，所有被邀请人均能收到该回调
     * 例如 A 邀请 B、C 进入通话，B拒绝，A、C均能收到该回调
     * @param trtc_uid 拒绝通话的用户
     */
    void onReject(String trtc_uid);

    /**
     * 1. 在C2C通话中，只有发起方会收到无人应答的回调
     * 例如 A 邀请 B、C 进入通话，B不应答，A可以收到该回调，但C不行
     *
     * 2. 在IM群组通话中，所有被邀请人均能收到该回调
     * 例如 A 邀请 B、C 进入通话，B不应答，A、C均能收到该回调
     * @param trtc_uid
     */
    void onNoResp(String trtc_uid);

    /**
     * 邀请方忙线
     * @param trtc_uid 忙线用户
     */
    void onLineBusy(String trtc_uid);

    /**
     * 作为被邀请方会收到，收到该回调说明本次通话被取消了
     */
    void onCallingCancel();

    /**
     * 作为被邀请方会收到，收到该回调说明本次通话超时未应答
     */
    void onCallingTimeout();

    /**
     * 收到该回调说明本次通话结束了
     */
    void onCallEnd();

    /**
     * 远端用户开启/关闭了摄像头
     * @param trtc_uid 远端用户ID
     * @param isVideoAvailable true:远端用户打开摄像头  false:远端用户关闭摄像头
     */
    void onUserVideoAvailable(String trtc_uid, boolean isVideoAvailable);

    /**
     * 远端用户开启/关闭了麦克风
     * @param trtc_uid 远端用户ID
     * @param isVideoAvailable true:远端用户打开麦克风  false:远端用户关闭麦克风
     */
    void onUserAudioAvailable(String trtc_uid, boolean isVideoAvailable);

    /**
     * 用户说话音量回调
     * @param volumeMap 音量表，根据每个userid可以获取对应的音量大小，音量最小值0，音量最大值100
     */
    void onUserVoiceVolume(Map<String, Integer> volumeMap);


    void onNetworkQuality(TRTCCloudDef.TRTCQuality trtcQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList);

}
