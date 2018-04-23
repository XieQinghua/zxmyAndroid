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
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.weishi.yiye.R;
import com.weishi.yiye.activity.order.SubmitOrderActivity;
import com.weishi.yiye.adapter.CommentsAdapter;
import com.weishi.yiye.adapter.GoodsImagesAdapter;
import com.weishi.yiye.application.YiyeApplication;
import com.weishi.yiye.base.BaseSwipeBackActivity;
import com.weishi.yiye.bean.CommentBean;
import com.weishi.yiye.bean.CommonBean;
import com.weishi.yiye.bean.GoodsDetailBean;
import com.weishi.yiye.common.Api;
import com.weishi.yiye.common.ConfigConstants;
import com.weishi.yiye.common.Constants;
import com.weishi.yiye.common.GoodsConstants;
import com.weishi.yiye.common.ShopConstants;
import com.weishi.yiye.common.StringConstants;
import com.weishi.yiye.common.util.CheckPermission;
import com.weishi.yiye.common.util.CropImageUtils;
import com.weishi.yiye.common.util.GsonUtil;
import com.weishi.yiye.common.util.HttpUtils;
import com.weishi.yiye.common.util.SPUtils;
import com.weishi.yiye.common.util.UIUtil;
import com.weishi.yiye.databinding.ActivityGoodsDetailBinding;
import com.weishi.yiye.view.CustomDialog;
import com.weishi.yiye.view.MapPopupWindow;
import com.weishi.yiye.view.MyScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @Author：xieqinghua
 * @Date：2018/1/11
 * @Description：商品详情
 * @Version:v1.0.0
 *****************************/
public class GoodsDetailActivity extends BaseSwipeBackActivity implements ViewPagerEx.OnPageChangeListener,
        BaseSliderView.OnSliderClickListener, View.OnClickListener, MyScrollView.OnScrollListener {
    private static final String TAG = GoodsDetailActivity.class.getSimpleName();
    private CheckPermission checkPermission;
    private ActivityGoodsDetailBinding goodsDetailBinding;
    private ImageView iv_right;
    private ImageView iv_collect;
    private GoodsDetailBean.DataBean.ProductInfoBean productInfo;

    private CommentsAdapter commentsAdapter;
    private List<CommentBean.DataBean.ListBean> commentsList = new ArrayList<CommentBean.DataBean.ListBean>();

    private GoodsImagesAdapter goodsImagesAdapter;
    private ArrayList<String> imgUrlList = new ArrayList<>();
    private int downPosition = 0;
    private ArrayList<String> imgPathList = new ArrayList<>();

    private ViewGroup.LayoutParams para;
    private int productId;
    private boolean isCollected = false;

    private View line_left, line_center, line_right;
    private TextView tv_left, tv_center, tv_right;
    private RelativeLayout rl_title_top_center, rl_title_top_left, rl_title_top_right;

    private int left;//购买须知
    private int center;//最新评价
    private int right;//图文详情
    private int layout;//标题布局高度

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @Override
    protected void initView() {
        goodsDetailBinding = DataBindingUtil.setContentView(GoodsDetailActivity.this, R.layout.activity_goods_detail);
        line_left = findViewById(R.id.line_left);
        line_center = findViewById(R.id.line_center);
        line_right = findViewById(R.id.line_right);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_center = (TextView) findViewById(R.id.tv_center);
        layout = UIUtil.dip2px(GoodsDetailActivity.this, 58);
        rl_title_top_left = (RelativeLayout) findViewById(R.id.rl_title_top_left);
        rl_title_top_center = (RelativeLayout) findViewById(R.id.rl_title_top_center);
        rl_title_top_right = (RelativeLayout) findViewById(R.id.rl_title_top_right);

        productId = getIntent().getIntExtra(GoodsConstants.KEY_GOODS_ID, 1);
        setTitleCenter("商品详情");
        checkPermission = new CheckPermission(GoodsDetailActivity.this) {
            @Override
            public void permissionSuccess(int requestCode) {
                switch (requestCode) {
                    case CheckPermission.REQUEST_CODE_PERMISSION_PHONE:
                        callPhone();
                        break;
                    case CheckPermission.REQUEST_CODE_PERMISSION_STORAGE:
                        downFile(imgUrlList.get(downPosition), downPosition);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void negativeButton() {
                //Toast.makeText(GoodsDetailActivity.this, "没有权限无法拨打电话", Toast.LENGTH_LONG).show();
            }
        };

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        /**轮播图*/
        para = goodsDetailBinding.sliderLayout.getLayoutParams();
        para.width = ScreenUtils.getScreenWidth();
        para.height = ScreenUtils.getScreenWidth() * 326 / 750;
        goodsDetailBinding.sliderLayout.setLayoutParams(para);

        goodsDetailBinding.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        goodsDetailBinding.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        goodsDetailBinding.sliderLayout.setDuration(10000);
        goodsDetailBinding.sliderLayout.addOnPageChangeListener(this);
        goodsDetailBinding.ivServiceTel.setOnClickListener(this);
        goodsDetailBinding.rlRightDetail.setOnClickListener(this);

        //标题栏三个点击事件
        rl_title_top_right.setOnClickListener(this);
        rl_title_top_left.setOnClickListener(this);
        rl_title_top_center.setOnClickListener(this);
        goodsDetailBinding.rlTitleCenter.setOnClickListener(this);
        goodsDetailBinding.rlTitleLeft.setOnClickListener(this);
        goodsDetailBinding.rlTitleRight.setOnClickListener(this);

        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_right.setVisibility(View.GONE);
        iv_right.setImageResource(R.drawable.icon_dark_share);
        iv_right.setOnClickListener(this);

        iv_collect = (ImageView) findViewById(R.id.iv_collect);
        iv_collect.setVisibility(View.VISIBLE);
        iv_collect.setImageResource(R.drawable.icon_not_collect);
        iv_collect.setOnClickListener(this);

        initCollectView();

        /**评论列表*/
        commentsAdapter = new CommentsAdapter(GoodsDetailActivity.this, R.layout.item_comment);
        goodsDetailBinding.lvGoodsRecommends.setAdapter(commentsAdapter);

        goodsImagesAdapter = new GoodsImagesAdapter(GoodsDetailActivity.this, R.layout.item_goods_image);
        goodsDetailBinding.lvGoodsImages.setAdapter(goodsImagesAdapter);

        //当布局的状态或者控件的可见性发生改变回调的接口
        goodsDetailBinding.mslLayout.setOnScrollListener(this);
        findViewById(R.id.parent_layout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //这一步很重要，使得上面的购买布局和下面的购买布局重合
                onScroll(goodsDetailBinding.mslLayout.getScrollY());

            }
        });
    }

    @Override
    protected void initData() {
        //获取商品信息
        getGoodsDetails();
        //获取评论列表
        getCommentList();
    }

    /**
     * 初始化收藏状态
     */
    private void initCollectView() {
        iv_collect.setImageResource(isCollected ? R.drawable.icon_already_collect : R.drawable.icon_not_collect);
    }

    private void getGoodsDetails() {
        startAnim(null);

        Map<String, Object> mapParams = new HashMap<>();
        if (0 != mSp.getInt(Constants.USER_ID, 0)) {
            mapParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        }
        mapParams.put("productId", productId);
        if (YiyeApplication.locationListBean != null) {
            mapParams.put("userLat", Double.valueOf(YiyeApplication.locationListBean.getLatitude()));
            mapParams.put("userLng", Double.valueOf(YiyeApplication.locationListBean.getLongitude()));
        } else {
            mapParams.put("userLat", 0);
            mapParams.put("userLng", 0);
        }

        HttpUtils.doGet(Api.GET_PRODUCT_INFO, mapParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.e(TAG, result);

                final GoodsDetailBean goodsDetailBean = GsonUtil.GsonToBean(result, GoodsDetailBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != goodsDetailBean) {
                            if (!Api.STATE_SUCCESS.equals(goodsDetailBean.getCode())) {
                                Toast.makeText(GoodsDetailActivity.this, goodsDetailBean.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            productInfo = goodsDetailBean.getData().getProductInfo();
                            if (productInfo != null) {
                                if (productInfo.getAlternateImgList() != null && productInfo.getAlternateImgList().size() != 0) {
                                    for (String name : productInfo.getAlternateImgList()) {
                                        TextSliderView textSliderView = new TextSliderView(GoodsDetailActivity.this);
                                        // initialize a SliderLayout
                                        textSliderView.image(name)
                                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                                .setOnSliderClickListener(GoodsDetailActivity.this);

                                        //add your extra information
//                                            textSliderView.bundle(new Bundle());
//                                            textSliderView.getBundle().putString("extra", productInfo.getProductName());

                                        goodsDetailBinding.sliderLayout.addSlider(textSliderView);
                                    }

                                } else {
                                    TextSliderView textSliderView = new TextSliderView(GoodsDetailActivity.this);
                                    textSliderView.image(R.drawable.icon_default_banner)
                                            .setScaleType(BaseSliderView.ScaleType.Fit)
                                            .setOnSliderClickListener(GoodsDetailActivity.this);
                                    goodsDetailBinding.sliderLayout.addSlider(textSliderView);
                                    goodsDetailBinding.sliderLayout.stopAutoCycle();
                                }
                                //shareTitle, shareText, shareImageUrl, shareUrl;
                                shareTitle = productInfo.getProductName();
                                shareText = productInfo.getProductDetail();
                                shareImageUrl = productInfo.getShowImg();
                                shareUrl = mSp.getString(ConfigConstants.PRODUCT_DETAIL, "") + "?productId=" + productInfo.getId();

                                goodsDetailBinding.tvGoodsName.setText(productInfo.getProductName() + "");
                                goodsDetailBinding.tvSellNum.setText("支持退款 | 已售 " + productInfo.getSellNum());
                                goodsDetailBinding.tvSoldOut.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(productInfo.getPrice()));

                                goodsDetailBinding.btnShopping.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                                            Intent intent = new Intent(GoodsDetailActivity.this, SubmitOrderActivity.class);
                                            intent.putExtra("goods_detail", goodsDetailBean);
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            GoodsDetailActivity.this.overridePendingTransition(R.anim.activity_open, 0);
                                        }
                                    }
                                });
                                goodsDetailBinding.tvDetailName.setText(productInfo.getProductName());
                                goodsDetailBinding.tvDetailPrice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(productInfo.getMarketPrice()));
                                goodsDetailBinding.tvDetailTprice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(productInfo.getMarketPrice()));
                                goodsDetailBinding.tvDetailMprice.setText(getString(R.string.money_unit) + new DecimalFormat("#0.00").format(productInfo.getPrice()));
                                goodsDetailBinding.tvTime.setText(productInfo.getIndate());
                                goodsDetailBinding.tvMsg.setText(productInfo.getReserveInformation());
                                goodsDetailBinding.tvEvent.setText(productInfo.getNotes());
                                goodsDetailBinding.tvGoodsDetail.setText(productInfo.getDescription());

                                if (productInfo.getDetailImgList() != null && productInfo.getDetailImgList().size() != 0) {
//                                    goodsDetailBinding.llDetailImg.setVisibility(View.VISIBLE);
//                                    ViewGroup.LayoutParams para = goodsDetailBinding.llDetailImg.getLayoutParams();
//                                    para.width = ScreenUtils.getScreenWidth();
//                                    para.height = ScreenUtils.getScreenWidth() * 1 / 3;
//                                    goodsDetailBinding.llDetailImg.setLayoutParams(para);
                                    imgUrlList = (ArrayList<String>) productInfo.getDetailImgList();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_STORAGE);
                                    } else {
                                        downFile(imgUrlList.get(downPosition), downPosition);
                                    }
//                                    productDetailImgAdapter.setData(imgUrlList);
//                                    productDetailImgAdapter.notifyDataSetChanged();
                                }

                                goodsDetailBinding.tvGrade.setText(productInfo.getStarLevel() + "分");
                                goodsDetailBinding.rb.setStar((float) productInfo.getStarLevel());
                                goodsDetailBinding.tvShopName.setText(productInfo.getStoreName());
                                goodsDetailBinding.tvShopName.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intentDetail = new Intent(GoodsDetailActivity.this, ShopDetailActivity.class);
                                        intentDetail.putExtra(ShopConstants.KEY_SHOP_ID, productInfo.getStoreId());
                                        startActivity(intentDetail);
                                    }
                                });
                                goodsDetailBinding.tvShopAddress.setText(productInfo.getAddress() + " " +
                                        new DecimalFormat("#0.0").format(productInfo.getDistance() / 1000) + "km");
                                goodsDetailBinding.tvShopAddress.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //跳转地图导航
                                        new MapPopupWindow(GoodsDetailActivity.this,
                                                productInfo.getLat() + "",
                                                productInfo.getLng() + "",
                                                productInfo.getAddress(),
                                                goodsDetailBinding.tvShopAddress);
                                    }
                                });
                            }
                            isCollected = true ? goodsDetailBean.getData().getIsExist() == 1 : false;
                            Log.e(TAG, "isCollected = " + isCollected);
                            initCollectView();

                            stopAnim();
                            //渲染完数据让标题栏获取焦点，ScrollView滚动到顶部
                            goodsDetailBinding.title.setFocusable(true);
                            goodsDetailBinding.title.setFocusableInTouchMode(true);
                            goodsDetailBinding.title.requestFocus();
                            goodsDetailBinding.llGoodsDetail.setVisibility(View.VISIBLE);
                            goodsDetailBinding.layoutViewTop.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void downFile(String url, final int downPosition) {
        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        final String imgPath = CropImageUtils.createImagePath(fileName);
        //判断内存中是否存在改图片
        if (isExists(imgPath)) {
            imgPathList.add(imgPath);
            if (downPosition == (imgUrlList.size() - 1)) {
                //全部图片下载完成
                Log.e(TAG, "全部图片均下载过");
                stopAnim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goodsImagesAdapter.setData(imgPathList);
                        goodsImagesAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                downFile(imgUrlList.get(downPosition + 1), downPosition + 1);
            }
        } else {
            //下载图片
            HttpUtils.downFile(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        File file = new File(imgPath);
                        fos = new FileOutputStream(file);
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        fos.flush();
                        //Log.e(TAG, "保存第" + (downPosition + 1) + "张图片到" + imgPath);
                        imgPathList.add(imgPath);

                        if (downPosition == (imgUrlList.size() - 1)) {
                            //全部图片下载完成
                            Log.e(TAG, "全部图片下载完成");

                            stopAnim();
                            //开始渲染
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goodsImagesAdapter.setData(imgPathList);
                                    goodsImagesAdapter.notifyDataSetChanged();
                                }
                            });

                        } else {
                            downFile(imgUrlList.get(downPosition + 1), downPosition + 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    }
                }
            });
        }
    }

    private boolean isExists(String imgPath) {
        try {
            File f = new File(imgPath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void getCommentList() {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("pageNum", 1);
        mapParams.put("pageSize", 3);
        mapParams.put("productId", productId);

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
                            goodsDetailBinding.tvTotalComment.setText(commentBean.getData().getTotal() + "条评论");
                            goodsDetailBinding.tvTotalComment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(GoodsDetailActivity.this, CommentsListActivity.class);
                                    intent.putExtra("type", "productId");
                                    intent.putExtra("id", productId);
                                    startActivity(intent);
                                }
                            });

                            commentsList = commentBean.getData().getList();
                            commentsAdapter.setData(commentsList);
                            commentsAdapter.notifyDataSetChanged();
                        } else {
                            goodsDetailBinding.lvGoodsRecommends.setVisibility(View.GONE);
                            //goodsDetailBinding.tvCommentEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        Toast.makeText(GoodsDetailActivity.this, slider.getBundle().getString("extra"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                //分享
                showShare();
                break;
            case R.id.iv_service_tel:
                //拨打电话
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_PHONE);
                } else {
                    callPhone();
                }
                break;
            case R.id.iv_collect:
                //若是登录状态下，收藏
                if (SPUtils.getInstance().getBoolean(Constants.IS_LOGIN, false)) {
                    if (isCollected) {
                        cancelCollectProduct();
                    } else {
                        collectProduct();
                    }
                }
                //若是未登录状态下，跳转到登录界面
                else {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_right_detail:
                if (null != productInfo) {
                    Intent intent = new Intent(GoodsDetailActivity.this, GoodsInsideDetailActivity.class);
                    intent.putExtra("productDetail", productInfo.getProductDetail());//文字内容
                    intent.putExtra("detailImgList", (ArrayList<String>) productInfo.getDetailImgList());//图片路径
                    startActivity(intent);
                }
                break;
            case R.id.rl_title_left:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlLeftDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                line_right.setBackgroundColor(getResources().getColor(R.color.white));
                line_center.setBackgroundColor(getResources().getColor(R.color.white));
                tv_left.setTextColor(getResources().getColor(R.color.main_text_color));
                tv_right.setTextColor(getResources().getColor(R.color.grey_text));
                tv_center.setTextColor(getResources().getColor(R.color.grey_text));
                break;
            case R.id.rl_title_center:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlCenterDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.white));
                line_right.setBackgroundColor(getResources().getColor(R.color.white));
                line_center.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                tv_left.setTextColor(getResources().getColor(R.color.grey_text));
                tv_right.setTextColor(getResources().getColor(R.color.grey_text));
                tv_center.setTextColor(getResources().getColor(R.color.main_text_color));
                break;
            case R.id.rl_title_right:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlRightDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.white));
                line_right.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                line_center.setBackgroundColor(getResources().getColor(R.color.white));
                tv_left.setTextColor(getResources().getColor(R.color.grey_text));
                tv_right.setTextColor(getResources().getColor(R.color.main_text_color));
                tv_center.setTextColor(getResources().getColor(R.color.grey_text));
                break;
            case R.id.rl_title_top_left:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlLeftDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                line_right.setBackgroundColor(getResources().getColor(R.color.white));
                line_center.setBackgroundColor(getResources().getColor(R.color.white));
                tv_left.setTextColor(getResources().getColor(R.color.main_text_color));
                tv_right.setTextColor(getResources().getColor(R.color.grey_text));
                tv_center.setTextColor(getResources().getColor(R.color.grey_text));
                break;
            case R.id.rl_title_top_center:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlCenterDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.white));
                line_right.setBackgroundColor(getResources().getColor(R.color.white));
                line_center.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                tv_left.setTextColor(getResources().getColor(R.color.grey_text));
                tv_right.setTextColor(getResources().getColor(R.color.grey_text));
                tv_center.setTextColor(getResources().getColor(R.color.main_text_color));
                break;
            case R.id.rl_title_top_right:
                goodsDetailBinding.mslLayout.smoothScrollTo(0, goodsDetailBinding.rlRightDetail.getTop() - layout);

                line_left.setBackgroundColor(getResources().getColor(R.color.white));
                line_right.setBackgroundColor(getResources().getColor(R.color.main_text_color));
                line_center.setBackgroundColor(getResources().getColor(R.color.white));
                tv_left.setTextColor(getResources().getColor(R.color.grey_text));
                tv_right.setTextColor(getResources().getColor(R.color.main_text_color));
                tv_center.setTextColor(getResources().getColor(R.color.grey_text));
                break;
            default:
                break;
        }
    }

    /**
     * 取消收藏商品
     */
    private void cancelCollectProduct() {
        Map<String, Object> jsonParams = new HashMap<String, Object>();
        jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
        jsonParams.put("productId", productId);
        HttpUtils.doGet(Api.DROP_PORDUCT_COLLECT, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
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
                                Toast.makeText(GoodsDetailActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (StringConstants.STR_TRUE.equals(commonBean.getData().toString())) {
                                Toast.makeText(GoodsDetailActivity.this, "商品取消收藏成功", Toast.LENGTH_SHORT).show();
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
     * 收藏商品
     */
    private void collectProduct() {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("userId", mSp.getInt(Constants.USER_ID, 0));
            jsonParams.put("productId", productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpUtils.doPost(Api.ADD_PORDUCT_COLLECT, jsonParams, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
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
                                Toast.makeText(GoodsDetailActivity.this, commonBean.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (StringConstants.STR_TRUE.equals(commonBean.getData().toString())) {
                                Toast.makeText(GoodsDetailActivity.this, "商品收藏成功", Toast.LENGTH_SHORT).show();
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
        CustomDialog.Builder builder = new CustomDialog.Builder(GoodsDetailActivity.this);
        builder.setMessage(productInfo.getTelephone());
        builder.setTitleVisibility(View.GONE);
        builder.setPositiveButton("呼叫", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + productInfo.getTelephone()));
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

    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, goodsDetailBinding.layoutView.getTop());
        goodsDetailBinding.layoutViewTop.layout(0, mBuyLayout2ParentTop, goodsDetailBinding.layoutViewTop.getWidth(), mBuyLayout2ParentTop + goodsDetailBinding.layoutViewTop.getHeight());
        left = goodsDetailBinding.rlLeftDetail.getTop();
        center = goodsDetailBinding.rlCenterDetail.getTop();
        right = goodsDetailBinding.rlRightDetail.getTop();
        if (right <= scrollY + layout) {
            line_left.setBackgroundColor(getResources().getColor(R.color.white));
            line_right.setBackgroundColor(getResources().getColor(R.color.main_text_color));
            line_center.setBackgroundColor(getResources().getColor(R.color.white));
            tv_left.setTextColor(getResources().getColor(R.color.grey_text));
            tv_right.setTextColor(getResources().getColor(R.color.main_text_color));
            tv_center.setTextColor(getResources().getColor(R.color.grey_text));
        } else if (center <= scrollY + layout) {
            line_left.setBackgroundColor(getResources().getColor(R.color.white));
            line_right.setBackgroundColor(getResources().getColor(R.color.white));
            line_center.setBackgroundColor(getResources().getColor(R.color.main_text_color));
            tv_left.setTextColor(getResources().getColor(R.color.grey_text));
            tv_right.setTextColor(getResources().getColor(R.color.grey_text));
            tv_center.setTextColor(getResources().getColor(R.color.main_text_color));
        } else {
            line_left.setBackgroundColor(getResources().getColor(R.color.main_text_color));
            line_right.setBackgroundColor(getResources().getColor(R.color.white));
            line_center.setBackgroundColor(getResources().getColor(R.color.white));
            tv_left.setTextColor(getResources().getColor(R.color.main_text_color));
            tv_right.setTextColor(getResources().getColor(R.color.grey_text));
            tv_center.setTextColor(getResources().getColor(R.color.grey_text));
        }
    }

    @Override
    public void onScrollEnd(int valueY) {
       /* left = goodsDetailBinding.rlLeftDetail.getTop();
        center = goodsDetailBinding.rlCenterDetail.getTop();
        right = goodsDetailBinding.rlRightDetail.getTop();
        if (right <= valueY+layout) {
            line_left.setVisibility(View.GONE);
            line_center.setVisibility(View.GONE);
            line_right.setVisibility(View.VISIBLE);
            tv_left.setTextColor(getResources().getColor(R.color.grey_text));
            tv_right.setTextColor(getResources().getColor(R.color.main_text_color));
            tv_center.setTextColor(getResources().getColor(R.color.grey_text));
        } else if (center <= valueY+layout) {
            line_left.setVisibility(View.GONE);
            line_center.setVisibility(View.VISIBLE);
            line_right.setVisibility(View.GONE);
            tv_left.setTextColor(getResources().getColor(R.color.grey_text));
            tv_right.setTextColor(getResources().getColor(R.color.grey_text));
            tv_center.setTextColor(getResources().getColor(R.color.main_text_color));
        } else {
            line_left.setVisibility(View.VISIBLE);
            line_center.setVisibility(View.GONE);
            line_right.setVisibility(View.GONE);
            tv_left.setTextColor(getResources().getColor(R.color.main_text_color));
            tv_right.setTextColor(getResources().getColor(R.color.grey_text));
            tv_center.setTextColor(getResources().getColor(R.color.grey_text));
        }
*/
    }

}
