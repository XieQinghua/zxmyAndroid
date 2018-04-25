package com.weishi.yiye.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.FragmentTabAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseActivity;
import com.weishi.yiye.bean.ConfigListBean;
import com.weishi.yiye.bean.LocationListBean;
import com.weishi.yiye.bean.eventbus.LoadingStateEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.LocationUtils;
import com.weishi.yiye.fragment.HomeFragment;
import com.weishi.yiye.fragment.MineFragment;
import com.weishi.yiye.fragment.NearbyFragment;
import com.weishi.yiye.fragment.OrderFragment;
import com.weishi.yiye.view.CustomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/9
 * @Description：
 * @Version:v1.0.0
 *****************************/

public class MainActivity extends BaseActivity implements LocationUtils.LocationUtilsinterface {
    private final static String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Fragment> fragments;
    private RadioGroup myRadioGroup;
    private RadioButton rbHome, rbNearby, rbOrder, rbMine;

    private CheckPermission checkPermission;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        myRadioGroup = (RadioGroup) findViewById(R.id.main_radiogroup);
        rbHome = (RadioButton) findViewById(R.id.maintabs_radio_home);
        rbNearby = (RadioButton) findViewById(R.id.maintabs_radio_nearby);
        rbOrder = (RadioButton) findViewById(R.id.maintabs_radio_order);
        rbMine = (RadioButton) findViewById(R.id.maintabs_radio_mine);

        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new NearbyFragment());
        fragments.add(new OrderFragment());
        fragments.add(new MineFragment());

        new FragmentTabAdapter(MainActivity.this, getSupportFragmentManager(), fragments, R.id.content, myRadioGroup);

        checkPermission = new CheckPermission(MainActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                startLocation();
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finish
                //super.negativeButton();
//                Uri packageURI = Uri.parse("package:" + getPackageName());
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

                Toast.makeText(MainActivity.this, "没有权限无法获取当前位置信息", Toast.LENGTH_LONG).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_LOCATION);
        } else {
            startLocation();
        }
    }

    @Override
    protected void initData() {
        getConfigList();
    }

    private void getConfigList() {
        HttpUtils.doGet(Api.GET_CONFIG_LIST, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final ConfigListBean configListBean = GsonUtil.GsonToBean(result, ConfigListBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(configListBean.getCode())) {
                            if (configListBean.getData() != null && configListBean.getData() != null && configListBean.getData().size() != 0) {
                                for (ConfigListBean.DataBean bean : configListBean.getData()) {
                                    mSp.putString(bean.getKey(), bean.getValue());
                                }
                                checkUpdate();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        int versionCode = AppUtils.getAppVersionCode();
        int configCode = Integer.parseInt(mSp.getString(ConfigConstants.VERSIONCODE, "0"));
        if (configCode > versionCode) {
            CustomDialog.Builder builder = new CustomDialog.Builder(this);
            builder.setMessage("更新内容:\n" + mSp.getString(ConfigConstants.VERSIONDESC, ""));
            builder.setTitle("新版本" + mSp.getString(ConfigConstants.VERSION, ""));
            builder.setTitleVisibility(View.VISIBLE);
            builder.setPositiveButton("立即更新", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri url = Uri.parse(mSp.getString(ConfigConstants.VERSIONADDRESS, ""));
                    intent.setData(url);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    public void startLocation() {
        new LocationUtils(MainActivity.this, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadingState(LoadingStateEvent loadingStateEvent) {
        switch (loadingStateEvent.getType()) {
            case LoadingStateEvent.START_ANIM:
                startAnim(loadingStateEvent.getLoadingText());
                break;
            case LoadingStateEvent.STOP_ANIM:
                stopAnim();
                break;
            default:
                break;
        }
    }

    /**
     * 主页监听按两次返回键退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onLocationSuccess(LocationListBean locationListBean) {
        YiyeApplication.locationListBean = locationListBean;
    }

    @Override
    public void onLocationError(String err) {
//        Toast.makeText(this, err, Toast.LENGTH_LONG).show();
        LogUtils.e(TAG, err);
    }
}
