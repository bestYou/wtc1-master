package com.bestyou.wtc1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.bestyou.word.utils.Const;
import com.bestyou.word.utils.DBOpenHelper;
import com.bestyou.word.utils.FileUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=*****");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean exist = sharedPreferences.getBoolean(Const.SP_KEY, false);
        if (!exist) {
            sharedPreferences.edit().putBoolean(Const.SP_KEY, true).apply();
            new com.bestyou.wtc1.SplashActivity.FileTask().execute();
        } else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    startMain();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, Const.DELAY_TIME);
        }
    }

    private void startMain() {
        Intent intent = new Intent(com.bestyou.wtc1.SplashActivity.this, com.hr.nipuream.luckpan.MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void initTable() {
        DBOpenHelper dbOpenHelper = DBOpenHelper.getInstance(this);
        SQLiteDatabase database = dbOpenHelper.getDatabase();
        database.execSQL("create table if not exists TABLE_UNIT (" +
                "Unit_Key integer not null," +
                "Unit_Time integer not null default 0," +
                "Cate_Key text references TABLE_META(Meta_Key)" +
                ");");
        for (String metaKey : Const.META_KEYS) {
            Cursor cursor = database.rawQuery("select Meta_UnitCount from TABLE_META where Meta_Key=?;"
                    , new String[]{metaKey});
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(cursor.getColumnIndex("Meta_UnitCount"));
                for (int i = 1; i <= count; i++) {
                    database.execSQL("insert into TABLE_UNIT (Unit_Key,Unit_Time,Cate_Key) " +
                            "values(?,?,?);", new Object[]{i, 0, metaKey});
                }
            }
            cursor.close();
        }
    }

    private class FileTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            FileUtils.writeData(com.bestyou.wtc1.SplashActivity.this);
            initTable();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startMain();
        }
    }



}