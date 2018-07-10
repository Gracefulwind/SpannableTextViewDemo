package com.wind.widget.spannable.utils;

/**
 * Created by Gracefulwind Wang on 2018/7/6.
 * Email : Gracefulwindbigwang@gmail.com
 *
 * @author : Gracefulwind
 */
//public class UiUtils {
//
//    public static void setContext(Context context) {
//        UiUtils.mContext = context;
//    }
//
//    private static Context mContext;
//
//    /**
//     * Init method.Must been executed before use.
//     * $:can use setContext instead.
//     * */
//    public static void init(Context context){
//        setContext(context);
//    }
//
//    private static void checkInit() {
//        if(null == mContext){
//            throw new RuntimeException("This Utils has not been initialized, please execute the \" init \" method first!");
//        }
//    }
//
//    public static View inflate(@LayoutRes int layoutId){
//        checkInit();
//        View inflate = inflate(layoutId, null);
//        return inflate;
//    }
//
//    public static View inflate(@LayoutRes int layoutId, ViewGroup parent){
//        checkInit();
//        View inflate = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
//        return inflate;
//    }
//
//    public static int getTextViewHeight(TextView tv){
//        Layout layout = tv.getLayout();
//        if(null == layout){
//            return 0;
//        }
//        int desired = layout.getLineTop(tv.getLineCount());
//        int padding = tv.getCompoundPaddingTop() + tv.getCompoundPaddingBottom();
//        return desired + padding;
//    }
//
//}

