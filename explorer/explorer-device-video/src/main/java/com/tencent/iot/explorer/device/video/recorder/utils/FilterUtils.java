package com.tencent.iot.explorer.device.video.recorder.utils;

import android.content.Context;
import com.example.ivdemo.recorder.opengles.render.base.bean.base.BaseRenderBean;
import com.tencent.iot.explorer.device.video.recorder.opengles.render.RenderConstants;
import com.tencent.iot.explorer.device.video.recorder.opengles.render.filter.BaseFilter;
import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static final List<BaseRenderBean> FILTER_LIST = new ArrayList<>();

    static {
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.NORMAL, "原画"));
//        FILTER_LIST.add(new MeanBlurBean("均值模糊", 2, 30, 1, 1));
//        FILTER_LIST.add(new GaussianBlurBean("高斯模糊", 2, 30));
//        FILTER_LIST.add(new GaussianBlurBean("毛玻璃", 2, 30, 5, 5));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.GRAY, "灰度滤镜"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.PIP, "画中画"));
//        FILTER_LIST.add(new MotionBlurBean(30, 10));
//        FILTER_LIST.add(new ScaleBean("放大", 2));
//        FILTER_LIST.add(new ScaleBean("缩小", 0.5f));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.SKEW, "扭曲"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.BLUE_LINE_CHALLENGE_H, "蓝线挑战（横向）"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.BLUE_LINE_CHALLENGE_V, "蓝线挑战（纵向）"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.RETAIN_FRAME, "保留帧"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.CONVEYOR_BELT_H, "传送带（横向）"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.CONVEYOR_BELT_V, "传送带（纵向）"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.TWO_PART, "两分屏"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.THREE_PART, "三分屏"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.FOUR_PART, "四分屏"));
        FILTER_LIST.add(new BaseRenderBean(RenderConstants.Filter.NINE_PART, "九分屏"));
    }

    public static BaseFilter getFilter(Context context, BaseRenderBean bean) {
        BaseFilter filter;
        switch (bean.getType()) {
//            case RenderConstants.Filter.GAUSSIAN_BLUR:
//                filter = new GaussianBlurFilter(context);
//                break;
//            case RenderConstants.Filter.MEAN_BLUR:
//                filter = new MeanBlurFilter(context);
//                break;
//            case RenderConstants.Filter.GRAY:
//                filter = new GrayFilter(context);
//                break;
//            case RenderConstants.Filter.PIP:
//                filter = new PipFilter(context);
//                break;
//            case RenderConstants.Filter.MOTION_BLUR:
//                filter = new MotionBlurFilter(context);
//                break;
//            case RenderConstants.Filter.SCALE:
//                filter = new ScaleFilter(context);
//                break;
//            case RenderConstants.Filter.SKEW:
//                filter = new SkewFilter(context);
//                break;
//            case RenderConstants.Filter.BLUE_LINE_CHALLENGE_H:
//                filter = new BlueLineChallengeHFilter(context);
//                break;
//            case RenderConstants.Filter.BLUE_LINE_CHALLENGE_V:
//                filter = new BlueLineChallengeVFilter(context);
//                break;
//            case RenderConstants.Filter.RETAIN_FRAME:
//                filter = new RetainFrameFilter(context);
//                break;
//            case RenderConstants.Filter.CONVEYOR_BELT_H:
//                filter = new ConveyorBeltHFilter(context);
//                break;
//            case RenderConstants.Filter.CONVEYOR_BELT_V:
//                filter = new ConveyorBeltVFilter(context);
//                break;
//            case RenderConstants.Filter.TWO_PART:
//                filter = new TwoPartFilter(context);
//                break;
//            case RenderConstants.Filter.THREE_PART:
//                filter = new ThreePartFilter(context);
//                break;
//            case RenderConstants.Filter.FOUR_PART:
//                filter = new FourPartFilter(context);
//                break;
//            case RenderConstants.Filter.NINE_PART:
//                filter = new NinePartFilter(context);
//                break;
            default:
                filter = new BaseFilter(context);
        }
        filter.setRenderBean(bean);
        filter.setBindFbo(true);
        return filter;
    }
}
