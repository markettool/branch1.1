package org.market.tool.adapter;

import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.base.MyBaseAdapter;
import org.market.tool.adapter.base.ViewHolder;
import org.market.tool.bean.ApplicantBean;
import org.market.tool.bean.Message;
import org.market.tool.bean.OriginTaskBean;
import org.market.tool.bean.SubTaskBean;
import org.market.tool.bean.User;
import org.market.tool.util.MessageUtil;
import org.market.tool.util.ProgressUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class TaskAdapter extends MyBaseAdapter {

	private List<OriginTaskBean> beans;
	
	public TaskAdapter(Context context, List<OriginTaskBean> beans) {
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.task_item, null);
		}
		ImageView ivOwnerPic =ViewHolder.get(convertView, R.id.user_pic);
		TextView tvOwnername =ViewHolder.get(convertView,R.id.user_name);
		TextView tvTaskContent =ViewHolder.get(convertView,R.id.opera_content);
//		LinearLayout llScan =ViewHolder.get(convertView,R.id.ll_feed_like);
//		LinearLayout llComment =ViewHolder.get(convertView,R.id.ll_feed_comment);
		TextView tvScanNum = ViewHolder.get(convertView,R.id.tv_feed_like_num);
		TextView tvCommentNum = ViewHolder.get(convertView,R.id.tv_feed_comment_num);
		TextView tvBailFund =ViewHolder.get(convertView,R.id.bail_fund);

		Button btApplicant = (Button) convertView.findViewById(R.id.bt_applicant);
		ImageView ivTaskPic = (ImageView) convertView.findViewById(R.id.opera_pic);	
		final int position = arg0;
		try {

			if (position < beans.size()) {
				final OriginTaskBean bean=beans.get(position);
				tvOwnername.setText(bean.getOwnerName());
				tvTaskContent.setText(bean.getTaskContent());
				tvScanNum.setText("" + bean.getScanNum());
				tvCommentNum.setText(""+ bean.getCommentNum());
				tvBailFund.setText("担保金额："+ bean.getFund()+" 元");

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
				try {
					if(user.getUsername().equals(bean.getOwnerName())){
						btApplicant.setVisibility(View.GONE);
					}
					else{
						btApplicant.setVisibility(View.VISIBLE);
					}
					ApplicantBean b=dbUtils.findFirst(Selector.from(ApplicantBean.class).where("id", "==", bean.getObjectId()));
				    if(b!=null){
				    	btApplicant.setText("已申请");
				    	btApplicant.setEnabled(false);
				    }else{
				    	btApplicant.setText("申请接任务");
				    	btApplicant.setEnabled(true);
				    }
				} catch (DbException e) {
					e.printStackTrace();
				}
				btApplicant.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						ApplicantBean entity=new ApplicantBean();
						entity.setId(bean.getObjectId());
						try {
							dbUtils.save(entity);
						} catch (DbException e) {
							e.printStackTrace();
						}
						insertSubTaskBean(bean);
						queryUserByName(bean.getOwnerName(), user.getUsername()+" 申请执行任务\n"+bean.getTaskContent());
						notifyDataSetChanged();
					}
				});
			}
		} catch (Exception e) {

//			Log.e("majie", e.getMessage());
		}

		return convertView;
	}
	
	/**
	 * insert subtaskbean
	 */
	private void insertSubTaskBean(OriginTaskBean bean) {
         SubTaskBean p=new SubTaskBean();
         p.setOriginTaskId(bean.getObjectId());
         p.setOriginTaskContent(bean.getTaskContent());
         p.setUsername(user.getUsername());
         ProgressUtil.showProgress(mContext, "");
         p.save(mContext, new SaveListener() {
			
			@Override
			public void onSuccess() {
				ProgressUtil.closeProgress();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				ProgressUtil.closeProgress();
			}
		});
		
	}
	
	@Override
	public void action(User user, String msg) {
		super.action(user, msg);
		String json=MessageUtil.toMessageJson(Message.ENROLL, msg,user.getUsername(),user.getNick());
		push(user, json);
	}

}
