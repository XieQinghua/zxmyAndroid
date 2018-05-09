package com.weishi.yiye.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.GoodsDetailActivity;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.OrderDetailBean;
import com.weishi.yiye.bean.eventbus.ChangeScoreEvent;
import com.weishi.yiye.bean.eventbus.OrderActionEvent;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.databinding.ActivityOrderDetailBinding;
import com.weishi.yiye.view.CustomDialog;
import com.weishi.yiye.view.MapPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/2/24
 * @Description：订单详情
 * @Version:v1.0.0
 *****************************/

public class OrderDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = OrderDetailActivity.class.getSimpleName();
    private ActivityOrderDetailBinding orderDetailBinding;

    private ImageView iv_right;
    private String orderNum, orderStatus;
    private int orderId, useStatus, showStatus;

    private CheckPermission checkPermission;
    private String phoneNumber;
    private OrderDetailBean orderDetailBean;

    @Override
    protected void initView() {
        orderDetailBinding = DataBindingUtil.setContentView(OrderDetailActivity.this, R.layout.activity_order_detail);
        setTitleCenter("订单详情");

        //解决华为虚拟按键遮挡布局底部按键
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
        iv_right.setImageResource(R.drawable.icon_dark_share);
        iv_right.setOnClickListener(this);

        checkPermission = new CheckPermission(OrderDetailActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                callPhone();
            }

            @Override
            public void negativeButton() {
                Toast.makeText(OrderDetailActivity.this, "没有权限无法拨打电话", Toast.LENGTH_LONG).show();
            }
        };

        orderId = getIntent().getIntExtra("orderId", 0);
        orderNum = getIntent().getStringExtra("orderNum");
        useStatus = getIntent().getIntExtra("useStatus", 0);
        orderStatus = getIntent().getStringExtra("orderStatus");
        showStatus = getIntent().getIntExtra("showStatus", 0);

        switch (showStatus) {
            case 1:
                orderDetailBinding.llVerifyInfo.setVisibility(View.GONE);
                orderDetailBinding.rlPayInfo.setVisibility(View.VISIBLE);
                orderDetailBinding.btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderDetailActivity.this, PayScoreActivity.class);
                        intent.putExtra("orderNum", orderDetailBean.getData().getOrderNum());
                        startActivity(intent);
                        finish();
                    }
                });
                orderDetailBinding.btnCancel.setText("取消订单");
                orderDetailBinding.btnCancel.setVisibility(View.VISIBLE);
                orderDetailBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancelOrder();
                    }
                });
                break;
            case 2:
                orderDetailBinding.llVerifyInfo.setVisibility(View.GONE);
                break;
            case 3:
                orderDetailBinding.rlCommentInfo.setVisibility(View.VISIBLE);
                orderDetailBinding.tvComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderDetailActivity.this, CommentActivity.class);
                        // 传评价订单数据
                        intent.putExtra("orderId", orderDetailBean.getData().getId());
                        intent.putExtra("productId", orderDetailBean.getData().getProductId());
                        intent.putExtra("storeId", orderDetailBean.getData().getStoreId());
                        intent.putExtra("showImg", orderDetailBean.getData().getShowImg());
                        intent.putExtra("productName", orderDetailBean.getData().getProductName());
                        intent.putExtra("orderPrice", orderDetailBean.getData().getOrderPrice());
                        intent.putExtra("orderNum", orderDetailBean.getData().getNumber());
                        intent.putExtra("validTime", orderDetailBean.getData().getValidTime());
                        startActivity(intent);
                    }
                });
                orderDetailBinding.tvUseStatus.setText("已使用");
                break;
            case 4:
                orderDetailBinding.tvUseStatus.setText("已使用");
                break;
            case 5:
                orderDetailBinding.tvUseStatus.setText("未使用");
                orderDetailBinding.btnCancel.setText("申请退款");
                orderDetailBinding.btnCancel.setVisibility(View.VISIBLE);
                orderDetailBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        applyForRefund();
                    }
                });
                break;
            case 6:
                orderDetailBinding.tvUseStatus.setText("已使用");
                orderDetailBinding.rlCommentInfo.setVisibility(View.VISIBLE);
                orderDetailBinding.tvComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(OrderDetailActivity.this, CommentActivity.class);
                        // 传评价订单数据
                        intent.putExtra("orderId", orderDetailBean.getData().getId());
                        intent.putExtra("productId", orderDetailBean.getData().getProductId());
                        intent.putExtra("storeId", orderDetailBean.getData().getStoreId());
                        intent.putExtra("showImg", orderDetailBean.getData().getShowImg());
                        intent.putExtra("productName", orderDetailBean.getData().getProductName());
                        intent.putExtra("orderPrice", orderDetailBean.getData().getOrderPrice());
                        intent.putExtra("orderNum", orderDetailBean.getData().getNumber());
                        intent.putExtra("validTime", orderDetailBean.getData().getValidTime());
                        startActivity(intent);
                    }
                });
                break;
            case 7:
            case 8:
            case 9:
                orderDetailBinding.llVerifyInfo.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("orderNum", orderNum);
        mapParams.put("useStatus", useStatus);
        mapParams.put("orderStatus", orderStatus);
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }
        HttpUtils.doGet(Api.GET_ORDER_DETAIL, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, "OrderPayDetail=" + result);

                orderDetailBean = GsonUtil.GsonToBean(result, OrderDetailBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (orderDetailBean != null && Api.STATE_SUCCESS.equals(orderDetailBean.getCode())) {
                            //填充数据
                            fillData(orderDetailBean);
                        }
                    }
                });
            }
        });
    }

    private void fillData(final OrderDetailBean orderDetailBean) {
        orderDetailBinding.sdvOrdersHead.setImageURI(Uri.parse(orderDetailBean.getData().getShowImg()));
        orderDetailBinding.tvGoodsName.setText(orderDetailBean.getData().getProductName());
        orderDetailBinding.tvSoldOut.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(orderDetailBean.getData().getOrderPrice()));
        orderDetailBinding.tvScore.setText("数量：x" + orderDetailBean.getData().getNumber());
        orderDetailBinding.tvServiceTime.setText("有效期至：" + TimeUtils.millis2String(orderDetailBean.getData().getValidTime(),
                new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())));
        orderDetailBinding.rlGoodsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra(GoodsConstants.KEY_GOODS_ID, orderDetailBean.getData().getProductId());
                OrderDetailActivity.this.startActivity(intent);
            }
        });
        orderDetailBinding.tvVerifyCode.setText(orderDetailBean.getData().getUseCode());
        orderDetailBinding.tvShopName.setText(orderDetailBean.getData().getStoreName());
        orderDetailBinding.tvAddress.setText(orderDetailBean.getData().getAddress() + " " +
                new DecimalFormat("#0.00").format(orderDetailBean.getData().getDistance() / 1000) + "km");
        orderDetailBinding.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转地图导航
                new MapPopupWindow(OrderDetailActivity.this,
                        orderDetailBean.getData().getStoreLat() + "",
                        orderDetailBean.getData().getStoreLng() + "",
                        orderDetailBean.getData().getAddress(),
                        orderDetailBinding.tvAddress);
            }
        });
        phoneNumber = orderDetailBean.getData().getPhone();
        orderDetailBinding.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拨打电话
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_PHONE);
                } else {
                    callPhone();
                }
            }
        });

        orderDetailBinding.tvDescription.setText(orderDetailBean.getData().getDescription());

//        orderDetailBinding.tvImageText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OrderDetailActivity.this, GoodsInsideDetailActivity.class);
//                intent.putExtra("productDetail", orderDetailBean.getData().getProductDetail());
//                intent.putExtra("detailImgList", (ArrayList<String>) orderDetailBean.getData().getDetailImgList());
//                startActivity(intent);
//            }
//        });
        orderDetailBinding.tvOrderNumber.setText(orderDetailBean.getData().getOrderNum());
        orderDetailBinding.tvOrderPhone.setText(orderDetailBean.getData().getMobile());
        if (orderDetailBean.getData().getPayTime() != null) {
            orderDetailBinding.tvOrderTime.setText(TimeUtils.millis2String(orderDetailBean.getData().getPayTime(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())));
        }
        if (orderDetailBean.getData().getUseTime() != null) {
            orderDetailBinding.tvUserTime.setText(TimeUtils.millis2String(orderDetailBean.getData().getUseTime(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())));
        }
        orderDetailBinding.tvNumber.setText(orderDetailBean.getData().getNumber() + "");
        orderDetailBinding.tvOrderPrice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(orderDetailBean.getData().getOrderPrice()));
        orderDetailBinding.tvRealPrice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(orderDetailBean.getData().getOrderPrice()));
        orderDetailBinding.llDetailInfo.setVisibility(View.VISIBLE);
    }

    /**
     * 拨打客服电话
     */
    public void callPhone() {
        CustomDialog.Builder builder = new CustomDialog.Builder(OrderDetailActivity.this);
        builder.setMessage(phoneNumber);
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("呼叫", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                //分享
                showShare();
                break;
            default:
                break;
        }
    }

    private void applyForRefund() {
        startAnim(null);

        HttpUtils.doGet(Api.ORDER_REFUND + "/" + orderId, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, "applyForRefund=" + result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            Toast.makeText(OrderDetailActivity.this, "申请退款成功", Toast.LENGTH_SHORT).show();

                            EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.REFUND_SUCCESS));
                            EventBus.getDefault().post(new ChangeScoreEvent(ChangeScoreEvent.REFUND));

                            finish();
                        }
                    }
                });
            }
        });
    }

    private void cancelOrder() {
        startAnim(null);

        HttpUtils.doGet(Api.CANCEL_ORDER + "/" + orderId, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                stopAnim();

                final String result = response.body().string();
                Log.e(TAG, "cancelOrder=" + result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                            Toast.makeText(OrderDetailActivity.this, "取消订单成功", Toast.LENGTH_SHORT).show();

                            EventBus.getDefault().post(new OrderActionEvent(OrderActionEvent.CANCEL_ORDER));

                            finish();
                        }
                    }
                });
            }
        });
    }

    private String shareTitle, shareText, shareImageUrl, shareUrl;

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(shareTitle);
        // titleUrl QQ和QQ空间跳转链接
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareText);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(shareImageUrl);
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(shareUrl);
        // 启动分享GUI
        oks.show(this);
    }
}
