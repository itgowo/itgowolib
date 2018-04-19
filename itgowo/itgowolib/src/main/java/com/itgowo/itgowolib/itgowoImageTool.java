package com.itgowo.itgowolib;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lujianchao on 2017/12/12.
 * http://itgowo.com
 * https://github.com/hnsugar
 * QQ:1264957104
 * 不依赖第三方库也可以正常开发自己的库，使用这个工具即可，引用这个库的库不用担心给别人引入过多三方库，这个工具可以自动查找项目内使用的对应的库，直接使用主项目的即可。
 */

public class itgowoImageTool {
    static {
        ImageTool.searchImageTool();
    }

    /**
     * 因为Glide改版方法等有些变化，不保证兼容所有版本的Glide，请先测试下
     *
     * @param mActivityFragmentView
     * @param url
     * @param mImageView
     */
    @Deprecated
    public void bindView(Object mActivityFragmentView, String url, ImageView mImageView) {
        ImageTool.bindImage(mActivityFragmentView, url, mImageView);
    }

    private static class ImageTool {
        public static final String WARNING_JSON = "not found Glide,not found Picasso,not found Fresco,请在主工程中使用Glide、Picasso或者Fresco库，不然无法处理图片加载事件，此工具内部不集成任何第三方，绿色无公害(ˇˍˇ) 想～";

        public static class GlideObject {
            public static Class sGlideClass;
            public static Class sRequestManager;
            public static Class sRequestBuilder;
            public static Method sWithActivity;
            public static Method sWithFragmentActivity;
            public static Method sWithFragmentSupport;
            public static Method sWithFragment;
            public static Method sWithContext;
            public static Method sWithView;
            public static Method sLoadString;
            public static Method sIntoImageView;
        }

        /**
         * 用反射检查APP内集成的图片工具（只实现了Glide）。
         *
         * @return 是否找到并初始化
         */
        protected static boolean searchImageTool() {
            try {
                GlideObject.sGlideClass = Class.forName("com.bumptech.glide.Glide");
                GlideObject.sRequestManager = Class.forName("com.bumptech.glide.RequestManager");
                GlideObject.sLoadString = GlideObject.sRequestManager.getMethod("load", String.class);
                GlideObject.sRequestBuilder = Class.forName("com.bumptech.glide.RequestBuilder");
                GlideObject.sIntoImageView = GlideObject.sRequestBuilder.getMethod("into", ImageView.class);

                GlideObject.sWithActivity = GlideObject.sGlideClass.getMethod("with", Activity.class);
                GlideObject.sWithFragmentActivity = GlideObject.sGlideClass.getMethod("with", FragmentActivity.class);
                GlideObject.sWithFragmentSupport = GlideObject.sGlideClass.getMethod("with", Fragment.class);
                GlideObject.sWithFragment = GlideObject.sGlideClass.getMethod("with", android.app.Fragment.class);
                GlideObject.sWithContext = GlideObject.sGlideClass.getMethod("with", Context.class);
                GlideObject.sWithView = GlideObject.sGlideClass.getMethod("with", View.class);
            } catch (ClassNotFoundException mE) {
                mE.printStackTrace();
            } catch (NoSuchMethodException mE) {
                mE.printStackTrace();
            }
            return true;
        }

        /**
         * 对象转换成Json文本
         *
         * @param mActivityFragmentView
         * @return
         */
        protected static void bindImage(Object mActivityFragmentView, String url, ImageView mImageView) {
            try {
                if (mActivityFragmentView instanceof Activity) {
                    Object mO = GlideObject.sWithActivity.invoke(null, (Activity) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                } else if (mActivityFragmentView instanceof FragmentActivity) {
                    Object mO = GlideObject.sWithFragmentActivity.invoke(null, (FragmentActivity) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                } else if (mActivityFragmentView instanceof Fragment) {
                    Object mO = GlideObject.sWithFragmentSupport.invoke(null, (Fragment) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                } else if (mActivityFragmentView instanceof android.app.Fragment) {
                    Object mO = GlideObject.sWithFragment.invoke(null, (android.app.Fragment) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                } else if (mActivityFragmentView instanceof View) {
                    Object mO = GlideObject.sWithView.invoke(null, (View) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                } else {
                    Object mO = GlideObject.sWithContext.invoke(null, (Context) mActivityFragmentView);
                    mO = GlideObject.sLoadString.invoke(mO, url);
                    GlideObject.sIntoImageView.invoke(mO, mImageView);
                }
            } catch (IllegalAccessException mE) {
                mE.printStackTrace();
            } catch (InvocationTargetException mE) {
                mE.printStackTrace();
            }
        }
    }
}
