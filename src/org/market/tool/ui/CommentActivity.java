package org.market.tool.ui;

import java.util.ArrayList;
import java.util.List;

import org.market.tool.R;
import org.market.tool.adapter.CommentAdapter;
import org.market.tool.bean.CommentBean;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.util.ProgressUtil;
import org.market.tool.view.EmoticonsEditText;
import org.market.tool.view.xlist.XListView;
import org.market.tool.view.xlist.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentActivity extends BaseActivity {
	
	private TaskBean taskBean;
	private XListView xlv;
	private RelativeLayout mAdContainer;
	private LinearLayout inputView;
	private TextView tvTask;
	private TextView tvComment;
	private Button btn_chat_emo, btn_chat_send, btn_chat_add,btn_chat_keyboard, btn_speak, btn_chat_voice;
	private EmoticonsEditText edit_user_comment;
	private Button btn_chat;
	
	private int skip;
	
	public static final int FINISH_REFRESHING=0;
	public static final int FINISH_LOADING=1;
	
	private List<CommentBean> commentBeans=new ArrayList<CommentBean>();
	
	private CommentAdapter adapter=null;
	
	private User myuser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		
		initView();
		setAdapter();
		
		setListeners();
		initData();
		queryComments(FINISH_REFRESHING);
	}

	private void setListeners(){
		btn_chat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ProgressUtil.showProgress(CommentActivity.this, "");
				queryUserByName(taskBean.getUsername());
			}
		});
		
		tvComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tvComment.setVisibility(View.GONE);
				btn_chat.setVisibility(View.GONE);
				inputView.setVisibility(View.VISIBLE);
				showSoftKeyboard(edit_user_comment);
			}
		});
		
		btn_chat_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String msg=edit_user_comment.getText().toString();
				if(TextUtils.isEmpty(msg)){
					ShowToast("输入为空");
				}else{
					writeComment(msg, taskBean.getObjectId());
					edit_user_comment.setText("");
				}
			}
		});
		
		xlv.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				Log.e("majie", "refresh");
				commentBeans.clear();
				skip=0;
				queryComments(FINISH_REFRESHING);
			}
			
			@Override
			public void onLoadMore() {
				
			}
		});
	}
	
	protected void initView() {

		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		tvTask=(TextView) findViewById(R.id.tv_task);
		tvComment=(TextView) findViewById(R.id.tv_comment);
		xlv=(XListView) findViewById(R.id.lv);
		inputView=(LinearLayout) findViewById(R.id.inputview);
		
		initBottomView();
	}
	
	private void initBottomView() {
		// 最左边
		btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
		btn_chat_add.setVisibility(View.GONE);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_emo.setVisibility(View.GONE);
		// 最右边
		btn_chat_keyboard = (Button) findViewById(R.id.btn_chat_keyboard);
		btn_chat_voice = (Button) findViewById(R.id.btn_chat_voice);
		btn_chat_voice.setVisibility(View.GONE);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
//		btn_chat_send.setPressed(true);;
		btn_chat_send.setVisibility(View.VISIBLE);
		btn_chat = (Button) findViewById(R.id.btn_chat);
		// 最下面
//		layout_more = (LinearLayout) findViewById(R.id.layout_more);
//		layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
//		layout_add = (LinearLayout) findViewById(R.id.layout_add);
//		initAddView();
//		initEmoView();

		// 最中间
		// 语音框
		btn_speak = (Button) findViewById(R.id.btn_speak);
		// 输入框
		edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
		edit_user_comment.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btn_chat_send.setPressed(true);
				} 
				else {
					btn_chat_send.setPressed(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}

	protected void initData() {

		taskBean=(TaskBean) getIntent().getSerializableExtra("taskBean");
		String operaText=taskBean.getTaskContent();
		tvTask.setText(operaText);
		myuser=BmobUser.getCurrentUser(this, User.class);
		if(myuser==null){
			startAnimActivity(LoginActivity.class);
			finish();
		}
		initTopBarForLeft("任务详情");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		myuser=BmobUser.getCurrentUser(this, User.class);
	}
	
    private void writeComment(String comment,String operaId) {
		
		final CommentBean p2 = new CommentBean();
		p2.setComment(comment);
		p2.setOperaId(operaId);
		
		p2.setUsername(myuser.getUsername());
		p2.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
//				Log.d("bmob", "success  " );
				ShowToast("发表成功");
//				etComment.setText("");
				updateComment(taskBean);
				if(commentBeans.size()==0){
					queryComments(FINISH_REFRESHING);
				}
//				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowToast("创建数据失败：" + msg);
			}
		});
	} 
    
    private void queryComments(final int handle){
		BmobQuery<CommentBean> bmobQuery	 = new BmobQuery<CommentBean>();
//		bmobQuery.setLimit(10);
		bmobQuery.addWhereEqualTo("operaId", taskBean.getObjectId());
//		bmobQuery.order("-likeNum");
//		bmobQuery.setSkip(skip);
		bmobQuery.findObjects(this, new FindListener<CommentBean>() {

			@Override
			public void onSuccess(List<CommentBean> object) {
				Log.e("majie", "查询成功：共"+object.size()+"条数据。");
				skip+=object.size();
				commentBeans.addAll(object);
				
				adapter.notifyDataSetChanged();
				xlv.stopRefresh();
				
			}

			@Override
			public void onError(int code, String msg) {
				Log.e("majie","查询失败："+msg);
				xlv.stopRefresh();
			}
		});
	}
	
	private void setAdapter(){
		adapter=new CommentAdapter(this, commentBeans);
		xlv.setAdapter(adapter);
	}
	
	/**
	 * 更新对象
	 */
	private void updateComment(TaskBean bean) {
		final TaskBean p2 = new TaskBean();
		p2.setCommentNum(bean.getCommentNum()+1);
		p2.update(this, bean.getObjectId(), new UpdateListener() {

			@Override
			public void onSuccess() {
				Log.e("majie", "更新成功：" + p2.getUpdatedAt());
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.e("majie", "更新失败：" + msg);
			}
		});

	}
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK){
//			if(btn_chat.getVisibility()==View.GONE){
//				btn_chat.setVisibility(View.VISIBLE);
//				inputView.setVisibility(View.GONE);
//			}
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	
	private void queryUserByName(String searchName){
		userManager.queryUserByName(searchName, new FindListener<BmobChatUser>() {
	        @Override
	        public void onError(int arg0, String arg1) {
	            ShowToast("发起人存在异常");
	            ProgressUtil.closeProgress();
	        }

	        @Override
	        public void onSuccess(List<BmobChatUser> arg0) {
	        	ProgressUtil.closeProgress();
	            if(arg0!=null && arg0.size()>0){
	            	Intent i=new Intent(CommentActivity.this,ChatActivity.class);
					i.putExtra("user", arg0.get(0));
					startAnimActivity(i);
	            }else{
	                ShowToast("发起人存在异常");
	            }
	        }
	    });
	}
	
}
