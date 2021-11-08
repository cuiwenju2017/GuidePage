package com.example.guidepage.viewpagetransforme;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class TurnPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1) {
            page.setRotationY(0);
        } else if (position <= 0) { //[-1,0]a向左滑出
            page.setScaleX(1 - Math.abs(position));//设置缩放
            page.setScaleY(1 - Math.abs(position));
            page.setRotationY(-30 * position); //设置以Y轴旋转动画
        } else if (position <= 1) { //[0,1]b滑入
            page.setTranslationX(-page.getWidth() * position);//设置向左平移动画,page.getWidth()为页面宽度
            page.setScaleX(Math.max(0.7f, Math.abs(position)));
            page.setScaleY(Math.max(0.7f, Math.abs(position)));
            page.setRotationY(-30 * position);
        } else {
            page.setRotationY(0);
        }
    }
}
