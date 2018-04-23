package com.weishi.yiye.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.LoginActivity;
import com.weishi.yiye.activity.TestActivity;
import com.weishi.yiye.activity.mine.InviteFriendActivity;
import com.weishi.yiye.activity.mine.MyCollectActivity;
import com.weishi.yiye.activity.mine.ScoreBuyActivity;
import com.weishi.yiye.activity.mine.SettingActivity;
import com.weishi.yiye.activity.mine.ShopsJoinActivity;
import com.weishi.yiye.activity.mine.SystemMsgActivity;
import com.weishi.yiye.activity.mine.UserInfoActivity;
import com.weishi.yiye.base.BaseFragment;
import com.weishi.yiye.bean.ScoreBalanceBean;
import com.weishi.yiye.bean.UserInfoBean;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.bean.eventbus.ChangeUserInfoEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：我的
 * @Version:v1.0.0
 *****************************/
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = MineFragment.class.getSimpleName();
    private SimpleDraweeView sdv_user_head;
    private LinearLayout ll_user_info;
    private TextView tv_click_login;
    private TextView tv_user_name, tv_user_info;
    private TextView tv_score_balance;
    private TextView tv_score_buy, tv_shops_join, tv_invite_friend, tv_my_collect, tv_setting, tv_system_msg;

    private TextView tv_test;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        sdv_user_head = findView(R.id.sdv_user_head);
        ll_user_info = findView(R.id.ll_user_info);
        tv_click_login = findView(R.id.tv_click_login);
        tv_user_name = findView(R.id.tv_user_name);
        tv_user_info = findView(R.id.tv_user_info);
        sdv_user_head.setOnClickListener(this);
        tv_user_info.setOnClickListener(this);

        tv_score_balance = findView(R.id.tv_score_balance);

        tv_score_buy = findView(R.id.tv_score_buy);
        tv_shops_join = findView(R.id.tv_shops_join);
        tv_invite_friend = findView(R.id.tv_invite_friend);
        tv_my_collect = findView(R.id.tv_my_collect);
        tv_setting = findView(R.id.tv_setting);
        tv_system_msg = findView(R.id.tv_system_msg);
        tv_test = findView(R.id.tv_test);

        tv_click_login.setOnClickListener(this);
        tv_score_buy.setOnClickListener(this);
        tv_shops_join.setOnClickListener(this);
        tv_invite_friend.setOnClickListener(this);
        tv_my_collect.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_system_msg.setOnClickListener(this);
        tv_test.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //未登录状态
        if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            ll_user_info.setVisibility(View.GONE);
            tv_click_login.setVisibility(View.VISIBLE);
            tv_user_info.setVisibility(View.INVISIBLE);
            return;
        } else {
            ll_user_info.setVisibility(View.VISIBLE);
            tv_click_login.setVisibility(View.GONE);
            tv_user_info.setVisibility(View.VISIBLE);
        }
        //设置用户头像
        sdv_user_head.setImageURI(Uri.parse(SPUtils.getInstance().getString(Constants.AVATAR, "")));

        getUserInfo();

        getScoreBalance();
    }

    @Override
    public void onResume() {
        super.onResume();
        //未登录状态
        if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            ll_user_info.setVisibility(View.GONE);
            tv_click_login.setVisibility(View.VISIBLE);
            tv_user_info.setVisibility(View.INVISIBLE);
            sdv_user_head.setImageURI(Uri.parse(""));
            return;
        } else {
            ll_user_info.setVisibility(View.VISIBLE);
            tv_click_login.setVisibility(View.GONE);
            tv_user_info.setVisibility(View.VISIBLE);
            sdv_user_head.setImageURI(Uri.parse(SPUtils.getInstance().getString(Constants.AVATAR, "")));
            getUserInfo();
            getScoreBalance();
        }
    }

    private void getScoreBalance() {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("uid", SPUtils.getInstance().getInt(Constants.USER_ID, 0));

        HttpUtils.doGet(Api.GET_SCORE_BALANCE, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                Log.e(TAG, result);

                final ScoreBalanceBean scoreBalanceBean = GsonUtil.GsonToBean(result, ScoreBalanceBean.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (scoreBalanceBean != null && Api.STATE_SUCCESS.equals(scoreBalanceBean.getCode())) {
                            tv_score_balance.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format((double) scoreBalanceBean.getData().getTotalScore() / 10000));
                        }
                    }
                });
            }
        });
    }

    private void getUserInfo() {
        Map<String, Object> mapParams = new HashMap<>();
        if (SPUtils.getInstance().getInt(Constants.USER_ID, 0) != 0) {
            mapParams.put("userId", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
            mapParams.put("uid", SPUtils.getInstance().getInt(Constants.USER_ID, 0));
        }
        HttpUtils.doGet(Api.GET_USER_INFO, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, result);

                if (getActivity() == null) {
                    return;
                }
                final UserInfoBean userInfoBean = GsonUtil.GsonToBean(result, UserInfoBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(userInfoBean.getCode()) &&
                                userInfoBean != null &&
                                userInfoBean.getData() != null) {
                            if (userInfoBean.getData().getNickName() != null) {
                                tv_user_name.setText(userInfoBean.getData().getNickName() + "(" + userInfoBean.getData().getRoleName() + ")");
                                SPUtils.getInstance().putString(Constants.ROLE_NAME, userInfoBean.getData().getRoleName());
                            } else {
                                tv_user_name.setText("会员:" + userInfoBean.getData().getId() + "(" + userInfoBean.getData().getRoleName() + ")");
                                SPUtils.getInstance().putString(Constants.ROLE_NAME, userInfoBean.getData().getRoleName());
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(ChangeUserInfoEvent userInfoEvent) {
        if (!"".equals(userInfoEvent.getAvatar())) {
            sdv_user_head.setImageURI(Uri.parse(userInfoEvent.getAvatar()));
        }
        if (!"".equals(userInfoEvent.getNickName())) {
            tv_user_name.setText(userInfoEvent.getNickName() + "(" + SPUtils.getInstance().getString(Constants.ROLE_NAME, "") + ")");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateScore(ChangeScoreEvent changeScoreEvent) {
        getScoreBalance();
    }

    @Override
    public void onClick(View view) {
        //未登录状态，点击事件都跳转登录
        if (!SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().overridePendingTransition(R.anim.activity_open, 0);
            return;
        }
        switch (view.getId()) {
            case R.id.sdv_user_head:
            case R.id.tv_user_info:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.tv_score_buy:
                startActivity(new Intent(getActivity(), ScoreBuyActivity.class));
                break;
            case R.id.tv_shops_join:
                startActivity(new Intent(getActivity(), ShopsJoinActivity.class));
                break;
            case R.id.tv_invite_friend:
                startActivity(new Intent(getActivity(), InviteFriendActivity.class));
                break;
            case R.id.tv_my_collect:
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.tv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.tv_system_msg:
                startActivity(new Intent(getActivity(), SystemMsgActivity.class));
                break;
            case R.id.tv_click_login:
                //跳转登录页面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.tv_test:
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            default:
                break;
        }
    }
}
