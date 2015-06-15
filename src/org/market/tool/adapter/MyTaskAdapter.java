package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_task_item, null);
			holder = new ViewHolder();
			holder.ivOwnerPic = (CircleImageView) convertView.findViewById(R.id.user_pic);
			holder.tvOwnername = (TextView) convertView.findViewById(R.id.user_name);
			holder.tvTaskContent = (TextView) convertView.findViewById(R.id.opera_content);

			holder.ivTaskPic = (ImageView) convertView.findViewById(R.id.opera_pic);
			holder.btCloseTask=(Button) convertView.findViewById(R.id.close_task);
			holder.tvExecuters=(TextView) convertView.findViewById(R.id.tv_executers);
			holder.tvCategory=(TextView) convertView.findViewById(R.id.category);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final int position = arg0;
		final TaskBean bean=beans.get(position);
		try {
			if (position < beans.size()) {
				
				holder.tvOwnername.setText(bean.getOwnerName());
				holder.tvTaskContent.setText(bean.getTaskContent());

				BmobFile ownerPic=bean.getOwnerPic();
				if(ownerPic!=null){
					ownerPic.loadImageThumbnail(mContext, holder.ivOwnerPic, 60, 60);
				}else{
					holder.ivOwnerPic.setImageResource(R.drawable.wwj_748);
				}
				
				BmobFile operaPic=bean.getTaskPic();
				if(operaPic!=null){
					bitmapUtils.display(holder.ivTaskPic, operaPic.getFileUrl(mContext));
				}else{
					holder.ivTaskPic.setImageBitmap(null);
				}
				if(bean.getApplicants()!=null&&bean.getApplicants().size()!=0){
					holder.tvExecuters.setText(bean.getApplicants().get(0)+" 等"+bean.getApplicants().size()+"人 报名执行任务");
				}
				
			}
		} catch (Exception e) {

//			Log.e("majie", e.getMessage());
		}
		
		if(beans.get(position).getOwnerName().equals(user.getUsername())){
			holder.tvCategory.setText("我发起的");
			holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.blue_press));
		}else{
			holder.tvCategory.setText("我执行的");
			holder.tvCategory.setTextColor(mContext.getResources().getColor(R.color.orange));
		    holder.btCloseTask.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	class ViewHolder {
		CircleImageView ivOwnerPic;
		TextView tvOwnername;
		TextView tvCategory;
		TextView tvTaskContent;
		ImageView ivTaskPic;
		Button btCloseTask;
		TextView tvExecuters;
	}
	
}
