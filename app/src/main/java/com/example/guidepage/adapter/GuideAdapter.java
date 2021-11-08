package com.example.guidepage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class GuideAdapter extends PagerAdapter {

    private ArrayList<ImageView> mImageViewList;

    public GuideAdapter(ArrayList<ImageView> mImageViewList) {
        this.mImageViewList = mImageViewList;
    }

    //item的个数
    @Override
    public int getCount() {
        return mImageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //初始化item布局
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = mImageViewList.get(position);
        container.addView(view);
        return view;
    }

    //销毁item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}