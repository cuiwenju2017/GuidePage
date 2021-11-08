package com.example.guidepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.guidepage.databinding.ActivitySplashBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.mmkv.MMKV;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    //是否是第一次使用
    private boolean isFirstUse;
    private MMKV kv = MMKV.defaultMMKV();

    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //执行一次后销毁本页面
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        ImmersionBar.with(this).init();//沉浸状态栏

        isFirstUse = kv.decodeBool("isFirstUse", true);
        if (isFirstUse) {
            startActivity(new Intent(this, GuideActivity.class));
            finish();
        } else {
            handler.sendEmptyMessageDelayed(0, 1000 * 2);
        }
    }

    @Override
    public void onBackPressed() {

    }
}