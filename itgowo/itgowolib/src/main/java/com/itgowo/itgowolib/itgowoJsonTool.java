package com.itgowo.itgowolib;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by lujianchao on 2017/12/12.
 * http://itgowo.com
 * https://github.com/hnsugar
 * QQ:1264957104
 * 不依赖第三方库也可以正常开发自己的库，使用这个工具即可，引用这个库的库不用担心给别人引入过多三方库，这个工具可以自动查找项目内使用的对应的库，直接使用主项目的即可。
 */

public class itgowoJsonTool {
    static {
        JsonTool.searchJsonTool();
    }

    public String ObjectToJson(Object mO) {
        return JsonTool.ObjectToJson(mO);
    }

    public <T> T JsonToObject(String mJsonString, Class<T> mClass) {
        return JsonTool.JsonToObject(mJsonString, mClass);
    }

    public <T> T JsonToObject(String mJsonString, Type mType) {
        return JsonTool.JsonToObject(mJsonString, mType);
    }

    private static class JsonTool {
        public static final String WARNING_JSON = "not found fastjson,not found Gson, 请在主工程中使用fastjson或者Gson库，不然无法处理Json，此工具内部不集成任何第三方，绿色无公害(ˇˍˇ) 想～";
        private static Class mFastJson = null;
        private static Object mGsonJson = null;
        private static boolean isFastJson = true;
        private static Method mJsonMethodToJsonString = null;
        private static Method mJsonMethodToJsonObjectClass = null;
        private static Method mJsonMethodToJsonObjectType = null;
        private static Object mFeatures;

        /**
         * 用反射检查APP内集成的Json工具。
         *
         * @return 是否找到并初始化
         */
        protected static boolean searchJsonTool() {
            try {
                mFastJson = Class.forName("com.alibaba.fastjson.JSON");
                isFastJson = true;
                mJsonMethodToJsonString = mFastJson.getMethod("toJSONString", Object.class);
                mJsonMethodToJsonObjectClass = mFastJson.getMethod("parseObject", String.class, Class.class);
                Class mFeatureClass = Class.forName("com.alibaba.fastjson.parser.Feature");
                mFeatures = Array.newInstance(mFeatureClass, 1);
                Array.set(mFeatures, 0, mFeatureClass.getEnumConstants()[0]);
                mJsonMethodToJsonObjectType = mFastJson.getMethod("parseObject", String.class, Type.class, Array.newInstance(mFeatureClass, 1).getClass());
            } catch (ClassNotFoundException mE) {
                mE.printStackTrace();
            } catch (NoSuchMethodException mE) {
                mE.printStackTrace();
            }
            if (mFastJson == null || mJsonMethodToJsonString == null || mJsonMethodToJsonObjectClass == null || mJsonMethodToJsonObjectType == null) {
                isFastJson = false;
                try {
                    Class mGsonClass = Class.forName("com.google.gson.Gson");
                    mGsonJson = mGsonClass.newInstance();
                    mJsonMethodToJsonObjectClass = mGsonClass.getDeclaredMethod("fromJson", String.class, Class.class);
                    mJsonMethodToJsonObjectType = mGsonClass.getDeclaredMethod("fromJson", String.class, Type.class);
                    mJsonMethodToJsonString = mGsonClass.getDeclaredMethod("toJson", Object.class);
                } catch (IllegalAccessException mE) {
                    mE.printStackTrace();
                } catch (InstantiationException mE) {
                    mE.printStackTrace();
                } catch (NoSuchMethodException mE) {
                    mE.printStackTrace();
                } catch (ClassNotFoundException mE) {
                    mE.printStackTrace();
                }
            }
            if (mFastJson == null && mGsonJson == null || mJsonMethodToJsonString == null || mJsonMethodToJsonObjectClass == null) {
                new Throwable(WARNING_JSON);
                return false;
            }
            return true;
        }

        /**
         * 对象转换成Json文本
         *
         * @param mO
         * @return
         */
        protected static String ObjectToJson(Object mO) {
            if (mO != null) {

                if (isFastJson) {
                    try {
                        return (String) mJsonMethodToJsonString.invoke(null, mO);
                    } catch (IllegalAccessException mE) {
                        mE.printStackTrace();
                    } catch (InvocationTargetException mE) {
                        mE.printStackTrace();
                    }
                } else {
                    try {
                        return (String) mJsonMethodToJsonString.invoke(mGsonJson, mO);
                    } catch (IllegalAccessException mE) {
                        mE.printStackTrace();
                    } catch (InvocationTargetException mE) {
                        mE.printStackTrace();
                    }
                }
            }
            return "";
        }

        /**
         * Json文本转换成Json对象
         *
         * @param mJsonString
         * @param mClass
         * @param <T>
         * @return
         */
        protected static <T> T JsonToObject(String mJsonString, Class<T> mClass) {
            if (mJsonString != null && mClass != null) {
                if (isFastJson) {
                    try {
                        return (T) mJsonMethodToJsonObjectClass.invoke(null, mJsonString, mClass);
                    } catch (IllegalAccessException mE) {
                        mE.printStackTrace();
                    } catch (InvocationTargetException mE) {
                        mE.printStackTrace();
                    }
                } else {
                    try {
                        return (T) mJsonMethodToJsonObjectClass.invoke(mGsonJson, mJsonString, mClass);
                    } catch (IllegalAccessException mE) {
                        mE.printStackTrace();
                    } catch (InvocationTargetException mE) {
                        mE.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected static <T> T JsonToObject(String mJsonString, Type mType) {
            if (mJsonString != null && mType != null) {
                if (isFastJson) {
                    try {
                        return (T) mJsonMethodToJsonObjectType.invoke(null, mJsonString, mType, mFeatures);
                    } catch (Exception mE) {
                        mE.printStackTrace();
                    }
                } else {
                    try {
                        return (T) mJsonMethodToJsonObjectType.invoke(mGsonJson, mJsonString, mType);
                    } catch (IllegalAccessException mE) {
                        mE.printStackTrace();
                    } catch (InvocationTargetException mE) {
                        mE.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

}
