package com.example.guidepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.guidepage.adapter.GuideAdapter;
import com.example.guidepage.databinding.ActivityGuideBinding;
import com.example.guidepage.viewpagetransforme.DepthPageTransformer;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;

/**
 * 引导页
 */
public class GuideActivity extends AppCompatActivity {

    private ActivityGuideBinding binding;

    private int[] mImageIds = new int[]{R.drawable.iv_ydy1, R.drawable.iv_ydy2, R.drawable.iv_ydy3};
    private ArrayList<ImageView> mImageViewList;
    private int mPaintDis;
    private MMKV kv = MMKV.defaultMMKV();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        ImmersionBar.with(this).init();
        binding.startBtn.setVisibility(View.GONE);
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            //创建ImageView把mImgaeViewIds放进去
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mImageIds[i]);
            //添加到ImageView的集合中
            mImageViewList.add(view);
            //小圆点
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.shape_point1);
            //初始化布局参数，父控件是谁，就初始化谁的布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //当添加的小圆点的个数超过一个的时候就设置当前小圆点的左边距为20dp;
                params.leftMargin = 40;
            }
            //设置小灰点的宽高包裹内容
            pointView.setLayoutParams(params);
            //将小灰点添加到LinearLayout中
            binding.llContainer.addView(pointView);
        }
        GuideAdapter adapter = new GuideAdapter(mImageViewList);
        binding.vpGuide.setPageTransformer(true, new DepthPageTransformer()); //添加动画效果
        binding.vpGuide.setAdapter(adapter);

        binding.startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            kv.encode("isFirstUse", false);
            finish();
        });

        //监听布局是否已经完成  布局的位置是否已经确定
        binding.ivPoint.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            binding.ivPoint.getViewTreeObserver().removeOnGlobalFocusChangeListener((oldFocus, newFocus) -> {

            });
            //布局完成了就获取第一个小灰点和第二个之间left的距离
            mPaintDis = binding.llContainer.getChildAt(1).getLeft() - binding.llContainer.getChildAt(0).getLeft();
        });

        //ViewPager滑动Pager监听
        binding.vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当滑到第二个Pager的时候，positionOffset百分比会变成0，position会变成1，所以后面要加上position*mPaintDis
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                //在父布局控件中设置他的leftMargin边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.ivPoint.getLayoutParams();
                params.leftMargin = letfMargin;
                binding.ivPoint.setLayoutParams(params);
            }

            /**
             * 设置按钮最后一页显示，其他页面隐藏
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    binding.startBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.startBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}