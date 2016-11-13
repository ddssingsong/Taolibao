package com.jhs.taolibao.code.challenge.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.code.challenge.activity.MyInfoActivity;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.utils.NoDoubleClickListener;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;

import java.util.List;

/**
 * @author jiao、xujingbo on 2016/7/15 11:37
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:"短线排行榜"fragment的适配器
 */
public class ShortRankingFragmentAdapter extends BaseAdapter {
    private OnItemClick click;
    private List<ArenaUserInfo> datas;
    public void setOnItemClickListener(OnItemClick click) {
        this.click = click;
    }
    
    public void setDatas(List<ArenaUserInfo> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas== null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shortrankingfragment
                    , parent, false);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.short_adapter_name);
            holder.tvGuard = (TextView) convertView.findViewById(R.id.short_adapter_game_time);
            holder.tvWin = (TextView) convertView.findViewById(R.id.short_adapter_success);
            holder.tvCumulativeReturns = (TextView) convertView.findViewById(R.id.short_adapter_income);
            holder.tvbg = (TextView) convertView.findViewById(R.id.short_adapter_tvbg);
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.short_adapter_icon);
            holder.left = (Button) convertView.findViewById(R.id.short_adapter_left_btn);
            //holder.right = (Button) convertView.findViewById(R.id.short_adapter_right_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ArenaUserInfo userInfo = datas.get(position);
        holder.name.setText(userInfo.getAlias());
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(), holder.ivAvatar, ImageLoaderUtil.roundOptions);
        holder.tvCumulativeReturns.setText(userInfo.getCumulativeReturns());
        holder.tvGuard.setText(String.valueOf(userInfo.getGuard()));
        holder.tvWin.setText(String.valueOf(userInfo.getWin()) + ":" + String.valueOf(userInfo.getLose()+userInfo.getWin()));
        holder.left.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //click.onItemClick(v);
                parent.getContext().startActivity(
                        new Intent(parent.getContext(),
                                MyInfoActivity.class).putExtra("userid",userInfo.getUserID()));
            }
        });
       /* holder.right.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //click.onItemClick(v);
                showCostDialog(userInfo.getUserId(),parent.getContext());

                    }
                });*/
        TextView view = (TextView) convertView.findViewById(R.id.short_adapter_tvbg);
        view.setText(String.valueOf(position+1));
        GradientDrawable drawable = (GradientDrawable) view.getBackground();
        switch (position) {
            case 0:
                drawable.setColor(Color.RED);
                break;
            case 1:
                drawable.setColor(Color.BLUE);
                break;
            case 2:
                drawable.setColor(Color.parseColor("#ffc21d"));
                break;
            default:
                drawable.setColor(Color.GRAY);
                break;
        }
        return convertView;
    }

    /**
     * 偷看持仓扣费提示框
     */
    /*private void showCostDialog(final int userId, final Context context){
        //获取总宝币数
        ChallengeCenter.getInstance().getTotalPoint(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                UserInfo userInfo = (UserInfo) response;
                final int totalPoint = userInfo.getUser().getUserTotalPoint();
                final int costPoint = ChallengeConstant.COST_POINT;
                final int type = ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal();
                //扣费弹出框
                new CostAlertDialog(context).builder()
                        .setTotalCoin(String.valueOf(totalPoint))
                        .setMsg("偷看持仓将扣除"+costPoint+"宝币")
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //challengeOrWatch(type);
                                if (totalPoint < costPoint){
                                    ToastUtil.showToast(context, "宝币总数不够");
                                }else {
                                    context.startActivity(
                                            new Intent(context, PxCookActivity.class).putExtra("userid", userId));
                                    ChallengeCenter.getInstance().getInserArenaUser(0, type, costPoint,
                                            new ChallengeTask.OnChallengeListener() {
                                                @Override
                                                public void onSuccess(Object response) {
                                                    context.startActivity(
                                                            new Intent(context, PxCookActivity.class).putExtra("userid", userId));
                                                }

                                                @Override
                                                public void onFailure(String msg, Exception e) {
                                                    if (!msg.equals("")) {
                                                        ToastUtil.showToast(context,msg);
                                                    }
                                                }
                                            });

                                }
                            }
                        }).show();
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }*/
    class ViewHolder {
        TextView name, tvGuard, tvWin, tvCumulativeReturns, tvbg;
        ImageView ivAvatar;
        Button left, right;
    }

    public interface OnItemClick {
        void onItemClick(View view);
    }
}
