package com.example.joy.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.joy.smartbutler.MainActivity;
import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2018/3/24.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PAGE_THREE = 2;
    private static final int PAGE_ONE = 0;
    private static final int PAGE_TWO = 1;
    private ViewPager mViewpager;
    private View view1,view2,view3;
    private List<View> mListData=new ArrayList<>();
    private ImageView ivSkip;
    private ImageView iv_point1;
    private ImageView iv_point2;
    private ImageView iv_point3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        mViewpager = findViewById(R.id.mViewPager);
        ivSkip = findViewById(R.id.iv_skip);
        iv_point1 = findViewById(R.id.iv_point1);
        iv_point2 = findViewById(R.id.iv_point2);
        iv_point3 = findViewById(R.id.iv_point3);

        //设置下面导航小图标
        setImagePoint(true,false,false);

        view1 = View.inflate(this, R.layout.page_item_one, null);
        view2 = View.inflate(this, R.layout.page_item_two, null);
        view3 = View.inflate(this, R.layout.page_item_three, null);
        Button btnEnterMain = view3.findViewById(R.id.btn_enter_main);
        btnEnterMain.setOnClickListener(this);

        mListData.add(view1);
        mListData.add(view2);
        mListData.add(view3);

        mViewpager.setAdapter(new GuideAdapter());
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == PAGE_ONE) {
                    setImagePoint(true,false,false);
                }

                if (position == PAGE_TWO) {
                    setImagePoint(false,true,false);
                }

                if(position==PAGE_THREE){
                    setImagePoint(false,false,true);
                    ivSkip.setVisibility(View.GONE);
                }else{
                    ivSkip.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivSkip.setOnClickListener(this);
    }

    private void setImagePoint(boolean checked1, boolean checked2, boolean checked3) {
        if(checked1){
            iv_point1.setImageResource(R.drawable.point_on);
        }else{
            iv_point1.setImageResource(R.drawable.point_off);
        }

        if(checked2){
            iv_point2.setImageResource(R.drawable.point_on);
        }else{
            iv_point2.setImageResource(R.drawable.point_off);
        }

        if (checked3) {
            iv_point3.setImageResource(R.drawable.point_on);
        }else{
            iv_point3.setImageResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_skip:
            case R.id.btn_enter_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(mListData.get(position));
            return mListData.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListData.get(position));
        }
    }
}
