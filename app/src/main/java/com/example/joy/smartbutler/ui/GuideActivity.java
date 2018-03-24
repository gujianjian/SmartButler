package com.example.joy.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.joy.smartbutler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2018/3/24.
 */

public class GuideActivity extends AppCompatActivity{

    private ViewPager mViewpager;
    private View view1,view2,view3;
    private List<View> mListData=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        mViewpager = findViewById(R.id.mViewPager);
        view1 = View.inflate(this, R.layout.page_item_one, null);
        view2 = View.inflate(this, R.layout.page_item_two, null);
        view3 = View.inflate(this, R.layout.page_item_three, null);

        mListData.add(view1);
        mListData.add(view2);
        mListData.add(view3);

        mViewpager.setAdapter(new GuideAdapter());
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
