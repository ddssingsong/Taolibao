package com.jhs.taolibao.code.challenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.challenge.activity.ChallengeDetailsActivity;
import com.jhs.taolibao.code.challenge.activity.MyInfoActivity;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.MyArena;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;

import java.util.List;

/**
 * 我的擂台adapter
 * Created by KANGXIANGTAO on 2016/7/15.
 */
public class MyArenaAdapter extends BaseAdapter {

    private Context ctx;
    private List<MyArena.ArenaDetailView> dataList;
    private int type;//2：挑战中 4：观战中 3：已完成
    public MyArenaAdapter(Context ctx,int type,List<MyArena.ArenaDetailView> dataList){
        this.ctx = ctx;
        this.dataList = dataList;
        this.type = type;
    }

    public void setData(List<MyArena.ArenaDetailView> dataList){
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_myarena,null);
            viewHolder = new ViewHolder();
            viewHolder.ivChampion = (ImageView) convertView.findViewById(R.id.iv_leizhu);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            //守擂次数
            viewHolder.tvGuradTimes = (TextView) convertView.findViewById(R.id.tv_gurad_times);
            //胜率
            viewHolder.tvWinRote = (TextView) convertView.findViewById(R.id.tv_winrate);
            //收益率
            viewHolder.tvEranRate = (TextView) convertView.findViewById(R.id.tv_eranrate);
            viewHolder.btnCheckInfo = (Button) convertView.findViewById(R.id.btn_check_info);

            viewHolder.tvStartDate = (TextView) convertView.findViewById(R.id.tv_start_date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MyArena.ArenaDetailView arenaDetail = dataList.get(position);
        if (null == arenaDetail){
            return convertView;
        }
        final ArenaUserInfo userInfo = arenaDetail.getChampion();
        if (null == userInfo){
            return convertView;
        }
        if (type == 3){
            //已完成不能查看对战情况
            viewHolder.btnCheckInfo.setVisibility(View.GONE);
            viewHolder.tvStartDate.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tvStartDate.setVisibility(View.GONE);
            viewHolder.btnCheckInfo.setVisibility(View.VISIBLE);
        }
        //viewHolder.tvStartDate.setText(arenaDetail.getStartDtae());
        viewHolder.tvStartDate.setText(DateUtils.getWeekByTime(Long.decode(arenaDetail.getStartDtae())));
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(), viewHolder.ivIcon, ImageLoaderUtil.roundOptions);
        viewHolder.tvName.setText(userInfo.getAlias());
        viewHolder.tvGuradTimes.setText(String.valueOf(userInfo.getGuard()) + "次");
        viewHolder.tvWinRote.setText(
                String.valueOf(userInfo.getWin()) + ":" + String.valueOf(userInfo.getLose()+userInfo.getWin()));
        viewHolder.tvEranRate.setText(String.valueOf(userInfo.getCumulativeReturns()));
        final int arenaId = arenaDetail.getArenaID();
        viewHolder.btnCheckInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不用再获取擂台角色了
                //getUserRole(userInfo.getUserId(),arenaDetail);
                Intent intent = new Intent(ctx,ChallengeDetailsActivity.class);
                if (type == 2){
                    //挑战
                    intent.putExtra("type",ChallengeConstant.ArenaType.TYPE_CHALLENGE.ordinal());
                    intent.putExtra("role", ChallengeConstant.ArenaRole.CHANGLLER.ordinal());
                }else if (type == 4){
                    //观战
                    intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                    if (userInfo.getUserID() == UserInfoSingleton.getUserInfo().getId()){
                        intent.putExtra("role", ChallengeConstant.ArenaRole.CHAMPION.ordinal());
                    }else {
                        intent.putExtra("role", ChallengeConstant.ArenaRole.WHATCHER.ordinal());
                    }
                }
                intent.putExtra("arenaId",arenaId);
                ctx.startActivity(intent);
            }
        });
        //点击头像查看用户信息
        viewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctx.startActivity(
                        new Intent(parent.getContext(),
                                MyInfoActivity.class).putExtra("userid", userInfo.getUserID()));
            }
        });
        return convertView;
    }
    /**
     * 获取用户擂台角色
     * @param userId
     */
    /*private void getUserRole(int userId, final MyArena.ArenaDetailView arenaDetailView){

        try {
            ChallengeCenter.getInstance().getUserRole(arenaDetailView.getArenaID(),userId,new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    UserRole userRole = (UserRole)response;
                    ChallengeConstant.ArenaRole role = userRole.getRole();
                    startNewActivity( role, arenaDetailView);
                }
                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     *
     * 根据用户的擂台角色进入进入相应的观战或者挑战界面
     * @param  用户的角色
     */
    /*private void startNewActivity(ChallengeConstant.ArenaRole role,MyArena.ArenaDetailView arenaDetailView) {
        int arenaId = arenaDetailView.getArenaID();
        Intent intent = new Intent(ctx,ChallengeDetailsActivity.class);
        switch (role){

            case CHAMPION:
                //擂主,直接进入观战界面
                intent.putExtra("type",ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                break;
            case CHANGLLER:
                //挑战者，进入挑战界面
                intent.putExtra("type",ChallengeConstant.ArenaType.TYPE_CHALLENGE.ordinal());
                break;
            case WHATCHER:
                //观看者，进入观战界面
                intent.putExtra("type",ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                break;
            default:
                //观看者，进入观战界面
                intent.putExtra("type",ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                break;
        }
        intent.putExtra("arenaId",arenaId);
        intent.putExtra("role",role);
        ctx.startActivity(intent);
    }*/
    class ViewHolder{
        ImageView ivChampion,ivIcon;
        TextView tvName,tvGuradTimes,tvWinRote,tvEranRate;
        Button btnCheckInfo;
        TextView tvStartDate;//开赛时间，已完成的擂台显示
    }
}
