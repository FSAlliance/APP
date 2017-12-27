package com.mobile.fsaliance.mine;

import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhangming on 2017/8/2.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    public List<Fragment> fragments;
    private List<String> shareTypes;
    private Context context;

    public FragmentAdapter(Context context, FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.context = context;
        this.fragments = list;
        this.shareTypes = titles;
    }

    /**
     * 返回显示的Fragment总数
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 返回要显示的Fragment的某个实例
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return shareTypes.get(position);

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}