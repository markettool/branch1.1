package org.market.tool.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.TaskFragmentAdapter;
import org.market.tool.ui.FragmentBase;
import org.market.tool.ui.PublishTaskActivity;
import org.market.tool.view.HeaderLayout.onRightImageButtonClickListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskPagerFragment extends FragmentBase {
	
	private ViewPager viewPager;
    private TaskFragmentAdapter adapter=null;
    private List<Fragment> fragments;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_task_pager, null);
		viewPager=(ViewPager) view.findViewById(R.id.viewpager);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTopBarForRight("任务", "发起任务", new onRightImageButtonClickListener() {

			@Override
			public void onClick() {
			    startAnimActivity(PublishTaskActivity.class);
			}
		});
		initData();
	}
	
	private void initData(){
		fragments=new ArrayList<Fragment>();
		TaskFragment fragment=new TaskFragment();
		MyTaskFragment fragment2=new MyTaskFragment();
		fragments.add(fragment);
		fragments.add(fragment2);
		adapter=new TaskFragmentAdapter(getFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
	}
}
