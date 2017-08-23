package com.example.mymis.lyhview;

import android.view.View;

/**
 * 作者：LYH   2017/8/21
 * <p>
 * 邮箱：945131558@qq.com
 */

public interface OnItemClickListener {
    /**
     * item点击回调
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 删除按钮回调
     *
     * @param position
     */
    void onDeleteClick(int position);
}
