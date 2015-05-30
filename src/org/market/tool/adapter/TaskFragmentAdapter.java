package org.market.tool.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TaskFragmentAdapter extends FragmentPagerAdapter {
	
	private String arr[]={"推荐","与我相关"};
	
	private List<Fragment> fragments;

	public TaskFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return arr[position];
	}


}
