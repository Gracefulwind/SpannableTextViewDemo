package com.wind.widget.spannable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Gracefulwind Wang on 2018/7/6.
 * Email : Gracefulwindbigwang@gmail.com
 *
 * @author : Gracefulwind
 */
public class SimpleTestView extends FrameLayout {

    private static final int[] STATE_MESSAGE_UNREAD = {R.attr.expand1};

    private TextView messageSubject;
    private boolean messageUnread;

    public SimpleTestView(@NonNull Context context) {
        super(context);
    }

    public SimpleTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadViews();
    }

    public SimpleTestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadViews();
    }

    private void loadViews() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.simple_test, this, true);
        setBackgroundResource(R.color.message_list_item_background_unread);
        messageSubject = (TextView) findViewById(R.id.stv_tv_content);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        // If the message is unread then we merge our custom message unread state into
        // the existing drawable state before returning it.
        if (messageUnread) {
            // We are going to add 1 extra state.
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, STATE_MESSAGE_UNREAD);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public void setMessageSubject(String subject) {
        messageSubject.setText(subject);
    }

    public void setMessageUnread(boolean messageUnread) {
        // Performance optimisation: only update the state if it has changed.
        if (this.messageUnread != messageUnread) {
            this.messageUnread = messageUnread;

            // Refresh the drawable state so that it includes the message unread state if required.
            refreshDrawableState();
        }
    }
    public boolean getMessageUnread(){
        return messageUnread;
    }
}
