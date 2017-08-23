package com.example.mymis.lyhview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 作者：LYH   2017/8/21
 * <p>
 * 邮箱：945131558@qq.com
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView content;
    public TextView delete;
    public LinearLayout layout;
    public MyViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.item_content);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
