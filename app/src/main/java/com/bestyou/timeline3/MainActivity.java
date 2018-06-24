package com.bestyou.timeline3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bestyou.wtc1.R;
import com.bestyou.wtc1.SplashActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextWord;

    Toolbar mToolbar;
    TextView mTextView;

    //存储列表数据
    List<TimeData> list = new ArrayList<>();
    TimeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        RecyclerView rlView = (RecyclerView) findViewById(R.id.activity_rlview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
       //mTextView = (TextView) findViewById(R.id.tvCenter);
        initView();

        editTextWord = (EditText) findViewById(R.id.editTextWord);

        //初始化数据
        initData();
        // 将数据按照时间排序
        TimeComparator comparator = new TimeComparator();
        Collections.sort(list, comparator);
        // recyclerview绑定适配器
        rlView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeAdapter(this, list);
        rlView.setAdapter(adapter);


    }

    public void searchOnClick(View view){
        //luckPanLayout.rotate(-1,100);
        if (editTextWord.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(MainActivity.this, Edison.class);

            //在Intent对象当中添加一个键值对
            intent.putExtra("key",editTextWord.getText().toString() );
            startActivity(intent);

        }
    }

    private void initData() {
        list.add(new TimeData("20180523", "Geodesy"));
        list.add(new TimeData("20180523", "大地测量学"));

        list.add(new TimeData("20180522", "Photogrammetry"));
        list.add(new TimeData("20180522", "摄影测量学"));
        list.add(new TimeData("20180522", "Remote Sensing(Rs)"));
        list.add(new TimeData("20180522", "遥感"));

        list.add(new TimeData("20180521", "Fixed Error"));
        list.add(new TimeData("20180521", "固定误差"));

        list.add(new TimeData("20180520", "Total Station"));
        list.add(new TimeData("20180520", "全站仪"));

        list.add(new TimeData("20180519", "Satellite Positioning"));
        list.add(new TimeData("20180519", "卫星定位"));
    }


    private void initView() {
        mToolbar.setTitle("Title");
        setSupportActionBar(mToolbar);
//        mToolbar.inflateMenu(R.menu.menu_toolbar);
//        mToolbar.setSubtitle("Subtitle");
//        mToolbar.setLogo(R.mipmap.ic_star_white_24dp);
//        mToolbar.setBackgroundColor(getColor(R.color.colorPrimaryDark));
//        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "back", Toast.LENGTH_SHORT).show();
//            }
//        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionMe:
                        //Toast.makeText(App.getContext(), "actionMe", Toast.LENGTH_SHORT).show();

                        Toast toast=Toast.makeText(getApplicationContext(), "显示带图片的toast", 3000);
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

                        break;
                    case R.id.actionDownload:
                        Toast.makeText(getApplicationContext(), "actionDownload", Toast.LENGTH_SHORT).show();
                        break;
//                    case R.id.actionItem1:
//                        Toast.makeText(App.getContext(), "1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.actionItem2:
//                        Toast.makeText(App.getContext(), "actionItem2", Toast.LENGTH_SHORT).show();
//                        break;
                }
                return false;
            }
        });

        if (mToolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mTextView != null) {
            mTextView.setText("居中OnTitleChange");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }


}
