package com.jhs.taolibao.code.challenge.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jhs.taolibao.code.challenge.utils.BitmapUtils;
import com.jhs.taolibao.code.challenge.utils.ChallengeTask;
import com.jhs.taolibao.code.challenge.utils.NoDoubleClickListener;
import com.jhs.taolibao.code.simtrade.base.BaseFragment2;
import com.jhs.taolibao.code.simtrade.utils.DateUtils;
import com.jhs.taolibao.code.simtrade.utils.ImageLoaderUtil;
import com.jhs.taolibao.code.user.LoginAndRegitActivity;
import com.jhs.taolibao.utils.BitmapUtil;
import com.jhs.taolibao.utils.DialogUtil;

/**
 * @author jiao on 2016/7/11 18:36
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:ChallengeFragment内置的界面
 */
public class ChallengeChildFragment extends BaseFragment2 {
    private static final String TAG = "ChallengeChildFragment";
    private static final String defaultString = "没有数据";
    private static final String KEY = "ChallengeChild";

    //身份---擂主或挑战者
    private TextView identity;
    //名称
    private TextView tvName;
    //头像
    private ImageView ivAvatar;
    //守擂次数
    private TextView tvGuardTimes;
    //胜率
    private TextView tvWinPercent;
    //按钮
    private Button btn;
    //赛程
    private TextView tvGameTime;
    //背景
    private LinearLayout bg;
    //官方累计收益率
    private TextView tvCumulativeReturns;
    private Arena.Challenger arenaData;

    private ArenaUserInfo userInfo;
    private ChallengeConstant.ArenaRole role;//0 无角色   1，擂主2，挑战者3，观看者
    private int arenaId;
    private int arenaType;
    private int state;//状态 1 等待开始 2比赛中  3 比赛已结

    public static ChallengeChildFragment newInstance(Arena.Challenger arenaData) {
        ChallengeChildFragment challengeChildFragment = new ChallengeChildFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, arenaData);
        challengeChildFragment.setArguments(bundle);

        return challengeChildFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_challenge_child, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        arenaData = (Arena.Challenger) (bundle != null ? bundle.getSerializable(KEY) : null);

        if (null == arenaData) {
            return;
        }
        arenaId = arenaData.getID();
        arenaType = arenaData.getArenaType();
        userInfo = arenaData.getChampion();
        state = arenaData.getState();
        initView();
        setListener();
        refreshView();

    }

    @Override
    public void OnActCreate(Bundle savedInstanceState) {

    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //setViewClick(R.id.child_btn);
        setViewClick(R.id.child_icon);
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置背景
        //setBgBlur();
    }

    /**
     * 设置模糊背景
     */
    private void setBgBlur() {
        Log.d(TAG, "setBgBlur: ");
        if (null == userInfo) {
            Log.d(TAG, "setBgBlur: null == userInfo");
            return;
        }
        final String imgUrl = ChallengeConstant.URL + userInfo.getIconImg();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap bitmap = BitmapUtil.getBitmap(imgUrl);
                if (null == bitmap) {
                    //bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.img_default_avatar);
                    return;
                }

                final Bitmap finalBitmap = bitmap;
                bg.post(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bitmapRound = BitmapUtils.toRoundCorner(finalBitmap, 8);
                        Drawable drawable = new BitmapDrawable(bitmapRound);
                        bg.setBackgroundDrawable(drawable);
                        bg.getBackground().setAlpha((int) (255 * 0.4));
                    }
                });

            }
        }).start();

    }

    /**
     * 初始化view
     */
    private void initView() {
        bg = (LinearLayout) getView().findViewById(R.id.child_bg);
        identity = (TextView) getView().findViewById(R.id.child_identity);
        tvName = (TextView) getView().findViewById(R.id.child_name);
        ivAvatar = (ImageView) getView().findViewById(R.id.child_icon);
        tvGuardTimes = (TextView) getView().findViewById(R.id.child_time);
        tvWinPercent = (TextView) getView().findViewById(R.id.child_success);
        btn = (Button) getView().findViewById(R.id.child_btn);
        tvGameTime = (TextView) getView().findViewById(R.id.child_game);
        //tvGameTime.setText("page " + String.valueOf(index));

        tvCumulativeReturns = (TextView) getView().findViewById(R.id.tv_cumulativeReturns);
        if (arenaType == 1) {
            identity.setText("官方");
            identity.setTextColor(getContext().getResources().getColor(R.color.Ocean));
            identity.setBackgroundResource(R.drawable.rectangle_blue_bg1);
            btn.setText("查看持仓");
            btn.setBackgroundResource(R.drawable.rectangle_blue_solid_bg);
            getView().findViewById(R.id.ll_guanfang_info).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.ll_leizhu_info).setVisibility(View.GONE);
            tvGameTime.setVisibility(View.INVISIBLE);
        } else {
            tvGameTime.setVisibility(View.VISIBLE);
            getView().findViewById(R.id.ll_guanfang_info).setVisibility(View.GONE);
            getView().findViewById(R.id.ll_leizhu_info).setVisibility(View.VISIBLE);
        }
        setBgBlur();
        if (state == 1 && arenaType != 1) {
            //比赛未开始
            btn.setText("观战/挑战");
        } else {
            //比赛已开始
            if (arenaType != 1) {
                btn.setText("观战");
            }

        }
        if (String.valueOf(userInfo.getUserID()).equals(UserInfoSingleton.getUserId())) {
            btn.setText("守擂");
        }
        btn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (null != UserInfoSingleton.getUserInfo()) {
                    if (null != userInfo) {
                        int userId = userInfo.getUserID();
                        if (arenaType == 1) {
                            //startNewActivity(3);
                            //官方擂台
                            checkOfficalRight();
                        } else {
                            getUserRole(arenaData.getID(), userId);
                        }
                    }
                } else {
                    //未登录。去登录界面
                    startActivity(new Intent(getActivity(), LoginAndRegitActivity.class).putExtra("type", "login"));
                }
            }
        });

    }

    /**
     * 设置数据
     */
    private void refreshView() {
        if (null == arenaData) {
            return;
        }

        if (state == 1) {
            //比赛未开始。显示开赛时间
            tvGameTime.setText(DateUtils.getWeekByTime(arenaData.getStartDateStr()) + "开赛");
        } else {
            //比赛已开始，显示结束时间
            tvGameTime.setText(DateUtils.getWeekByTime(arenaData.getEndDateStr()) + "结束");
        }
        if (null == userInfo) {
            return;
        }
        tvName.setText(userInfo.getAlias());
        ImageLoaderUtil.getInstance().displayFromWeb(
                ChallengeConstant.URL + userInfo.getIconImg(), ivAvatar, ImageLoaderUtil.roundOptions);
        tvGuardTimes.setText(String.valueOf(userInfo.getGuard()));
        tvWinPercent.setText(String.valueOf(userInfo.getWin() + " : " + (userInfo.getLose() + userInfo.getWin())));

        tvCumulativeReturns.setText(userInfo.getCumulativeReturns());

    }

    @Override
    public void OnViewClick(View v) {
        switch (v.getId()) {

            case R.id.child_icon:
                startActivity(
                        new Intent(getActivity(),
                                MyInfoActivity.class).putExtra("userid", userInfo.getUserID()));
                break;

        }
    }

    /**
     * 官方观战扣币确认框
     */
    private void showDialog() {
        final int costPoint = 1000;
        final int type = ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal();
        showCostDialog("查看持仓将扣除" + String.valueOf(costPoint) + "宝币，可持续查看1个月", costPoint, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeCenter.getInstance().setOfficialArena(new ChallengeTask.OnChallengeListener() {
                    @Override
                    public void onSuccess(Object response) {
                        OfficialArenaRight right = (OfficialArenaRight) response;
                        if (right.getCode() == 0) {
                            startActivity(new Intent(getActivity(), PxCookActivity.class).putExtra("userid", userInfo.getUserID()));
                        } else {
                            showToast("宝币扣除失败");
                        }
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        if (!msg.equals("")) {
                            showToast(msg);
                        }
                    }
                });

            }
        });

    }

    /**
     * 进入观战或者挑战界面
     */
    private void startNewActivity() {
        Intent intent = null;
        switch (role) {
            case NO_ROLE:
                //无角色，进入观战、挑战选择界面
                intent = new Intent(getContext(), ChallengeOrLookActivity.class);
                intent.putExtra("state", state);
                if (state == 1) {
                    long startTime = arenaData.getStartDateStr();
                    long curTime = System.currentTimeMillis();
                    intent.putExtra("cutDownTime", startTime - curTime);
                }
                break;
            case CHAMPION:
                //擂主,直接进入观战界面
                if (state == 1) {
                    //弹窗提示“还没有开赛，请及时做好守擂准备”
                    DialogUtil.alertDialogTipsNoDraw(getActivity(), "温馨提示", "还没有开赛，请及时做好守擂准备");
                    return;
                } else {
                    intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                    intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                }

                break;

            case CHANGLLER:
                //挑战者，进入挑战界面
                intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_CHALLENGE.ordinal());
                break;
            case WHATCHER:
                //观看者，进入观战界面
                intent = new Intent(getContext(), ChallengeDetailsActivity.class);
                intent.putExtra("type", ChallengeConstant.ArenaType.TYPE_WHATCH.ordinal());
                break;
        }

        intent.putExtra("role", role.ordinal());
        intent.putExtra("arenaId", arenaId);
        getContext().startActivity(intent);
    }

    /**
     * 获取用户擂台角色
     *
     * @param arenaId
     * @param userId
     */
    private int getUserRole(int arenaId, int userId) {
        //arenaId = 0;

        try {
            ChallengeCenter.getInstance().getUserRole(arenaId, new ChallengeTask.OnChallengeListener() {
                @Override
                public void onSuccess(Object response) {
                    UserRole userRole = (UserRole) response;
                    role = userRole.getRole();
                    startNewActivity();
                }

                @Override
                public void onFailure(String msg, Exception e) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查看是否有观看官方擂台的权限
     */
    private void checkOfficalRight() {
        Log.d(TAG, "checkOfficalRight: ");
        ChallengeCenter.getInstance().checkOfficialArena(new ChallengeTask.OnChallengeListener() {
            @Override
            public void onSuccess(Object response) {
                OfficialArenaRight right = (OfficialArenaRight) response;
                if (right.getCode() == 0) {
                    //有权限直接查看持仓
                    startActivity(new Intent(getActivity(), PxCookActivity.class).putExtra("userid", userInfo.getUserID()));
                } else if (right.getCode() == 1) {
                    //无权限 弹出扣费框
                    showDialog();
                }
            }

            @Override
            public void onFailure(String msg, Exception e) {
                Log.e(TAG, "onFailure: msg" + msg);
            }
        });
    }
}
