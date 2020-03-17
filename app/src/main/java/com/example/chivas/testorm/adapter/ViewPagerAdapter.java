package com.example.chivas.testorm.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chivas.testorm.view.fragment.GreenDaoFragment;
import com.example.chivas.testorm.view.fragment.ObjectBoxFragment;
import com.example.chivas.testorm.view.fragment.RealmFragment;
import com.example.chivas.testorm.view.fragment.RoomFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new GreenDaoFragment());
        fragments.add(new ObjectBoxFragment());
        fragments.add(new RealmFragment());
        fragments.add(new RoomFragment());
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
    public Object instantiateItem(ViewGroup vg, int position) {
        fragments.get(position).onCreate(null);
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.get(position).onDestroy();
        super.destroyItem(container, position, object);
    }
}
