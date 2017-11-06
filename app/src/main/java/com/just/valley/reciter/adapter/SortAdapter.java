package com.just.valley.reciter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.just.valley.reciter.R;
import com.just.valley.reciter.act.SortActivity;
import com.just.valley.reciter.entity.ContentFormat;
import com.just.valley.reciter.entity.SortFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Valley on 2017/10/28.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {

    private Context mContext;
    private List<SortFormat> mSortFormat;

    public SortAdapter(List<SortFormat> sortFormats){
        mSortFormat = sortFormats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_sort_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SortFormat sortFormat = mSortFormat.get(position);
                if (sortFormat.getNumArticle() == null){
                    //发送消息给服务，添加分类
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
                    Intent intent = new Intent("com.just.valley.ADD_NEW_SORT_ITEM");
                    localBroadcastManager.sendBroadcast(intent);
                }else {
                    ArrayList<ContentFormat> contentList = sortData2Card();
                    Intent intent = new Intent(mContext, SortActivity.class);
                    intent.putExtra(SortActivity.SORT_NAME,sortFormat.getSortName());
                    intent.putExtra(SortActivity.CONTENTS,contentList);

                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    private ArrayList<ContentFormat> sortData2Card(){
        //在这里实现对数据的分类
        ContentFormat[] textContent = {
                new ContentFormat("hello","hhhhhhllllll","哈哈",120),
                new ContentFormat("hi","hhhhhhhiiiiiiii","嘻嘻",100)
        };
        ArrayList<ContentFormat> contentList = new ArrayList<>();
        for (int i=0;i<10;i++){
            Random random = new Random();
            int index = random.nextInt(textContent.length);
            contentList.add(textContent[index]);
        }
        return contentList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mSortName.setText(mSortFormat.get(position).getSortName());
        if(mSortFormat.get(position).getNumArticle() != null){
            holder.mNumArticle.setText(mSortFormat.get(position).getNumArticle()+"篇文章");
        }else {
            holder.mNumArticle.setText("+");
        }


    }

    @Override
    public int getItemCount() {
        return mSortFormat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mSortName;
        private TextView mNumArticle;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.getRootView();
            mSortName = (TextView) itemView.findViewById(R.id.name_sort);
            mNumArticle = (TextView) itemView.findViewById(R.id.number_article);
        }
    }

}
