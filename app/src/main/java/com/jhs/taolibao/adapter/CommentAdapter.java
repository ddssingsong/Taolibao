package com.jhs.taolibao.adapter;

import android.content.Context;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.WebBiz;
import com.jhs.taolibao.base.recyclerview.CommonAdapter;
import com.jhs.taolibao.base.recyclerview.ViewHolder;
import com.jhs.taolibao.entity.Comment;

/**
 * Created by dds on 2016/6/24.
 *
 * @TODO
 */
public class CommentAdapter extends CommonAdapter<Comment> {
    public CommentAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Comment comment) {

        if (comment.getUserIcon() != null) {
            holder.setCircleImageDrawable(R.id.iv_user_icon, WebBiz.UPLOAD_PREFIX + comment.getUserIcon());
        }
        holder.setText(R.id.tv_user_alias, comment.getMobile());
        if (comment.getAlias() != null && !comment.getAlias().equals("")) {
            holder.setText(R.id.tv_user_alias, comment.getAlias());
        }
        holder.setText(R.id.tv_comment_info, comment.getComment());
        holder.setText(R.id.tv_comment_time, comment.getTimeStr());


    }
}
