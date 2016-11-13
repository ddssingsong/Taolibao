package com.jhs.taolibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.entity.GuessHistory;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dds on 2016/7/18.
 *
 * @TODO
 */
public class GuessHistoryAdapter extends BaseExpandableListAdapter {

    private List<GuessHistory.DirectoryResultsEntity> gamelist;
    private HashMap<Integer, List<GuessHistory.DirectoryResultsEntity.GameListEntity>> childlist;
    private LayoutInflater inflater;


    public GuessHistoryAdapter(Context context, List<GuessHistory.DirectoryResultsEntity> gamelist,
                               HashMap<Integer, List<GuessHistory.DirectoryResultsEntity.GameListEntity>> childlist) {
        inflater = LayoutInflater.from(context);
        this.childlist = childlist;
        this.gamelist = gamelist;

    }

    @Override
    public int getGroupCount() {
        return gamelist == null ? 0 : gamelist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childlist.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gamelist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GuessHistory.DirectoryResultsEntity data = gamelist.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_guess_history_group, parent, false);
            AutoUtils.autoSize(convertView);
        }

        TextView tv_amount = (TextView) convertView.findViewById(R.id.tv_guess_history_amount);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_guess_date);
        ImageView iv_up_down_arrow = (ImageView) convertView.findViewById(R.id.iv_up_down_arrow);
        tv_amount.setText(Integer.toString(data.getAmount()));
        tv_date.setText(data.getDataStr());

        if (isExpanded) {
            iv_up_down_arrow.setBackgroundResource(R.drawable.ic_up_arrow);
        } else {
            iv_up_down_arrow.setBackgroundResource(R.drawable.ic_down_arrow);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GuessHistory.DirectoryResultsEntity.GameListEntity gameListEntity = gamelist.get(groupPosition).getGameList().get(childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_guess_history_child, parent, false);
            AutoUtils.autoSize(convertView);
        }

        TextView tv_typename = (TextView) convertView.findViewById(R.id.tv_guess_typename);
        TextView tv_guess_gamepoint = (TextView) convertView.findViewById(R.id.tv_guess_gamepoint);
        TextView tv_guess_endpoint = (TextView) convertView.findViewById(R.id.tv_guess_endpoint);
        TextView tv_reward_interval = (TextView) convertView.findViewById(R.id.tv_reward_interval);
        TextView tv_guess_result = (TextView) convertView.findViewById(R.id.tv_guess_result);

        tv_typename.setText(gameListEntity.getTypeName() + "");
        tv_guess_gamepoint.setText(gameListEntity.getGamePoint() + "");
        tv_guess_endpoint.setText("" + gameListEntity.getEndPoint());
        tv_reward_interval.setText(gameListEntity.getMinPoint() + "-" + gameListEntity.getMaxPoint());
        if (gameListEntity.getMinPoint() <= (int) gameListEntity.getEndPoint() && (int) gameListEntity.getEndPoint() <= gameListEntity.getMaxPoint()) {
            if (gameListEntity.getGamePoint() == (int) gameListEntity.getEndPoint()) {
                tv_guess_result.setEnabled(true);
                tv_guess_result.setText("竞猜成功");
                tv_guess_result.setTextColor(android.graphics.Color.RED);
            } else {
                tv_guess_result.setEnabled(false);
                tv_guess_result.setText("竞猜失败");
                tv_guess_result.setTextColor(android.graphics.Color.GRAY);
            }
        } else {
            if (gameListEntity.getMinPoint() <= gameListEntity.getGamePoint() && gameListEntity.getGamePoint() <= gameListEntity.getMaxPoint()) {
                tv_guess_result.setEnabled(false);
                tv_guess_result.setText("竞猜失败");
                tv_guess_result.setTextColor(android.graphics.Color.GRAY);
            } else {
                tv_guess_result.setEnabled(true);
                tv_guess_result.setText("竞猜成功");
                tv_guess_result.setTextColor(android.graphics.Color.RED);
            }

        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
