package com.weishi.yiye.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.weishi.yiye.R;
import com.weishi.yiye.adapter.CommentsAdapter;
import com.weishi.yiye.adapter.QueryGoodsAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommentBean;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.QueryProductBean;
import com.weishi.yiye.bean.QueryShopBean;
import com.weishi.yiye.bean.ShopDetailBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.databinding.ActivityShopDetailBinding;
import com.weishi.yiye.view.CustomDialog;
import com.weishi.yiye.view.MapPopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：yezhouyong
 * @Date：2018/1/11
 * @Description：店铺详情
 * @Version:v1.0.0
 *****************************/
public class ShopDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener {
    private static final String TAG = ShopDetailActivity.class.getSimpleName();
    private ActivityShopDetailBinding shopDetailBinding;
    private ViewGroup.LayoutParams para;
    private CheckPermission checkPermission;
    private QueryShopBean shopDetailInfo;
    private QueryGoodsAdapter goodsAdapter;
    private List<QueryProductBean> hotGoodsList = new ArrayList<QueryProductBean>();

    private CommentsAdapter commentsAdapter;
    private List<CommentBean.DataBean.ListBean> commentsList = new ArrayList<CommentBean.DataBean.ListBean>();

    private int shopId;
    private ImageView iv_right;
    private ImageView iv_collect;
    private boolean isCollected = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        shopDetailBinding = DataBindingUtil.setContentView(ShopDetailActivity.this, R.layout.activity_shop_detail);
        shopId = getIntent().getIntExtra(ShopConstants.KEY_SHOP_ID, 1);
        setTitleCenter("店铺详情");
        checkPermission = new CheckPermission(ShopDetailActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                callPhone();
            }

            @Override
            public void negativeButton() {
                Toast.makeText(ShopDetailActivity.this, "没有权限无法拨打电话", Toast.LENGTH_LONG).show();
            }
        };

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        /**轮播图(banner为单张图片，增加图册入口)*/
        para = shopDetailBinding.rlGoodsImg.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 848 / 1080;
        shopDetailBinding.rlGoodsImg.setLayoutParams(para);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) shopDetailBinding.sdvShopsHead.getLayoutParams();
        lp.setMargins(SizeUtils.dp2px(10), para.height - SizeUtils.dp2px(30), 0, 0);

        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.drawable.icon_dark_share);
        iv_right.setOnClickListener(this);

        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_collect.setVisibility(View.VISIBLE);
        iv_collect.setImageResource(R.drawable.icon_not_collect);
        iv_collect.setOnClickListener(this);

        initCollectView();

        shopDetailBinding.rlHotGoods.setOnClickListener(this);

        /**热门商品列表*/
        goodsAdapter = new QueryGoodsAdapter(this, R.layout.item_rec_goods);
        shopDetailBinding.lvHotGoods.setAdapter(goodsAdapter);
        shopDetailBinding.lvHotGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShopDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra(GoodsConstants.KEY_GOODS_ID, hotGoodsList.get(i).getId());
                ShopDetailActivity.this.startActivity(intent);
            }
        });

        /**评论列表*/
        commentsAdapter = new CommentsAdapter(ShopDetailActivity.this, R.layout.item_comment);
        shopDetailBinding.lvComments.setAdapter(commentsAdapter);
    }

    /**
     * 初始化收藏状态
     */
    private void initCollectView() {
        iv_collect.setImageResource(isCollected ? R.drawable.icon_already_collect : R.drawable.icon_not_collect);
    }

    @Override
    protected void initData() {
        getShopDetails();
        //获取评论列表
        getCommentList();
    }

    private void getCommentList() {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("pageNum", 1);
        mapParams.put("pageSize", 3);
        mapParams.put("storeId", shopId);

        HttpUtils.doGet(Api.GET_BATCH_USERINFO_BY_ID, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e(TAG, "comment= " + result);

                final CommentBean commentBean = GsonUtil.GsonToBean(result, CommentBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (commentBean != null &&
                                commentBean.getData() != null &&
                                commentBean.getData().getList() != null &&
                                commentBean.getData().getList().size() != 0) {
                            shopDetailBinding.tvTotalComment.setText(commentBean.getData().getTotal() + "条评论");
                            shopDetailBinding.tvTotalComment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(ShopDetailActivity.this, CommentsListActivity.class);
                                    intent.putExtra("type", "storeId");
                                    intent.putExtra("id", shopId);
                                    startActivity(intent);
                                }
                            });

                            commentsList = commentBean.getData().getList();
                            commentsAdapter.setData(commentsList);
                            commentsAdapter.notifyDataSetChanged();
                        } else {
                            shopDetailBinding.lvComments.setVisibility(View.GONE);
                            shopDetailBinding.tvCommentEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void getShopDetails() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        if (0 != mSp.getInt(Constants.USER_ID, 0)) {
            mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        }
        mapParams.put("storeId", shopId);
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }

        HttpUtils.doGet(Api.GET_STORE_INFOS, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final ShopDetailBean shopDetailBean = GsonUtil.GsonToBean(result, ShopDetailBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != shopDetailBean) {
                            if (!Api.STATE_SUCCESS.equals(shopDetailBean.getCode())) {
                                Toast.makeText(ShopDetailActivity.this, shopDetailBean.getMessage(), Toast.LENGTH_SHORT).show();
                                stopAnim();
                                return;
                            }

                            if (shopDetailBean.getData() != null) {
                                shopDetailInfo = shopDetailBean.getData().getStoreInfo();
                                //头部单张图片
                                if (shopDetailInfo.getImgOssUrl() != null && shopDetailInfo.getImgOssUrl().size() != 0) {
                                    shopDetailBinding.sdvGoodsImg.setImageURI(Uri.parse(shopDetailInfo.getImgOssUrl().get(0)));
                                }
                                shopDetailBinding.tvImgNum.setText(shopDetailBean.getData().getTotal() + "张");
                                shopDetailBinding.tvImgNum.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (shopDetailBean.getData().getTotal() == 0) {
                                            Toast.makeText(ShopDetailActivity.this, "相册中没有图片", Toast.LENGTH_SHORT).show();
                                        } else {
                                            //跳转图册
                                            Intent intent = new Intent(ShopDetailActivity.this, ProductImageActivity.class);
                                            intent.putExtra(ShopConstants.KEY_SHOP_ID, shopDetailInfo.getId());
                                            ShopDetailActivity.this.startActivity(intent);
                                        }
                                    }
                                });

                                //shareTitle, shareText, shareImageUrl, shareUrl;
                                shareTitle = shopDetailInfo.getStoreName();
                                shareText = shopDetailInfo.getIntroduce();
                                shareImageUrl = shopDetailInfo.getStoreLogo();
                                shareUrl = mSp.getString(ConfigConstants.STORE_DETAIL, "") + "?shopId=" + shopDetailInfo.getId();

                                shopDetailBinding.sdvShopsHead.setImageURI(Uri.parse(shareImageUrl));
                                shopDetailBinding.tvShopName.setText(shopDetailInfo.getStoreName());
                                shopDetailBinding.tvShopAddress.setText(shopDetailInfo.getAddress() + " " +
                                        new DecimalFormat("#0.0").format(shopDetailInfo.getDistance() / 1000) + "km");
                                shopDetailBinding.tvShopAddress.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //跳转地图导航
                                        new MapPopupWindow(ShopDetailActivity.this,
                                                shopDetailInfo.getLat() + "",
                                                shopDetailInfo.getLng() + "",
                                                shopDetailInfo.getAddress(),
                                                shopDetailBinding.tvShopAddress);
                                    }
                                });
                                shopDetailBinding.rb.setStar((float) shopDetailInfo.getStarLevel());
                                shopDetailBinding.tvGrade.setText(new DecimalFormat("#0.0").format(shopDetailInfo.getStarLevel()) + "分");
                                shopDetailBinding.ivServiceTel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_PHONE);
                                        } else {
                                            callPhone();
                                        }
                                    }
                                });

                                isCollected = true ? shopDetailBean.getData().getIsExist() == 1 : false;
                                Log.e(TAG, "isCollected = " + isCollected);
                                initCollectView();

                                //热门商品
                                if (shopDetailBean.getData() != null &&
                                        shopDetailBean.getData().getProductInfo() != null &&
                                        shopDetailBean.getData().getProductInfo().size() != 0) {
                                    hotGoodsList = shopDetailBean.getData().getProductInfo();
                                    goodsAdapter.setData(hotGoodsList);
                                    goodsAdapter.notifyDataSetChanged();
                                }

                                stopAnim();
                                //渲染完数据让标题栏获取焦点，ScrollView滚动到顶部
                                shopDetailBinding.title.setFocusable(true);
                                shopDetailBinding.title.setFocusableInTouchMode(true);
                                shopDetailBinding.title.requestFocus();
                                shopDetailBinding.llShopDetail.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                //分享
                showShare();
                break;
            case R.id.iv_collect:
                //若是登录状态下，收藏
                if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                    if (isCollected) {
                        cancelCollectShop();
                    } else {
                        collectShop();
                    }
                }
                //若是未登录状态下，跳转到登录界面
                else {
                    Intent intent = new Intent(ShopDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_hot_goods:
                Intent tempIntent = new Intent(ShopDetailActivity.this, ShopGoodsListActivity.class);
                tempIntent.putExtra(ShopConstants.KEY_SHOP_ID, shopId);
                startActivity(tempIntent);
                break;
            default:
                break;
        }
    }

    /**
     * 取消收藏店铺
     */
    private void cancelCollectShop() {
        Map<String, Object> jsonParams = new HashMap<String, Object>();
        jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        jsonParams.put("storeId", shopId);

        HttpUtils.doGet(Api.DORP_STORE_COLLECT, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != commonBean) {
                            if (!Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                                Toast.makeText(ShopDetailActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (StringConstants.STR_TRUE.equals(commonBean.getData().toString())) {
                                Toast.makeText(ShopDetailActivity.this, "店铺取消收藏成功", Toast.LENGTH_SHORT).show();
                                //修改收藏状态
                                isCollected = false;
                                //修改显示界面
                                initCollectView();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 收藏店铺
     */
    private void collectShop() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("storeId", shopId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.ADD_STORE_COLLECT, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final CommonBean commonBean = GsonUtil.GsonToBean(result, CommonBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != commonBean) {
                            if (!Api.STATE_SUCCESS.equals(commonBean.getCode())) {
                                Toast.makeText(ShopDetailActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (StringConstants.STR_TRUE.equals(commonBean.getData().toString())) {
                                Toast.makeText(ShopDetailActivity.this, "店铺收藏成功", Toast.LENGTH_SHORT).show();
                                //修改收藏状态
                                isCollected = true;
                                //修改显示界面
                                initCollectView();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 拨打客服电话
     */
    public void callPhone() {
        CustomDialog.Builder builder = new CustomDialog.Builder(ShopDetailActivity.this);
        builder.setMessage(shopDetailInfo.getMobile());
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("呼叫", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + shopDetailInfo.getMobile()));
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
