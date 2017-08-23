package com.example.mymis.lyhview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 作者：LYH   2017/8/21
 * <p>
 * 邮箱：945131558@qq.com
 */

public class MyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mList;

    public MyAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_delete, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.content.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
         return mList != null ? mList.size() : 0;
    }
    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
