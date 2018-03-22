package com.example.joy.smartbutler;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.joy.smartbutler.fragments.ButlerFragment;
import com.example.joy.smartbutler.fragments.GirlFragment;
import com.example.joy.smartbutler.fragments.UserFragment;
import com.example.joy.smartbutler.fragments.WeChatFragment;
import com.example.joy.smartbutler.ui.BaseActivity;
import com.example.joy.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private List<String> mListTitle;
    private List<Fragment> mListFragments;
    private final int INDEX=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();


    }


    private void initData() {
        mListTitle = new ArrayList<>();
        mListTitle.add(getResources().getString(R.string.string_butler));
        mListTitle.add(getResources().getString(R.string.string_wechat));
        mListTitle.add(getResources().getString(R.string.string_girl));
        mListTitle.add(getResources().getString(R.string.string_user));

        mListFragments = new ArrayList<>();
        mListFragments.add(new ButlerFragment());
        mListFragments.add(new WeChatFragment());
        mListFragments.add(new GirlFragment());
        mListFragments.add(new UserFragment());
    }

    private void initView() {
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);
        mFab = findViewById(R.id.mFab);
        mFab.setVisibility(View.GONE);

        //预加载
        mViewPager.setOffscreenPageLimit(mListFragments.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                L.d(position+"");
                if(position==INDEX){
                    mFab.setVisibility(View.GONE);
                }else{
                    mFab.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mListTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
