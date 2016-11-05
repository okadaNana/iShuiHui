package com.lufficc.ishuhui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lufficc.ishuhui.fragment.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lufficc on 2016/10/28.
 */

public class CategoriesFragmentsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>(3);

    public CategoriesFragmentsAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_HOT));
        fragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_MOUSE));
        fragments.add(CategoryFragment.newInstance(CategoryFragment.CLASSIFY_ID_SAME));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).toString();
    }
}