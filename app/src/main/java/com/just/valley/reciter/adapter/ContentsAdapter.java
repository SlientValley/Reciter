package com.just.valley.reciter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.just.valley.reciter.R;
import com.just.valley.reciter.act.ReciterActivity;
import com.just.valley.reciter.entity.ContentFormat;

import java.util.List;

/**
 * Created by Valley on 2017/10/27.
 */

public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ViewHolder> {

    private Context mContext;
    private List<ContentFormat> mContents;

    public ContentsAdapter(List<ContentFormat> Contents){
        mContents = Contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_content_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ContentFormat contentFormat = mContents.get(position);
                Intent intent = new Intent(mContext, ReciterActivity.class);
                intent.putExtra(ReciterActivity.ARTICLE_TITLE,contentFormat.getmTitle());
                intent.putExtra(ReciterActivity.ARTICLE_TEXT,contentFormat.getmText());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContentFormat contentItem = mContents.get(position);
        holder.mTitle.setText(contentItem.getmTitle());
        holder.mText.setText(contentItem.getmText());
        holder.mTextOrigin.setText(contentItem.getmTextOrigin());
        holder.mCharacterNumber.setText(contentItem.getmCharacterNumber()+"");

    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTitle;
        private TextView mText;
        private ImageView iNumber;
        private TextView mCharacterNumber;
        private ImageView iOrigin;
        private TextView mTextOrigin;
        private ImageView iTime;
        private TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            mTitle = (TextView) itemView.findViewById(R.id.item_pre_title);
            mText = (TextView) itemView.findViewById(R.id.item_pre_text);
            mCharacterNumber = (TextView) itemView.findViewById(R.id.number_character);
            mTextOrigin = (TextView) itemView.findViewById(R.id.origin_text);
            mTime = (TextView) itemView.findViewById(R.id.time);
            iNumber = (ImageView) itemView.findViewById(R.id.icon_number);
            iOrigin = (ImageView) itemView.findViewById(R.id.icon_origin);
            iTime = (ImageView) itemView.findViewById(R.id.icon_time);
        }
    }
}
