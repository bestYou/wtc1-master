package com.hr.nipuream.luckpan;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.bestyou.wtc1.HintPopupWindow;
import com.bestyou.wtc1.MapActivity;
import com.bestyou.wtc1.R;
import com.example.administrator.huaweiview.MyView;
import com.hr.nipuream.luckpan.view.LuckPanLayout;
import com.hr.nipuream.luckpan.view.RotatePan;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.views.PgyerDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import grap.yyh.nf.com.myapplication.utils.GetToast;
import grap.yyh.nf.com.myapplication.view.BofangView;
import grap.yyh.nf.com.myapplication.view.FlakeView;

public class MainActivity extends AppCompatActivity implements LuckPanLayout.AnimationEndListener{

    //大转盘模块
    private RotatePan rotatePan;
    private LuckPanLayout luckPanLayout;
    private ImageView goBtn;
    private ImageView yunIv;
    private String[] strs ;

    private ImageView go2Btn;

    //抢红包模块
    //图标跳动
    @Bind(R.id.ibtn_show)
    ImageButton ibtn_show;
    @Bind(R.id.ibtn_map)
    ImageButton ibtn_map;
    @Bind(R.id.ibtn_word)
    ImageButton ibtn_word;
    @Bind(R.id.ibtn_run)
    ImageButton ibtn_run;
    //金币掉落动画的主体动画
    private FlakeView flakeView;
    private BofangView iv_onclick;
    private ImageView iv_button_word;
    private ImageView iv_continue;

    //沉浸式状态栏
    Toolbar mToolbar;
    //EditText editTextWord;

    //仿QQ右上角选项弹出
    Button button_hint_pop;
    private HintPopupWindow hintPopupWindow;

    // 动态权限申请，要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    //能量球效果
    private TextView tv;
    MyView mv;
    LinearLayout ll;
    private int energy = 300;   //记录能量值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        strs = getResources().getStringArray(R.array.names);
        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.setAnimationEndListener(this);
        goBtn = (ImageView)findViewById(R.id.go);

        ibtn_map = (ImageButton) findViewById(R.id.ibtn_map);
        go2Btn = (ImageView)findViewById(R.id.go_map);

        //抢红包
        ButterKnife.bind(this);
        flakeView = new FlakeView(com.hr.nipuream.luckpan.MainActivity.this);
        doTreeWaggleAnimation(ibtn_show);
        doWaggleAnimation(ibtn_run);
        doWaggleAnimation(ibtn_map);
        doWaggleAnimation(ibtn_word);

        //沉浸式状态栏
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        //mTextView = (TextView) findViewById(R.id.tvCenter);

        initTopView();

        button_hint_pop = (Button) findViewById(R.id.button_right_top);
        button_hint_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出选项弹窗
                hintPopupWindow.showPopupWindow(v);
            }
        });

        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        strList.add("分享");
        strList.add("反馈");
        strList.add("关于");

        ArrayList<View.OnClickListener> clickList = new ArrayList<>();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "跳转分享页面", Toast.LENGTH_SHORT).show();
                showShare();
            }
        };
        View.OnClickListener clickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "跳转反馈页面", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 检查该权限是否已经获取
                    int i1 = ContextCompat.checkSelfPermission(MainActivity.this, permissions[0]);
                    int i2 = ContextCompat.checkSelfPermission(MainActivity.this, permissions[1]);
                    int i3 = ContextCompat.checkSelfPermission(MainActivity.this, permissions[2]);
                    int i4 = ContextCompat.checkSelfPermission(MainActivity.this, permissions[3]);

                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                    if (i1 != PackageManager.PERMISSION_GRANTED ||
                            i2 != PackageManager.PERMISSION_GRANTED ||
                            i3 != PackageManager.PERMISSION_GRANTED ||
                            i4 != PackageManager.PERMISSION_GRANTED
                            ) {
                        // 如果没有授予该权限，就去提示用户请求
                        startRequestPermission();
                        Toast.makeText(MainActivity.this, "请再次点击反馈按钮~", Toast.LENGTH_SHORT).show();
                    }
                }
                feedback();
            }
        };
        View.OnClickListener clickListener3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Power by Info_Coding Team.", Toast.LENGTH_SHORT).show();
            }
        };
        //第4个点击事件未被触发
        View.OnClickListener clickListener4 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击事件触发44", Toast.LENGTH_SHORT).show();
            }
        };
        clickList.add(clickListener);
        clickList.add(clickListener2);
        clickList.add(clickListener3);
        clickList.add(clickListener4);

        //具体初始化逻辑看下面的图
        hintPopupWindow = new HintPopupWindow(this, strList, clickList);

        //上报 Crash 异常
        PgyCrashManager.register(this);

        //能量球效果
        tv= (TextView) findViewById(R.id.tv);
        mv= (MyView) findViewById(R.id.mv);
        mv.change(energy);
        mv.moveWaterLine();
        ll= (LinearLayout) findViewById(R.id.ll);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.change(energy-10);
                go2Btn.performClick();
            }
        });
        mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.change(energy-10);
                //Toast.makeText(getApplicationContext(), "功能模块待添加", Toast.LENGTH_SHORT).show();
                go2Btn.performClick();
            }
        });
        mv.setOnAngleColorListener(onAngleColorListener);

        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(MainActivity.this);

        // 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造成启动次数偏高
        // 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
        StatService.start(this);
    }

    //反馈模块
    private void feedback(){
        PgyerDialog.setDialogTitleBackgroundColor("#42D5BB");
        PgyerDialog.setDialogTitleTextColor("#ffffff");
        // 以对话框的形式弹出
        PgyFeedback.getInstance().showDialog(MainActivity.this);
    }

    //接口回调实现背景同步色彩
    private MyView.OnAngleColorListener onAngleColorListener=new MyView.OnAngleColorListener() {
        @Override
        public void onAngleColorListener(int red, int green) {
            Color color=new Color();
            int c=color.argb(150, red, green, 0);
            // ll.setBackgroundColor(c);
        }
    };

    // 开始提交请求动态权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
        // 以对话框的形式弹出
        PgyFeedback.getInstance().showDialog(MainActivity.this);
    }

    //转盘开启转动
    public void rotation(View view){
        //go2Btn.setImageDrawable(getResources().getDrawable((R.drawable.btn_down)));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //注释掉的两行可以实现动态图标效果
                //go2Btn.setImageDrawable(getResources().getDrawable((R.drawable.btn_up)));
                luckPanLayout.rotate(-1,1000);
            }
        }, 1000);
    }
    public void showMap(View view){
        //luckPanLayout.rotate(-1,100);
        Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, com.bestyou.gaode.MainActivity.class);
        startActivity(intent);
    }
    public void showWord(View view){
        //luckPanLayout.rotate(-1,100);
        //Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, com.bestyou.timeline3.MainActivity.class);
        Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, com.bestyou.word.activity.MainActivity.class);
        startActivity(intent);
    }
    public void showRed(View view){//绿植界面
        Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, com.ltb.laer.waterview.MainActivity.class);
        startActivity(intent);

//        Toast.makeText(getApplicationContext(), "功能模块待添加",
////                Toast.LENGTH_SHORT).show();

    }
    public void showRun(View view){

        Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, cn.bluemobi.dylan.step.activity.MainActivity.class);
        startActivity(intent);

//        Toast.makeText(getApplicationContext(), "功能模块待添加",
////                Toast.LENGTH_SHORT).show();

    }
    public void showMainTop(View view){
//
//        Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, cn.bluemobi.dylan.step.activity.MainActivity.class);
//        startActivity(intent);

        Toast toast=Toast.makeText(getApplicationContext(), "风里雨里，单词与你@单词训练营", 3000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        //创建图片视图对象
        ImageView imageView= new ImageView(getApplicationContext());
        //设置图片
        imageView.setImageResource(R.mipmap.ic_launcher);
        //获得toast的布局
        LinearLayout toastView = (LinearLayout) toast.getView();
        //设置此布局为横向的
        toastView.setOrientation(LinearLayout.HORIZONTAL);
        //将ImageView在加入到此布局中的第一个位置
        toastView.addView(imageView, 0);
        toast.show();

    }

    @Override
    //转盘结束转动时触发
    public void endAnimation(int position) {
        Toast.makeText(this,"Position = "+position+","+strs[position],Toast.LENGTH_SHORT).show();
        energy = energy-10;
        if(position==0 || position==2 || position==4){
            showPopWindows(ibtn_show, "20", true);
        }else if(position==1){
            showWordPopWindows(ibtn_show, "Geo-Robot", "测量机器人",true);
        }else if(position==3){
            showWordPopWindows(ibtn_show, "IERS", "国际地球自转服务",true);
        }else if(position==5){
            showWordPopWindows(ibtn_show, "Control Survey", "控制测量",true);
        }else if(position==6){
            showWordPopWindows(ibtn_show, "Adjustment", "平差",true);
        }else if(position==7){
            showWordPopWindows(ibtn_show, "Mine Survey", "矿山测量学",true);
        }else{
            showWordPopWindows(ibtn_show, "Marine Survey", "海洋测量",true);
        }
//        }else if(position==6){
//
//        }else if(position==7){
//            showWordPopWindows(ibtn_show, "Adjustment", "平差",true);
//        }

    }

    //跳转地图
    @OnClick({R.id.ibtn_map})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_show:
               /* myPopupWindow.startAnomation(ibtn_show,true).showAtLocation(v, Gravity.CENTER, 0, 0);*/
                Intent intent = new Intent(com.hr.nipuream.luckpan.MainActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        flakeView.resume();
    }

    private PopupWindow pop;
    //弹出单词币效果
    private PopupWindow showPopWindows(View v, String moneyStr, boolean show) {
        View view = this.getLayoutInflater().inflate(R.layout.view_login_reward, null);
        TextView tvTips = (TextView) view.findViewById(R.id.tv_tip);
        TextView money = (TextView) view.findViewById(R.id.tv_money);
        tvTips.setText("恭喜您获得单词财富 ￥" + moneyStr + " ");
        money.setText(moneyStr);
        final LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
        container.removeAllViews();
        //将flakeView 添加到布局中
        container.addView(flakeView);
        //设置背景
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        //设置同时出现在屏幕上的金币数量  建议64以内 过多会引起卡顿
        flakeView.addFlakes(8);
        /**
         * 绘制的类型
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */
        flakeView.setLayerType(View.LAYER_TYPE_NONE, null);
        iv_onclick = (BofangView) view.findViewById(R.id.iv_onclick);
        // iv_onclick.setBackgroundResource(R.drawable.open_red_animation_drawable);
        iv_onclick.startAnation();
        iv_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container!=null){
                    container.removeAllViews();
                }
                pop.dismiss();
                GetToast.useString(getBaseContext(),"恭喜您，抢到红包");

            }
        });
        pop = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.half_color));
        pop.setBackgroundDrawable(dw);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(v, Gravity.CENTER, 0, 0);

        /**
         * 移除动画
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //设置2秒后
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            container.removeAllViews();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        if (!show)
            thread.start();
        //ivOpen指的是需要播放动画的ImageView控件
//        AnimationDrawable animationDrawable = (AnimationDrawable)iv_onclick.getBackground();
//        animationDrawable.start();//启动动画
        MediaPlayer player = MediaPlayer.create(this, R.raw.shake);
        player.start();

       // showWordPopWindows(ibtn_show, "20", true);    //会闪退

        return pop;

    }
    //弹出词卡效果
    private PopupWindow showWordPopWindows(View v, String wordStr,String meanStr, boolean show) {
        View view = this.getLayoutInflater().inflate(R.layout.view_word, null);
        TextView tvWord = (TextView) view.findViewById(R.id.tv_word);
        TextView tvMean = (TextView) view.findViewById(R.id.tv_mean);
        tvWord.setText(wordStr);
        tvMean.setText(meanStr);
        final LinearLayout container = (LinearLayout) view.findViewById(R.id.container_word);
        container.removeAllViews();

        //设置背景
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        /**
         * 绘制的类型
         * @see View.LAYER_TYPE_HARDWARE
         * @see View.LAYER_TYPE_SOFTWARE
         * @see View.LAYER_TYPE_NONE
         */

        iv_continue = (ImageView) view.findViewById(R.id.iv_continue);
        iv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container!=null){
                    container.removeAllViews();
                }
                pop.dismiss();
                GetToast.useString(getBaseContext(),"恭喜您，抢到红包");
            }
        });
        pop = new PopupWindow(view, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.half_color));
        pop.setBackgroundDrawable(dw);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(v, Gravity.CENTER, 0, 0);


        MediaPlayer player = MediaPlayer.create(this, R.raw.shake);
        player.start();
        return pop;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    public static void doWaggleAnimation(View view) {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("Rotation", 0f, -20f, 20f, -20f, 20f, -20f, 20f, -20f, 0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("ScaleX", 0.8f, 0.85f, 0.9f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("ScaleY", 0.8f, 0.85f, 0.9f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, rotation, scaleX, scaleY);
        animator.setDuration(2000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
    public static void doTreeWaggleAnimation(View view) {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("Rotation", 0f, -20f, 20f, -20f, 20f, -20f, 20f, -20f, 0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("ScaleX", 0.8f, 0.85f, 0.9f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("ScaleY", 0.8f, 0.85f, 0.9f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, rotation, scaleX, scaleY);
        animator.setDuration(4000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
    //抢红包end

    //初始化菜单项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //初始化沉浸式界面效果
    private void initTopView() {
        mToolbar.setTitle("Title");
        setSupportActionBar(mToolbar);

        mToolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionMe1:
                        Toast.makeText(getApplicationContext(), "actionMe", Toast.LENGTH_SHORT).show();
                        button_hint_pop.performClick();
                        break;
                }
                return false;
            }
        });

        if (mToolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    //社会化分享模块
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("WordPlay");

        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://wordplay.cf");

        // text是分享文本，所有平台都需要这个字段
        oks.setText("风里雨里，单词与你！");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(R.id.actionMe1);//确保SDcard下面存在此张图片
        oks.setImageUrl("https://goss1.vcg.com/creative/vcg/800/version23/VCG21gic19773202.jpg");

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://wordplay.cf");

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论文本(调试时使用)");

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://wordplay.cf");

        // 启动分享GUI
        oks.show(this);
    }


}
