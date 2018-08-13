package com.wowo.wowo.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.wowo.wowo.Appliction.wowoAppliction;
import com.wowo.wowo.Adapter.ConversationListAdapterEx;
import com.wowo.wowo.Appliction.wowoAppliction;
import com.wowo.wowo.Base.BaseActivity;
//import com.wowo.wowo.Message.ConversationListFragment;
//import com.wowo.wowo.Message.SealAppContext;
//import com.wowo.wowo.Message.SystemOrdActivity;
//import com.wowo.wowo.Order.OrderFragment;
//import com.wowo.wowo.Personal.PersonalFragment;
import com.wowo.wowo.Fragment.FriendFragment;
import com.wowo.wowo.Fragment.HomepageFragment;
import com.wowo.wowo.Fragment.PersonalFragment;
import com.wowo.wowo.Fragment.NewsFragment;
import com.wowo.wowo.Message.SealAppContext;
import com.wowo.wowo.Model.PicAndNickModel;
import com.wowo.wowo.Model.RongCloudTokenModel;
import com.wowo.wowo.Model.UserModel;
import com.wowo.wowo.R;
//import com.wowo.wowo.Request.HomepageFragment;
import com.wowo.wowo.Utils.BroadcastReceiver.IListener;
import com.wowo.wowo.Utils.BroadcastReceiver.ListenerManager;
import com.wowo.wowo.Utils.Encrypt;
import com.wowo.wowo.Utils.GetResourcesUtil;
import com.wowo.wowo.Utils.SharedPreferencesManager;
import com.wowo.wowo.Views.NoScrollViewPager;
import com.wowo.wowo.Views.SweetAlert.SweetAlertDialog;
//import com.wowo.wowo.rxjava.BaseResponse;
//import com.wowo.wowo.rxjava.ProgressSubscriber;
//import com.wowo.wowo.rxjava.RetrofitAPIManager;
//import com.wowo.wowo.rxjava.SubscriberOnNextListener;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

import static com.wowo.wowo.Appliction.wowoAppliction.mContext;



public class HomePageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, IUnReadMessageObserver, IListener {
    private static final String TAG = HomePageActivity.class.getSimpleName();

    private ImageView mIvHomepage, mIvMsg, mIvNews, mIvMe;
    private TextView mTvHomepage,  mTvMsg, mTvNews, mTvMe;
    private io.rong.imkit.fragment.ConversationListFragment mConversationListFragment = null;
    private Conversation.ConversationType[] mConversationsTypes = null;
    public static NoScrollViewPager mViewPager;
    private List<Fragment> mFragment = new ArrayList<>();
    private long mExitTime;//退出时间
    private String connectResultId;
    private String mToken;
    private int mStatus;
    private SweetAlertDialog dialog;
    private UserModel model;
    private PicAndNickModel picAndNickModel;
    private int getDataFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        findView();
        init();
//        setAlias();//设置别名
//        setRongYun();//设置融云
        Connect();//连接融云服务器
        if (isFirstOpen()) {
            setPermission();//权限申请
        }
        SealAppContext.init(this);
        changeTextViewColor();
        changeSelectedTabState(0);
        initMainViewPager();
    }

    private void init() {
        Intent intent = getIntent();
        model = SharedPreferencesManager.getInstance().getUserData(this);
    }

    private void setPermission() {
        // 申请权限。
        AndPermission.with(this)
                .permission(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("permissions", "onGranted");
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("permissions", "onDenied");
                        if (AndPermission.hasAlwaysDeniedPermission(HomePageActivity.this, permissions)) {
                            // 这些权限被用户总是拒绝。
                            final SettingService settingService = AndPermission.permissionSetting(mContext);
                            dialog = new SweetAlertDialog(HomePageActivity.this);
                            dialog.setTitleText("权限申请");
                            dialog.setContentText("您需要开启悬浮窗权限!" + "\n" + "否则不能接收到推送!!");
                            dialog.setConfirmText("去设置");
                            dialog.setCancelText("不设置");
                            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    // 如果用户同意去设置：
                                    settingService.execute();
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    // 如果用户不同意去设置：
                                    settingService.cancel();
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                })
                .start();

    }

    private Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions,
                                  RequestExecutor executor) {
            // 这里使用一个Dialog询问用户是否继续授权。
            // 如果用户继续：
            executor.execute();

            // 如果用户中断：
            executor.cancel();
        }
    };

//    private void setRongYun() {
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(final String s) {
//                SubscriberOnNextListener mListener = new SubscriberOnNextListener<BaseResponse<PicAndNickModel>>() {
//                    @Override
//                    public void onNext(BaseResponse<PicAndNickModel> baseResponse) {
//                        picAndNickModel = baseResponse.data;
//                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(s, picAndNickModel.getUserNick(), Uri.parse(picAndNickModel.getDefaultAvatar().get(0).getUrl())));
//                        getDataFlag = 1;
//                    }
//                };
//                if (getDataFlag == 0) {
//                    RetrofitAPIManager.getInstance().getPicAndNick(new ProgressSubscriber<BaseResponse<PicAndNickModel>>(mListener, HomePageActivity.this, 0), s, Encrypt.base64(model.getUserId()));
//                }
//                return null;
//            }
//        }, true);
//    }

//    private void setAlias() {
//        JPushInterface.setAlias(this, model.getUserId(), new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                SubscriberOnNextListener mListener = new SubscriberOnNextListener() {
//                    @Override
//                    public void onNext(Object o) {
//                    }
//                };
//                RetrofitAPIManager.getInstance().addJpush(new ProgressSubscriber
//                        <BaseResponse<String>>(mListener, HomePageActivity.this, 0), JPushInterface.getUdid(getApplicationContext()), model.getUserId(), Encrypt.base64(model.getUserId()));
//            }
//        });
//    }

    HomepageFragment homepageFragment;
    NewsFragment newsFragment;

    private void initMainViewPager() {
        Fragment conversationList = initConversationList();
        homepageFragment = new HomepageFragment();
        newsFragment = new NewsFragment();
        mViewPager = findView(R.id.main_viewpager);
        // mViewPager.setOffscreenPageLimit(1);
        mFragment.add(homepageFragment);
//        mFragment.add(conversationList);
        mFragment.add(new FriendFragment());
        mFragment.add(newsFragment);
        mFragment.add(new PersonalFragment());

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setOnPageChangeListener(this);
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(this));
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .build();
            mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                    Conversation.ConversationType.GROUP,
                    Conversation.ConversationType.PUBLIC_SERVICE,
                    Conversation.ConversationType.APP_PUBLIC_SERVICE,
                    Conversation.ConversationType.SYSTEM
            };
            listFragment.setUri(uri);
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }

    /**
     * 判断是否第一次打开APP
     *
     * @return
     */
    private boolean isFirstOpen() {
        SharedPreferences setting = getSharedPreferences("firstopen", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {//第一次
            setting.edit().putBoolean("FIRST", false).commit();
            return true;
        } else {
            return false;
        }
    }

    private void changeSelectedTabState(int position) {
        switch (position) {
            case 0:
                mTvHomepage.setTextColor(GetResourcesUtil.getColor(this, R.color.main_color));
                mIvHomepage.setImageResource(R.mipmap.ic_home_sel);
                break;
            case 1:
                mTvMsg.setTextColor(GetResourcesUtil.getColor(this, R.color.main_color));
                mIvMsg.setImageResource(R.mipmap.ic_friends_sel);
                break;
            case 2:
                mTvNews.setTextColor(GetResourcesUtil.getColor(this, R.color.main_color));
                mIvNews.setImageResource(R.mipmap.ic_news_sel);
                break;
            case 3:
                mTvMe.setTextColor(GetResourcesUtil.getColor(this, R.color.main_color));
                mIvMe.setImageResource(R.mipmap.ic_my_sel);
                break;
        }
    }

    private void changeTextViewColor() {
        mIvHomepage.setImageResource(R.mipmap.ic_home);
        mIvMsg.setImageResource(R.mipmap.ic_friends);
        mIvNews.setImageResource(R.mipmap.ic_news);
        mIvMe.setImageResource(R.mipmap.ic_my);
        mTvHomepage.setTextColor(Color.parseColor("#929292"));
        mTvMsg.setTextColor(Color.parseColor("#929292"));
        mTvNews.setTextColor(Color.parseColor("#929292"));
        mTvMe.setTextColor(Color.parseColor("#929292"));
    }

    private void findView() {
        mIvHomepage = findView(R.id.tab_img_homepage);
        mIvNews = findView(R.id.tab_img_news);
        mIvMsg = findView(R.id.tab_img_msg);
        mIvMe = findView(R.id.tab_img_me);
        mTvHomepage = findView(R.id.tab_text_homepage);
        mTvNews = findView(R.id.tab_text_news);
        mTvMsg = findView(R.id.tab_text_msg);
        mTvMe = findView(R.id.tab_text_me);
    }

    private void Connect() {
//        SubscriberOnNextListener mListener = new SubscriberOnNextListener<BaseResponse<RongCloudTokenModel>>() {
//            @Override
//            public void onNext(BaseResponse<RongCloudTokenModel> baseResponse) {
//                mToken = baseResponse.data.getToken().trim();
//                if (!TextUtils.isEmpty(mToken)) {
//                    //连接融云服务器
//                    RongIM.connect(baseResponse.data.getToken(), new RongIMClient.ConnectCallback() {
//                        @Override
//                        public void onTokenIncorrect() {
//
//                        }
//
//                        @Override
//                        public void onSuccess(final String userid) {
//                            connectResultId = userid;
//
//                            RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//                                @Override
//                                public boolean onReceived(Message message, int i) {
//                                    // 输出消息类型。
//                                    Log.d("Receive:", message.getObjectName());
//                                    // 此处输出判断是否是文字消息，并输出，其他消息同理。
//                                    if (message.getContent() instanceof TextMessage) {
//                                        final TextMessage textMessage = (TextMessage) message.getContent();
//                                        Log.d("onReceived", "Text Message: " + textMessage.getContent());
//                                    }
//                                    return false;
//                                }
//                            });
//
//                        }
//
//                        @Override
//                        public void onError(RongIMClient.ErrorCode errorCode) {
//
//                        }
//                    });
//                }
//            }
//        };
//        RetrofitAPIManager.getInstance().getToken(new ProgressSubscriber<BaseResponse<RongCloudTokenModel>>(mListener, this, 0), Encrypt.base64(model.getUserId()));

    }

    long firstClick = 0;
    long secondClick = 0;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.rl_homepage://
                if (mViewPager.getCurrentItem() == 0) {
//                    EventBus.getDefault().post(newsFragment.REFRESH);
                    if (firstClick == 0) {
                        firstClick = System.currentTimeMillis();
                    } else {
                        secondClick = System.currentTimeMillis();
                    }
                    if (secondClick - firstClick > 0 && secondClick - firstClick <= 800) {
                        if (mConversationListFragment != null) {
                            mConversationListFragment.focusUnreadItem();
                        }
                        firstClick = 0;
                        secondClick = 0;
                    } else if (firstClick != 0 && secondClick != 0) {
                        firstClick = 0;
                        secondClick = 0;
                    }
                }
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.rl_msg:
                mViewPager.setCurrentItem(1, false);
//                EventBus.getDefault().post(HomepageFragment.REFRESH);
                break;
            case R.id.rl_news:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.rl_me:
                mViewPager.setCurrentItem(3, false);
                break;

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("systemconversation", false)) {
            mViewPager.setCurrentItem(1, false);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTextViewColor();
        changeSelectedTabState(position);
    }

    @Override
    protected void onDestroy() {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
        ListenerManager.getInstance().unRegisterListener(this);//注销广播
        super.onDestroy();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ListenerManager.getInstance().registerListener(this);
    }

    @Override
    public void onCountChanged(int i) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序！",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                wowoAppliction.getInstance().destory();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void notifyAllActivity(String type, String value) {
        if (type != null) {
            if (type.equals("gohome")) {
                mViewPager.setCurrentItem(Integer.parseInt(value), false);
            }
        }
    }
}
