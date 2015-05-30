package org.market.tool.adapter;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.bean.ApplicantBean;
import org.market.tool.bean.TaskBean;
import org.market.tool.view.CircleImageView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class TaskAdapter extends MyBaseAdapter {

	private List<TaskBean> beans;
	
	public TaskAdapter(Context context, List<TaskBean> beans) {
		super(context);
		this.beans = beans;
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
			convertView = mInflater.inflate(R.layout.task_item, null);
			holder = new ViewHolder();
			holder.ivOwnerPic = (CircleImageView) convertView.findViewById(R.id.user_pic);
			holder.tvOwnername = (TextView) convertView.findViewById(R.id.user_name);
			holder.tvTaskContent = (TextView) convertView.findViewById(R.id.opera_content);
			holder.llScan = (LinearLayout) convertView.findViewById(R.id.ll_feed_like);
			holder.llComment = (LinearLayout) convertView.findViewById(R.id.ll_feed_comment);
			holder.tvScanNum = (TextView) convertView.findViewById(R.id.tv_feed_like_num);
			holder.tvCommentNum = (TextView) convertView.findViewById(R.id.tv_feed_comment_num);

			holder.btApplicant = (Button) convertView.findViewById(R.id.bt_applicant);
			holder.ivTaskPic = (ImageView) convertView.findViewById(R.id.opera_pic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final int position = arg0;
		try {

			if (position < beans.size()) {
				holder.tvOwnername.setText(beans.get(position).getOwnerName());
				holder.tvTaskContent.setText(beans.get(position).getTaskContent());
				holder.tvScanNum.setText("" + beans.get(position).getScanNum());
				holder.tvCommentNum.setText(""+ beans.get(position).getCommentNum());

				final TaskBean bean=beans.get(position);
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
				try {
					if(user.getUsername().equals(bean.getOwnerName())){
						holder.btApplicant.setVisibility(View.GONE);
					}
					else{
						holder.btApplicant.setVisibility(View.VISIBLE);
					}
					ApplicantBean b=dbUtils.findFirst(Selector.from(ApplicantBean.class).where("id", "==", bean.getObjectId()));
				    if(b!=null){
				    	holder.btApplicant.setText("已申请");
				    	holder.btApplicant.setEnabled(false);
				    }else{
				    	holder.btApplicant.setText("申请接任务");
				    	holder.btApplicant.setEnabled(true);
				    }
				} catch (DbException e) {
					e.printStackTrace();
				}
				holder.btApplicant.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ApplicantBean entity=new ApplicantBean();
						entity.setId(bean.getObjectId());
						try {
							dbUtils.save(entity);
						} catch (DbException e) {
							e.printStackTrace();
						}
						updateApplicant(bean);
						queryUserByName(bean.getOwnerName(), user.getUsername()+" 申请执行任务\n"+bean.getTaskContent());
						notifyDataSetChanged();
					}
				});
			}
		} catch (Exception e) {

			Log.e("majie", e.getMessage());
		}

		return convertView;
	}
	
	class ViewHolder {
		CircleImageView ivOwnerPic;
		TextView tvOwnername;
		TextView tvTaskContent;
		LinearLayout llScan;
		LinearLayout llComment;
		TextView tvScanNum;
		TextView tvCommentNum;
		ImageView ivTaskPic;
		Button btApplicant;
	}
	
	/**
	 * 更新对象
	 */
	private void updateApplicant(TaskBean bean) {
		final TaskBean p = new TaskBean();
		if(p.getApplicants()==null){
			p.setApplicants(new ArrayList<String>());
		}
		p.getApplicants().add(user.getUsername());
		p.update(mContext, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.e("majie", "更新成功：" + p.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.e("majie", "更新失败：" + msg);
			}
		});

	}

}
