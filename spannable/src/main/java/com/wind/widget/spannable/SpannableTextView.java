package com.wind.widget.spannable;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gracefulwind Wang on 2018/7/6.
 * Email : Gracefulwindbigwang@gmail.com
 *
 * @author : Gracefulwind
 */
public class SpannableTextView extends FrameLayout{


    private static final int[] STATE_EXPAND = new int[]{R.attr.expand};

    private LinkClickListener linkClickListener;
//    private boolean linkClicked = false;
    private int expandLines;

    //never use anymore
    private boolean expand = false;
    private int contentHeight = 0;
    private int expandHeight = 0;
    private TextView tvContent;
    private ImageView ivExpandIcon;
    private int textColor;
    private String textText;
    private float textSize;
    private int iconSrc;
    private float dimenCommon;
    //默认可伸缩
    private boolean canExpand = true;
    private OnClickListener contentListener;

    public SpannableTextView(Context context) {
        this(context, null);
    }

    public SpannableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SpannableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View inflate = LayoutInflater.from(context).inflate(layout(), this, true);
        tvContent = findViewById(R.id.stv_tv_content);
        ivExpandIcon = findViewById(R.id.stv_iv_expand_icon);

        if(null == attrs){
            return;
        }
        dimenCommon = context.getResources().getDimension(R.dimen.dimen_12sp);
        //==开始读取属性======
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpannableTextView);
        expandLines = typedArray.getInt(R.styleable.SpannableTextView_expandLines, Integer.MAX_VALUE);
        textText = typedArray.getString(R.styleable.SpannableTextView_text);
        textColor = typedArray.getColor(R.styleable.SpannableTextView_textColor, Color.BLACK);
        textSize = typedArray.getDimension(R.styleable.SpannableTextView_textSize, dimenCommon);
        //icon
        context.obtainStyledAttributes(attrs, R.styleable.SpannableTextView);
//        int iconSrc = typedArray.getResourceId(R.styleable.SpannableTextView_iconSrc, R.drawable.selector_icon);
//        Drawable drawable = context.getResources().getDrawable(iconSrc);
//        ivExpandIcon.setImageDrawable(drawable);
//        setIcon(iconSrc);
        expand = typedArray.getBoolean(R.styleable.SpannableTextView_expand, false);
        canExpand = typedArray.getBoolean(R.styleable.SpannableTextView_canExpand, true);
        int iconSrc = typedArray.getResourceId(R.styleable.SpannableTextView_iconSrc, R.drawable.selector_icon);
        float xmlHeight = typedArray.getDimension(R.styleable.SpannableTextView_IconHeight, -5);
        float xmlWidth = typedArray.getDimension(R.styleable.SpannableTextView_IconWidth, -5);
        float xmlMarginLeft = typedArray.getDimension(R.styleable.SpannableTextView_IconMarginLeft, -5);
        float xmlMarginTop = typedArray.getDimension(R.styleable.SpannableTextView_IconMarginTop, -5);
        float xmlMarginRight = typedArray.getDimension(R.styleable.SpannableTextView_IconMarginRight, -5);
//        int textSize0 = typedArray.getDimensionPixelOffset(R.styleable.SpannableTextView_textSize, 12);
//        float textSize1 = typedArray.getDimension(R.styleable.SpannableTextView_textSize, 12);
//        int textSize2 = typedArray.getDimensionPixelOffset(R.styleable.SpannableTextView_textSize, 12);
        typedArray.recycle();
        //=====
        //==设置右上图标====
        setIcon(iconSrc);
        //==设置宽高==
        setIconLayout(context, xmlHeight, xmlWidth, xmlMarginLeft, xmlMarginTop, xmlMarginRight);
        //设置可点击文本
        setContextText();
        initListener();
        System.out.println("=============");
    }

    private void initListener() {
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != contentListener){
                    contentListener.onClick(v);
                }
                if(canExpand){
                    changeExpandStatus();
                }
            }
        });
    }

    private void changeExpandStatus() {
        int maxLines = tvContent.getMaxLines();
        //todo:setExpandIcon
        if(maxLines == expandLines){
            expand = true;
            tvContent.setMaxLines(Integer.MAX_VALUE);
        }else {
            expand = false;
            tvContent.setMaxLines(expandLines);
        }
        refreshDrawableState();
    }

    private void setContextText() {
        CharSequence clickableHtml = getClickableHtml(textText);
        tvContent.setText(clickableHtml);
        tvContent.setMovementMethod(SpannableLinkMovementMethod.getInstance());
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvContent.setTextColor(textColor);
        if(expand){
            tvContent.setMaxLines(Integer.MAX_VALUE);
        }else {
            tvContent.setMaxLines(expandLines);
        }
    }

    /**
     *
     * 设置自定义伸缩图标的大小、位置。
     * 如果不设置，则为默认值，且会随着字体的大小变化而自适应。
     * 如果设置了，则使用用户自定义的大小，不再自适应。
     * MATCH_PARENT和WRAP_CONTENT强烈不推荐。。。
     *
     * */
    private void setIconLayout(Context context, float xmlHeight, float xmlWidth, float xmlMarginLeft, float xmlMarginTop, float xmlMarginRight) {
        //图标倍率，让图标随着文字大小适应
//        LinearLayout.LayoutParams.MATCH_PARENT = -1
//        LinearLayout.LayoutParams.WRAP_CONTENT = -2
        float rate = textSize / dimenCommon;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivExpandIcon.getLayoutParams();
        if(xmlHeight < 0){
            if(LayoutParams.MATCH_PARENT == xmlHeight){
                layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
            }else if(LayoutParams.WRAP_CONTENT == xmlHeight) {
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }else {
                float defaultHeight = context.getResources().getDimension(R.dimen.dimen_6dp);
                layoutParams.height = (int) (rate * defaultHeight);
            }
        }else {
            layoutParams.height = (int) xmlHeight;
        }
        if(xmlWidth < 0){
            if(LayoutParams.MATCH_PARENT == xmlWidth){
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            }else if(LayoutParams.WRAP_CONTENT == xmlWidth) {
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            }else {
                float defaultWidth = context.getResources().getDimension(R.dimen.dimen_12dp);
                layoutParams.width = (int) (rate * defaultWidth);
            }
        }else {
            layoutParams.height = (int) xmlWidth;
        }
        if(xmlMarginLeft < 0){
            float defaultMarginLeft = context.getResources().getDimension(R.dimen.dimen_0dp);
            layoutParams.leftMargin = (int) (rate * defaultMarginLeft);
        }else {
            layoutParams.leftMargin = (int) xmlMarginLeft;
        }
        if(xmlMarginTop < 0){
            float defaultMarginTop = context.getResources().getDimension(R.dimen.dimen_4dp);
            layoutParams.topMargin = (int) (rate * defaultMarginTop);
        }else {
            layoutParams.topMargin = (int) xmlMarginTop;
        }
        if(xmlMarginRight < 0){
            float defaultMarginRight = context.getResources().getDimension(R.dimen.dimen_3dp);
            layoutParams.rightMargin = (int) (rate * defaultMarginRight);
        }else {
            layoutParams.rightMargin = (int) xmlMarginRight;
        }
        ivExpandIcon.setLayoutParams(layoutParams);
    }


//    private void setIcon(int iconSrc) {
//        this.iconSrc = iconSrc;
//        refreshDrawableState();
//    }

    private void setIcon(int iconSrc) {
        ivExpandIcon.setImageResource(iconSrc);
    }


    public boolean getExpand(){
        return expand;
    }



    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if(expand){
            final int[] states = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(states, STATE_EXPAND);
            return states;
        }else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setExpand(boolean expand){
        System.out.println("pre expand=" + expand);
        if(this.expand != expand){
            changeExpandStatus();
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
    }

    protected @LayoutRes int layout() {
        return R.layout.spannable_text_view;
    }
    //======

//    @Override
//    public void setSpanClicked(boolean clicked) {
//        linkClicked = true;
//    }
//
//    @Override
//    public boolean performClick() {
//        //如果触发了span的事件了，则直接消费控件的点击事件
//        if(linkClicked){
//            return true;
//        }
//        return super.performClick();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //先触发touch.ACTION_UP，然后判断出是点击事件才会触发performClick
//        if(MotionEvent.ACTION_UP == event.getAction()){
//            linkClicked = false;
//        }
////        linkClicked = false;
//        return super.onTouchEvent(event);
//    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.contentListener = l;
    }

    public void setLinkClickListener(LinkClickListener linkClickListener){
        this.linkClickListener = linkClickListener;
    }

    /**
     *
     * 设置url同时设置url点击事件，
     * 如果不需要修改点击事件，请看:
     * @see #setText(CharSequence)
     *
     * */
    public void setText(CharSequence charSeq, LinkClickListener urlClickListener){
        this.linkClickListener = urlClickListener;
        setText(charSeq);
    }

    public void setText(CharSequence charSeq){
        tvContent.setText(getClickableHtml(charSeq));
//        tvContent.setMovementMethod(SpannableLinkMovementMethod.getInstance());
    }

    /**
     * 格式化超链接文本内容并设置点击处理
     */
    public CharSequence getClickableHtml(CharSequence html) {
        if(null == html){
            html = "";
        }
        Spanned spannedHtml = Html.fromHtml(html.toString());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = spannableStringBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(spannableStringBuilder, span);
        }
        return spannableStringBuilder;
    }

    /**
     * 设置点击超链接对应的处理内容
     */
    public void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,  final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                // super.updateDrawState(ds);
                ds.setUnderlineText(false); // 去除下划线
                ds.setColor(Color.parseColor("#60a8ee"));
            }

            @Override
            public void onClick(View view) {
                if(null != linkClickListener){
                    boolean expended = linkClickListener.onLinkClicked(SpannableTextView.this, urlSpan);
                    if(expended){
                        return;
                    }
                }
                //默认操作，打开浏览器访问url
                try{
                    String url = urlSpan.getURL();
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }catch (Exception excetion){
                    excetion.printStackTrace();
                    Toast.makeText(getContext(), "url异常，无法打开浏览器", Toast.LENGTH_SHORT).show();
                }

            }
        };
        //将可点span替换掉原内容
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
        clickableHtmlBuilder.removeSpan(urlSpan);
    }

    /**
     *
     * URL点击事件接口
     *
     * */
    public interface LinkClickListener {
        boolean onLinkClicked(SpannableTextView view, URLSpan urlSpan);
    }

}
