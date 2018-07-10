package com.wind.widget.spannabletextviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wind.widget.spannable.MessageListItemView;
import com.wind.widget.spannable.SimpleTestView;
import com.wind.widget.spannable.SpannableTextView;

public class MainActivity extends AppCompatActivity {

    private SpannableTextView adStvContent;
    private SimpleTestView amSimpleTest;
    private MessageListItemView amMlivTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adStvContent = findViewById(R.id.am_stv_content);
        //===
        amSimpleTest = findViewById(R.id.am_simple_test);
        amSimpleTest.setMessageSubject("测试1,未读");
        //===
        amMlivTest = findViewById(R.id.am_mliv_test);
        amMlivTest.setMessageSubject("测试,未读");
//        amMlivTest.setMessageUnread(true);
        adStvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "当前View是：" + v.toString(), Toast.LENGTH_SHORT).show();
            }
        });
//        R.drawable.selector_test;
//        SpannableTextView stv = new SpannableTextView(this);
//        stv.setLinkText();
    }

    public void click1(View view) {
        m1();
//        adStvContent.setText(null);
        adStvContent.setText(getResources().getString(R.string.testStr1));
    }

    private void m1() {
//        adStvContent.setExpand(!adStvContent.getExpand());
        amMlivTest.setMessageUnread(!amMlivTest.getMessageUnread());
        amSimpleTest.setMessageUnread(!amSimpleTest.getMessageUnread());
    }
}
