package com.jhs.taolibao.code.challenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhs.taolibao.R;
import com.jhs.taolibao.app.UserInfoSingleton;
import com.jhs.taolibao.code.challenge.ChallengeCenter;
import com.jhs.taolibao.code.challenge.activity.ChallengeDetailsActivity;
import com.jhs.taolibao.code.challenge.activity.ChallengeOrLookActivity;
import com.jhs.taolibao.code.challenge.activity.MyInfoActivity;
import com.jhs.taolibao.code.challenge.activity.PxCookActivity;
import com.jhs.taolibao.code.challenge.constant.ChallengeConstant;
import com.jhs.taolibao.code.challenge.model.Arena;
import com.jhs.taolibao.code.challenge.model.ArenaUserInfo;
import com.jhs.taolibao.code.challenge.model.OfficialArenaRight;
import com.jhs.taolibao.code.challenge.model.UserRole;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.challenge.utils.NoDoubleClickListener;
import com.jhs.taolibao.code.challenge.view.CostAlertDialog;
import com.jhs.taolibao.code.simtrade.entity.UserInfo;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.utils.DialogUtil;
import com.jhs.taolibao.utils.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * @author jiao on 2016/7/12 12:01
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:挑战,"方格"模式的适配器
 * role:用户在每一个擂台的角色 0 无角色   1，擂主2，挑战者3，观看者
 */
public class ChallengeCheckAdapter extends BaseAdapter {
    private static final String TAG = "ChallengeCheckAdapter";

    private List<Arena.Challenger> datas;
    private Context context;

    public ChallengeCheckAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置数据
     */
    public void setData(List<Arena.Challenger> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (null == datas) {
            return 3;
        } else {
            if (datas.size() % 2 == 0) {
                return datas.size() / 2;
            } else {
                return datas.size() / 2 + 1;
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position * 2);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                    adapter_challengecheck, parent, false);

            holder.identity1 = (TextView) convertView.findViewById(R.id.check_identity0);
            holder.identity2 = (TextView) convertView.findViewById(R.id.check_identity1);
            holder.tvName1 = (TextView) convertView.findViewById(R.id.check_name0);
            holder.tvName2 = (TextView) convertView.findViewById(R.id.check_name1);
            holder.ivAvatar1 = (ImageView) convertView.findViewById(R.id.check_icon0);
            holder.ivAvatar2 = (ImageView) convertView.findViewById(R.id.check_icon1);
            holder.tvGuard1 = (TextView) convertView.findViewById(R.id.check_time0);
            holder.tvGuard2 = (TextView) convertView.findViewById(R.id.check_time1);
            holder.tvWinPercent1 = (TextView) convertView.findViewById(R.id.check_success0);
            holder.tvWinPercent2 = (TextView) convertView.findViewById(R.id.check_success1);
            holder.btn1 = (Button) convertView.findViewById(R.id.check_btn0);
            holder.btn2 = (Button) convertView.findViewById(R.id.check_btn1);
            holder.tvStartTime1 = (TextView) convertView.findViewById(R.id.check_game0);
            holder.tvStartTime2 = (TextView) convertView.findViewById(R.id.check_game1);
            holder.llGuanfangInfo = (LinearLayout) convertView.findViewById(R.id.ll_guanfang_info);
            holder.llLeizhuInfo1 = (LinearLayout) convertView.findViewById(R.id.ll_leizhu_info);
            holder.tvCumulativeReturns = (TextView) convertView.findViewById(R.id.tv_cumulativeReturns);
            holder.layout_item_aream = (LinearLayout) convertView.findViewById(R.id.layout_item_aream);
            if (position == 0) {
                holder.layout_item_aream.setPadding(0, 400, 0, 0);
            }
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (null == datas) {
            return convertView;
        }

        final Arena.Challenger challenger1 = datas.get((position) * 2);
        Arena.Challenger challenger2 = null;
        if (((position) * 2 + 1) <= (datas.size() - 1)) {
            challenger2 = datas.get((position) * 2 + 1);
        }

        if (null != challenger1) {
            //·	状态 1 等待开始 2比赛中  3 比赛已结
            final int state = challenger1.getState();

            ArenaUserInfo userInfo = challenger1.getChampion();
            int arenaType = challenger1.getArenaType();
            if (arenaType == 1) {
                holder.identity1.setText("官方");
                holder.identity1.setTextColor(context.getResources().getColor(R.color.Ocean));
                holder.identity1.setBackgroundResource(R.drawable.rectangle_blue_bg);
                holder.btn1.setText("查看持仓");
                holder.btn1.setTextColor(context.getResources().getColor(R.color.Ocean));
                holder.btn1.setBackgroundResource(R.drawable.rectangle_blue_bg);
                holder.llLeizhuInfo1.setVisibility(View.GONE);
                holder.llGuanfangInfo.setVisibility(View.VISIBLE);
                holder.tvStartTime1.setVisibility(View.INVISIBLE);
            } else {
                holder.identity1.setText("擂主");
                holder.identity1.setTextColor(context.getResources().getColor(R.color.Red));
                holder.identity1.setBackgroundResource(R.drawable.rectangle_red_bg);

                holder.llLeizhuInfo1.setVisibility(View.VISIBLE);
                holder.llGuanfangInfo.setVisibility(View.GONE);

                holder.btn1.setTextColor(context.getResources().getColor(R.color.Red));
                holder.btn1.setBackgroundResource(R.drawable.rectangle_red_bg);


                if (String.valueOf(userInfo.getUserID()).equals(UserInfoSingleton.getUserId())) {
                    //用户是擂主
                    holder.btn1.setText("守擂");
                } else {
                    if (state == 1) {
                        holder.btn1.setText("观战/挑战");
                    } else {
                        holder.btn1.setText("观战");
                    }
                }


                holder.tvStartTime1.setVisibility(View.VISIBLE);
            }
            if (state == 1) {
                holder.tvStartTime1.setText(DateUtils.getWeekByTime(challenger1.getStartDateStr()) + "开赛");
            } else {
                holder.tvStartTime1.setText(DateUtils.getWeekByTime(challenger1.getEndDateStr()) + "结束");
            }
            if (null != userInfo) {
                holder.tvName1.setText(userInfo.getAlias());
                ImageLoaderUtil.getInstance().displayFromWeb(
                        ChallengeConstant.URL + userInfo.getIconImg(), holder.ivAvatar1, ImageLoaderUtil.roundOptions);
                holder.tvGuard1.setText(String.valueOf(userInfo.getGuard()));
                holder.tvWinPercent1.setText(String.valueOf(userInfo.getWin() + " : " + (userInfo.getLose() + userInfo.getWin())));
                holder.tvCumulativeReturns.setText(userInfo.getCumulativeReturns());
                final int userId = userInfo.getUserID();
                holder.btn1.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (null != UserInfoSingleton.getUserInfo()) {
                            if (position == 0) {
                                //showCostDialog(userId,challenger1.getID());
                                checkOfficalRight(userId, challenger1.getID());
                            } else {
                                getUserRole(userId, challenger1);
                            }
                        } else {
                            //未登录。去登录界面
                            context.startActivity(new Intent(context, LoginAndRegitActivity.class).putExtra("type", "login"));
                        }


                    }
                });
                //点击头像 进入个人信息查看
                holder.ivAvatar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parent.getContext().startActivity(
                                new Intent(parent.getContext(), MyInfoActivity.class).putExtra("userid", userId));
                    }
                });
            }


        }
        if (null != challenger2) {
            holder.tvStartTime2.setText(challenger2.getStartDate());
            ArenaUserInfo userInfo = challenger2.getChampion();
            final int state = challenger2.getState();
            if (null != userInfo) {
                if (String.valueOf(userInfo.getUserID()).equals(UserInfoSingleton.getUserId())) {
                    //用户是擂主
                    holder.btn2.setText("守擂");
                    if (state == 1) {
                        holder.tvStartTime2.setText(DateUtils.getWeekByTime(challenger2.getStartDateStr()) + "开赛");
                    } else {
                        holder.tvStartTime2.setText(DateUtils.getWeekByTime(challenger2.getEndDateStr()) + "结束");
                    }
                } else {
                    if (state == 1) {
                        holder.btn2.setText("观战/挑战");
                        holder.tvStartTime2.setText(DateUtils.getWeekByTime(challenger2.getStartDateStr()) + "开赛");
                    } else {
                        holder.btn2.setText("观战");
                        holder.tvStartTime2.setText(DateUtils.getWeekByTime(challenger2.getEndDateStr()) + "结束");
                    }
                }
                if (null != userInfo) {
                    final int userId = userInfo.getUserID();
                    holder.tvName2.setText(userInfo.getAlias());
                    ImageLoaderUtil.getInstance().displayFromWeb(
                            ChallengeConstant.URL + userInfo.getIconImg(), holder.ivAvatar2, ImageLoaderUtil.roundOptions);
                    holder.tvGuard2.setText(String.valueOf(userInfo.getGuard()));
                    holder.tvWinPercent2.setText(String.valueOf(userInfo.getWin() + " : " + (userInfo.getLose() + userInfo.getWin())));
                    final Arena.Challenger finalChallenger = challenger2;
                    holder.btn2.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            if (null != UserInfoSingleton.getUserInfo()) {
                                getUserRole(userId, finalChallenger);
                            } else {
                                //未登录。去登录界面
                                context.startActivity(new Intent(context, LoginAndRegitActivity.class).putExtra("type", "login"));
                            }
                        }
                    });
                    holder.ivAvatar2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            parent.getContext().startActivity(
                                    new Intent(parent.getContext(), MyInfoActivity.class).putExtra("userid", userId));
                        }
                    });
                }

            }
        }


        return convertView;
    }

    /**
     * 官方 观战扣费提示框
     */
    private void showCostDialog(final int userId, final int arenaId) {
        //获取总宝币数
        ChallengeCenter.getInstance().getTotalPoint(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                UserInfo userInfo = (UserInfo) response;
                final int totalPoint = userInfo.getUser().getUserTotalPoint();
                final int costPoint = 1000;
                final int type = ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal();
                //扣费弹出框
                new CostAlertDialog(context).builder()
                        .setTotalCoin(String.valueOf(totalPoint))
                        .setMsg("观战将扣除" + costPoint + "宝币")
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //challengeOrWatch(type);
                                if (totalPoint < costPoint) {
                                    ToastUtil.showToast(context, "宝币总数不够");
                                } else {
                                    ChallengeCenter.getInstance().setOfficialArena(
                                            new ChallengeTask.OnChallengeListener() {
                                                @Override
                                                public void onSuccess(Object response) {
                                                    OfficialArenaRight right = (OfficialArenaRight) response;
                                                    if (right.getCode() == 0) {
                                                        context.startActivity(
                                                                new Intent(context, PxCookActivity.class).putExtra("userid", userId));
                                                    }
                                                }

                                                @Override
                                                public void onFailure(String msg, Exception e) {
                                                    if (!msg.equals("")) {
                                                        ToastUtil.showToast(context, msg);
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
    }

    /**
     * 获取用户擂台角色
     *
     * @param userId
     */
    private void getUserRole(int userId, final Arena.Challenger challenger) {

        try {
            ChallengeCenter.getInstance().getUserRole(challenger.getID(), new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    UserRole userRole = (UserRole) response;
                    ChallengeConstant.ArenaRole role = userRole.getRole();
                    startNewActivity(role, challenger);
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户的擂台角色进入进入相应的观战或者挑战界面
     *
     * @param role 用户的角色
     */
    private void startNewActivity(ChallengeConstant.ArenaRole role, Arena.Challenger challenger) {
        int arenaId = challenger.getID();
        int state = challenger.getState();

        Intent intent = null;
        switch (role) {
            case NO_ROLE:
                //无角色，进入观战、挑战选择界面
                intent = new Intent(context, ChallengeOrLookActivity.class);
                intent.putExtra("state", state);
                if (state == 1) {
                    long startTime = challenger.getStartDateStr();
                    long curTime = System.currentTimeMillis();
                    intent.putExtra("cutDownTime", startTime - curTime);
                }
                break;
            case CHAMPION:

                if (state == 1) {
                    //弹窗提示“还没有开赛，请及时做好守擂准备”
                    DialogUtil.alertDialogTipsNoDraw(context, "温馨提示", "还没有开赛，请及时做好守擂准备");
                    return;
                } else {
                    //擂主,直接进入观战界面
                    intent = new Intent(context, ChallengeDetailsActivity.class);
                    intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_WHATCH);
                }


                break;
            case CHANGLLER:
                //挑战者，进入挑战界面
                intent = new Intent(context, ChallengeDetailsActivity.class);
                intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_CHALLENGE);
                break;
            case WHATCHER:
                //观看者，进入观战界面
                intent = new Intent(context, ChallengeDetailsActivity.class);
                intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_WHATCH);
                break;
        }
        intent.putExtra("arenaId", arenaId);
        intent.putExtra("role", role.ordinal());
        context.startActivity(intent);
    }

    /**
     * 查看是否有观看官方擂台的权限
     */
    private void checkOfficalRight(final int userId, final int arenaId) {
        ChallengeCenter.getInstance().checkOfficialArena(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                OfficialArenaRight right = (OfficialArenaRight) response;
                if (right.getCode() == 0) {
                    //有权限直接查看持仓
                    context.startActivity(new Intent(context, PxCookActivity.class).putExtra("userid", userId));
                } else if (right.getCode() == 1) {
                    //无权限 弹出扣费框
                    showCostDialog(userId, arenaId);
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }

    class ViewHolder {
        //0代表左边的,1代表右边的
        //身份---擂主或官方
        private TextView identity1, identity2;
        //名称
        private TextView tvName1, tvName2;
        //头像
        private ImageView ivAvatar1, ivAvatar2;
        //守擂次数
        private TextView tvGuard1, tvGuard2;
        //胜率
        private TextView tvWinPercent1, tvWinPercent2;
        //按钮
        private Button btn1, btn2;
        //赛程
        private TextView tvStartTime1, tvStartTime2;
        //官方累计收益率
        LinearLayout llLeizhuInfo1, llGuanfangInfo;
        TextView tvCumulativeReturns;

        LinearLayout layout_item_aream;
    }
}
