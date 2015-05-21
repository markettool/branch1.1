//package org.market.tool.ui;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.market.tool.R;
//import org.market.tool.bean.OperaBean;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.BmobUser;
//import cn.bmob.v3.listener.FindListener;
//import cn.bmob.v3.listener.SaveListener;
//import cn.bmob.v3.listener.UpdateListener;
//
//public class CommentActivity extends BaseActivity {
//	
//	private OperaBean operaBean;
//	private ListView lv;
//	private RefreshableView mRefreshableView;
//	private RelativeLayout mAdContainer;
//	private InputView inputView;
//	private TextView tvOpera;
//	
//	private int skip;
//	
//	public static final int FINISH_REFRESHING=0;
//	public static final int FINISH_LOADING=1;
//	
//	private List<CommentBean> commentBeans=new ArrayList<CommentBean>();
//	
//	private CommentAdapter adapter=null;
//	
//	private MyUser myuser;
//	
////	private int screenWidth;
////	private int screenHeight;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_comment);
//		
//		initView();
//		setAdapter();
//		
//		setListeners();
//		initData();
//		queryComments(FINISH_REFRESHING);
//	}
//
//	private void setListeners(){
//		
//        mRefreshableView.setOnRefreshListener(new PullToRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				Log.e("majie", "refresh");
//				commentBeans.clear();
//				skip=0;
//				queryComments(FINISH_REFRESHING);
//			}
//		}, 1, false);
//		
////		mRefreshableView.setOnLoadListener(new PullToLoadListener() {
////			
////			@Override
////			public void onLoad() {
////				queryComments(FINISH_LOADING);
////			}
////		});
//	}
//	
//	@Override
//	protected void initView() {
//
//		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
//		tvOpera=(TextView) findViewById(R.id.tv_opera);
//		inputView=(InputView) findViewById(R.id.inputview);
//		
//		lv=(ListView) findViewById(R.id.lv);
//		mRefreshableView=(RefreshableView) findViewById(R.id.refreshableview);
//		
//		mBtnTitleMiddle.setVisibility(View.VISIBLE);
//		mBtnTitleMiddle.setText("��������");
//		mBtnTitleMiddle.setTextColor(getResources().getColor(R.color.white));
//		
//		mImgLeft.setVisibility(View.VISIBLE);
//		mImgLeft.setBackgroundResource(R.drawable.bt_back_dark);
//		mImgLeft.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
//		
//		inputView.setOnSendClickListener(new OnSendClickListener() {
//			
//			@Override
//			public void onClick(String msg) {
//				writeComment(msg, operaBean.getObjectId());
//			}
//		});
//	}
//
//	@Override
//	protected void initData() {
//
//		operaBean=(OperaBean) getIntent().getSerializableExtra("operaBean");
//		String operaText=operaBean.getOperaContent();
//		tvOpera.setText(operaText);
//		myuser=BmobUser.getCurrentUser(this, MyUser.class);
//		if(myuser==null){
//			startActivity(LoginActivity.class);
//			finish();
//		}
//		
//	}
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		myuser=BmobUser.getCurrentUser(this, MyUser.class);
//	}
//	
//    private void writeComment(String comment,String operaId) {
//		
//		final CommentBean p2 = new CommentBean();
//		p2.setComment(comment);
//		p2.setOperaId(operaId);
//		
//		p2.setUsername(myuser.getUsername());
//		p2.save(this, new SaveListener() {
//
//			@Override
//			public void onSuccess() {
//				Log.d("bmob", "success  " );
//				toastMsg("����ɹ�");
////				etComment.setText("");
//				updateComment(operaBean);
//				if(commentBeans.size()==0){
//					queryComments(FINISH_REFRESHING);
//				}
////				finish();
//			}
//
//			@Override
//			public void onFailure(int code, String msg) {
//				toastMsg("��������ʧ�ܣ�" + msg);
//			}
//		});
//	} 
//    
//    private void queryComments(final int handle){
//		BmobQuery<CommentBean> bmobQuery	 = new BmobQuery<CommentBean>();
////		bmobQuery.setLimit(10);
//		bmobQuery.addWhereEqualTo("operaId", operaBean.getObjectId());
////		bmobQuery.order("-likeNum");
////		bmobQuery.setSkip(skip);
//		bmobQuery.findObjects(this, new FindListener<CommentBean>() {
//
//			@Override
//			public void onSuccess(List<CommentBean> object) {
//				Log.e("majie", "��ѯ�ɹ�����"+object.size()+"�����ݡ�");
//				skip+=object.size();
//				commentBeans.addAll(object);
//				
//				mHandler.sendEmptyMessage(handle);
//			}
//
//			@Override
//			public void onError(int code, String msg) {
//				Log.e("majie","��ѯʧ�ܣ�"+msg);
//				mHandler.sendEmptyMessage(handle);
//			}
//		});
//	}
//    
//    private Handler mHandler=new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case FINISH_REFRESHING:
//				mRefreshableView.finishRefreshing();
//				
//				break;
//
//			case FINISH_LOADING:
//				mRefreshableView.finishLoading();
//				if(skip+2<commentBeans.size()){
//					lv.setSelection(skip+2);
//				}
//				
//				break;
//			}
//			adapter.notifyDataSetChanged();
//			
//		};
//	};
//	
//	private void setAdapter(){
//		adapter=new CommentAdapter(this, commentBeans);
//		lv.setAdapter(adapter);
//	}
//	
//	/**
//	 * ���¶���
//	 */
//	private void updateComment(OperaBean bean) {
//		final OperaBean p2 = new OperaBean();
//		p2.setCommentNum(bean.getCommentNum()+1);
//		p2.update(this, bean.getObjectId(), new UpdateListener() {
//
//			@Override
//			public void onSuccess() {
//				Log.e("majie", "���³ɹ���" + p2.getUpdatedAt());
//			}
//
//			@Override
//			public void onFailure(int code, String msg) {
//				Log.e("majie", "����ʧ�ܣ�" + msg);
//			}
//		});
//
//	}
//
//}
