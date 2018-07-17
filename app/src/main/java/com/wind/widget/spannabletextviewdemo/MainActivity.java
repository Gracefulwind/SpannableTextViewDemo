package com.wind.widget.spannabletextviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.wind.widget.spannable.SpannableTextView;
import com.wind.widget.spannabletextviewdemo.adapters.SimpleListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.am_pcf_refresh)
    PtrClassicFrameLayout amPcfRefresh;
    @BindView(R.id.am_rv_text_list)
    RecyclerView amRvTextList;
    @BindView(R.id.am_tv_result)
    TextView amTvResult;
    @BindView(R.id.am_tv_title)
    TextView amTvTitle;
    private SpannableTextView amStvContent;


//  ====FIELDS==========
    ArrayList<String> data = new ArrayList<>();
    private SimpleListAdapter simpleListAdapter;
    private RecyclerAdapterWithHF recyclerAdapterWithHF;
    private MainActivity mActivity;

    {
        data.add("111这是个测试123456这是个测试123456<a href=https://www.baidu.com>百度超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("222这是个测试123456这是个测试123456<a href=https://www.baidu.com>百度超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("333这是个测试123456这是个测试123456<a href=https://www.baidu.com>百度超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("444这是个测试123456这是个测试123456<a href=https://www.baidu.com>百度超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("555这是个测试123456这是个测试123456<a href=https://www.baidu.com>百度超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("666这是个测试123456这是个测试123456<a href=https://www.163.com>这个超链的名字有点长哈哈哈哈哈超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("777这是个测试123456这是个测试12345这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是6<a href=https://www.baidu.com>百度超链</a>个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("888这是个测试123456这是个测试123456<a href=https://www.taobao.com>淘宝超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");
        data.add("999这是个测试123456<a href=https://www.qq.com>腾讯超链</a>这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456这是个测试123456");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ButterKnife.bind(this);
        initView();
//        amStvContent.setLinkClickListener();
//        R.drawable.selector_test;
//        SpannableTextView stv = new SpannableTextView(this);
//        stv.setLinkText();
    }

    private void initView() {
        amStvContent = findViewById(R.id.am_stv_content);
        amStvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "当前View是：" + v.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        amStvContent.setLinkClickListener(new SpannableTextView.LinkClickListener() {
            @Override
            public boolean onLinkClicked(SpannableTextView view, URLSpan urlSpan) {
//                Toast.makeText(MainActivity.this, "被点击的url是：" + urlSpan.getURL(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        initRecyclerView();
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        amRvTextList.setLayoutManager(linearLayoutManager);
        simpleListAdapter = new SimpleListAdapter(data);
        recyclerAdapterWithHF = new RecyclerAdapterWithHF(simpleListAdapter);

        amRvTextList.setAdapter(recyclerAdapterWithHF);
        amPcfRefresh.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    @OnClick({R.id.am_btn_fun2, R.id.am_btn_fun3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.am_btn_fun2:
                //测试最大行数
//                amStvContent.setExpandLines(2);
                //测试展开/收缩
//                amStvContent.setCanExpand(true);
                amStvContent.setTextSize(99);
                break;
            case R.id.am_btn_fun3:
                //测试展开/收缩
//                amStvContent.setCanExpand(false);
                //测试最大行数
//                amStvContent.setExpandLines(3);
                //测试超链接变色
//                amStvContent.setLinkColor(Color.parseColor("#FF0000"));
                //测试文字/右上图标缩放
                amStvContent.setTextSize(getResources().getDimension(R.dimen.dimen_12sp));
                break;
        }
    }
}
