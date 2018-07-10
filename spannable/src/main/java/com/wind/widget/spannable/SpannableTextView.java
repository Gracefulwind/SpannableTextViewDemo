package com.wind.widget.spannable;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class SpannableTextView extends FrameLayout implements SpanClickAccess{


    private static final int[] STATE_EXPAND = new int[]{R.attr.expand};

    private LinkClickListener linkClickListener;
    private boolean linkClicked = false;
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
        float dimenCommon = context.getResources().getDimension(R.dimen.dimen_12sp);

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
        int iconSrc = typedArray.getResourceId(R.styleable.SpannableTextView_iconSrc, R.drawable.selector_icon);
        setIcon(iconSrc);
        float defaultHeight = typedArray.getDimension(R.styleable.SpannableTextView_defaultIconHeight, context.getResources().getDimension(R.dimen.dimen_6dp));
        float defaultWidth = typedArray.getDimension(R.styleable.SpannableTextView_defaultIconWidth, context.getResources().getDimension(R.dimen.dimen_12dp));
        float defaultMarginLeft = typedArray.getDimension(R.styleable.SpannableTextView_defaultIconMarginLeft, 0);
        float defaultMarginTop = typedArray.getDimension(R.styleable.SpannableTextView_defaultIconMarginTop, context.getResources().getDimension(R.dimen.dimen_4dp));
        float defaultMarginRight = typedArray.getDimension(R.styleable.SpannableTextView_defaultIconMarginRight, context.getResources().getDimension(R.dimen.dimen_3dp));
//        int textSize0 = typedArray.getDimensionPixelOffset(R.styleable.SpannableTextView_textSize, 12);
//        float textSize1 = typedArray.getDimension(R.styleable.SpannableTextView_textSize, 12);
//        int textSize2 = typedArray.getDimensionPixelOffset(R.styleable.SpannableTextView_textSize, 12);
        typedArray.recycle();
        //=====
        tvContent.setText(textText);
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvContent.setTextColor(textColor);
        float rate = textSize / dimenCommon;
//        float defaultMarginRight = context.getResources().getDimension(R.dimen.dimen_3dp);
//        float defaultMarginTop = context.getResources().getDimension(R.dimen.dimen_4dp);
//        float defaultHeight = context.getResources().getDimension(R.dimen.dimen_6dp);
//        float defaultWidth = context.getResources().getDimension(R.dimen.dimen_12dp);


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivExpandIcon.getLayoutParams();
        layoutParams.height = (int) (rate * defaultHeight);
        layoutParams.width = (int) (rate * defaultWidth);
        layoutParams.leftMargin = (int) (rate * defaultMarginLeft);
        layoutParams.topMargin = (int) (rate * defaultMarginTop);
        layoutParams.rightMargin = (int) (rate * defaultMarginRight);
        System.out.println("=============");
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
            this.expand = expand;
            refreshDrawableState();
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

    @Override
    public void setSpanClicked(boolean clicked) {
        linkClicked = true;
    }

    @Override
    public boolean performClick() {
        //如果触发了span的事件了，则直接消费控件的点击事件
        if(linkClicked){
            return true;
        }
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //先触发touch.ACTION_UP，然后判断出是点击事件才会触发performClick
        if(MotionEvent.ACTION_UP == event.getAction()){
            linkClicked = false;
        }
//        linkClicked = false;
        return super.onTouchEvent(event);
    }


    public void setLinkClickListener(LinkClickListener linkClickListener){
        this.linkClickListener = linkClickListener;
    }

    /**
     *
     * 设置url同时设置url点击事件，
     * 如果不需要修改点击事件，请看:
     * @see #setLinkText(CharSequence)
     *
     * */
    public void setLinkText(CharSequence charSeq, LinkClickListener urlClickListener){
        this.linkClickListener = urlClickListener;
        setLinkText(charSeq);
    }

    public void setLinkText(CharSequence charSeq){
        tvContent.setText(getClickableHtml(charSeq.toString()));
        tvContent.setMovementMethod(SpannableLinkMovementMethod.getInstance());
    }

    /**
     * 格式化超链接文本内容并设置点击处理
     */
    public CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
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
