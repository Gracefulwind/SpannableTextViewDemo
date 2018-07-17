package com.wind.widget.spannabletextviewdemo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wind.widget.spannabletextviewdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gracefulwind Wang on 2018/7/16.
 * Email : Gracefulwindbigwang@gmail.com
 *
 * @author : Gracefulwind
 */
public class SimpleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> data;


    public SimpleListAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_list, parent, false);
        Holder holder = new Holder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String str = data.get(position);
        Holder thisHolder = (Holder)holder;
        thisHolder.imcraTvMsg.setText(str);
//        if(position / 2 == 1){
//            thisHolder.imcraTvMsg.setExpand(true);
//        }
    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.imcra_tv_title)
        TextView imcraTvTitle;
        @BindView(R.id.imcra_tv_msg)
        com.wind.widget.spannable.SpannableTextView imcraTvMsg;
        @BindView(R.id.imcra_tv_date)
        TextView imcraTvDate;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
