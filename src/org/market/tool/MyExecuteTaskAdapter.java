package org.market.tool;

import java.util.List;

import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.adapter.base.ViewHolder;
import org.market.tool.bean.SubTaskBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyExecuteTaskAdapter extends MyBaseAdapter {
	private List<SubTaskBean> stbs;

	public MyExecuteTaskAdapter(Context context,List<SubTaskBean> stbs) {
		super(context);
		this.stbs=stbs;
	}

	@Override
	public int getCount() {
		return stbs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_myexecute_task, null);
			
		}
		TextView tvContent=ViewHolder.get(convertView, R.id.tv_content);
		tvContent.setText(stbs.get(arg0).getOriginTaskContent());
		return convertView;
	}

}
