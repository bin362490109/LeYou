package com.fjby.travel.leyou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fjby.travel.leyou.R;
import com.fjby.travel.leyou.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monkey on 2015/6/29.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private Context mContext;
    private int [] mImages={R.drawable.groupchat,R.drawable.guidechat,R.drawable.resorts, R.drawable.supervision};
    private List<String[]> mDatas;
    private LayoutInflater mLayoutInflater;


    public MyRecyclerViewAdapter(Context mContext,List<String[]> dates) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatas=dates;
    }
    /**
     * 创建ViewHolder
     */
    @Override
    public MyRecyclerViewAdapter.MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mLayoutInflater.inflate(R.layout.adapter_infos, parent, false);
        MyRecyclerViewHolder mViewHolder = new MyRecyclerViewHolder(mView);
        return mViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return true;
                }
            });
        }
        holder.mListItemTitle.setText(mDatas.get(position)[0]);
        holder.mListItemMsg.setText(mDatas.get(position)[1]);
        holder.mListItemTime.setText(mDatas.get(position)[2]);
        int i= Integer.parseInt(mDatas.get(position)[3]);
        if(i<4){
            holder.mListItemIcon.setImageResource(mImages[i]);
        }else{
            holder.mListItemIcon.setImageResource(mImages[0]);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mListItemTitle;
        public TextView mListItemMsg;
        public TextView mListItemTime;
        public ImageView mListItemIcon;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mListItemTitle = (TextView) itemView.findViewById(R.id.list_item_title);
            mListItemMsg = (TextView) itemView.findViewById(R.id.list_item_msg);
            mListItemTime = (TextView) itemView.findViewById(R.id.list_item_time);
            mListItemIcon = (ImageView) itemView.findViewById(R.id.list_item_icon);
        }
    }
}
