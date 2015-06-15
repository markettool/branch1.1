package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.adapter.base.ViewHolder;
import org.market.tool.bean.TaskBean;
import org.market.tool.util.BitmapHelp;
import org.market.tool.view.CircleImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;

public class MyTaskAdapter extends MyBaseAdapter{
	private List<TaskBean> beans;
	public MyTaskAdapter(Context context, List<TaskBean> beans) {
		super(context);
		this.beans = beans;
		
		bitmapUtils=BitmapHelp.getBitmapUtils(context);
	}

	@Override
	public int getCount() {
		return beans.size();
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_task_item, null);
		}
			CircleImageView ivOwnerPic = ViewHolder.get(convertView, R.id.user_pic);
			TextView tvOwnername = ViewHolder.get(convertView,R.id.user_name);
			TextView tvTaskContent = ViewHolder.get(convertView,R.id.opera_content);

			ImageView ivTaskPic = ViewHolder.get(convertView,R.id.opera_pic);
			Button btCloseTask=ViewHolder.get(convertView,R.id.close_task);
			TextView tvExecuters=ViewHolder.get(convertView,R.id.tv_executers);
			TextView tvCategory=ViewHolder.get(convertView,R.id.category);

		final int position = arg0;
		final TaskBean bean=beans.get(position);
		try {
			if (position < beans.size()) {
				
			tvOwnername.setText(bean.getOwnerName());
				tvTaskContent.setText(bean.getTaskContent());

				BmobFile ownerPic=bean.getOwnerPic();
				if(ownerPic!=null){
					ownerPic.loadImageThumbnail(mContext, ivOwnerPic, 60, 60);
				}else{
					ivOwnerPic.setImageResource(R.drawable.wwj_748);
				}
				
				BmobFile operaPic=bean.getTaskPic();
				if(operaPic!=null){
					bitmapUtils.display(ivTaskPic, operaPic.getFileUrl(mContext));
				}else{
					ivTaskPic.setImageBitmap(null);
				}
				if(bean.getApplicants()!=null&&bean.getApplicants().size()!=0){
					tvExecuters.setText(bean.getApplicants().get(0)+" ��"+bean.getApplicants().size()+"�� ����ִ������");
				}
				
			}
		} catch (Exception e) {

//			Log.e("majie", e.getMessage());
		}
		
		if(beans.get(position).getOwnerName().equals(user.getUsername())){
			tvCategory.setText("�ҷ����");
			tvCategory.setTextColor(mContext.getResources().getColor(R.color.blue_press));
		}else{
			tvCategory.setText("��ִ�е�");
			tvCategory.setTextColor(mContext.getResources().getColor(R.color.orange));
		    btCloseTask.setVisibility(View.GONE);
		}
		
		return convertView;
	}

}
