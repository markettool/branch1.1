package org.market.tool.ui;

import java.io.File;

import org.market.tool.R;
import org.market.tool.bean.MyBmobFile;
import org.market.tool.bean.TaskBean;
import org.market.tool.bean.User;
import org.market.tool.util.BitmapUtil;
import org.market.tool.util.FileUtils;
import org.market.tool.util.ProgressUtil;
import org.market.tool.view.AlbumView;
import org.market.tool.view.AlbumView.onHandleListener;
import org.market.tool.view.HeaderLayout.onRightImageButtonClickListener;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PublishTaskActivity extends BaseActivity {
	int PICK_REQUEST_CODE = 0;
	
	private EditText etTask;
	private AlbumView albumView;
	private String dir;
	private User myUser;
	private MyBmobFile bmobFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publishtask);
		initView();
		setListeners();
		initData();
	}

	protected void initView() {
		etTask=(EditText) findViewById(R.id.et_opera);
		albumView=(AlbumView) findViewById(R.id.albumview);
		albumView.setLimit(1);
		
		initTopBarForBoth("发起任务", R.drawable.btn_chat_send_selector, new onRightImageButtonClickListener() {
			
			@Override
			public void onClick() {
				publish();
			}
		});
	}
	
	private void publish(){
		if(!TextUtils.isEmpty(etTask.getText().toString())){
			ProgressUtil.showProgress(PublishTaskActivity.this, "");
			if(bmobFile!=null){
				uploadFile();
			}else{
				publishTask(null);
			}
			
		}else{
			ShowToast("输入为空");
		}
	}
	
	private void setListeners(){
		
        albumView.setOnHandleListener(new onHandleListener() {
			
			@Override
			public void onClick(int index) {
				getFileFromSD();
			}
		});
	}

	protected void initData() {

		myUser=BmobUser.getCurrentUser(this, User.class);
		if(myUser==null){
			startAnimActivity(LoginActivity.class);
			finish();
		}
		
		dir = FileUtils.PHOTO_PATH;
		FileUtils.mkdirs(dir);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		myUser=BmobUser.getCurrentUser(this, User.class);
	}
	
	/**
	 * 插入对象
	 */
	private void publishTask(BmobFile file) {
		final TaskBean p = new TaskBean();
		if(myUser.getBmobFiles()!=null&&myUser.getBmobFiles().size()!=0){
			p.setOwnerPic(myUser.getBmobFiles().get(0));
		}
		p.setOwnerName(myUser.getUsername());
		p.setTaskContent(etTask.getText().toString());
		if(file!=null){
			p.setTaskPic(file);
		}
//		p.setTaskUser(myUser);
		p.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
//				Log.d("bmob", "success  " );
				ShowToast("发表成功");
				finish();
				ProgressUtil.closeProgress();
			}

			@Override
			public void onFailure(int code, String msg) {
				ShowToast("失败：" + msg);
				ProgressUtil.closeProgress();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		setResult(0x01);
	}
	
	private void getFileFromSD() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, PICK_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			try {
				String[] pojo = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				if (cursor != null) {
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);

//					Log.e("majie", "path  " + path);
					if (path != null) {
						saveThubPhoto(path);
					}
				}

			} catch (Exception e) {
			}

		}
	}
	
	private void uploadFile() {
		
		bmobFile.uploadblock(this, new UploadFileListener() {

			@Override
			public void onSuccess() {
				Log.e("majie", "success");
				publishTask(bmobFile);
			}

			@Override
			public void onProgress(Integer arg0) {
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("majie", "fail:" + arg0+",msg = "+arg1);
			}

		});

	}
	
	private void saveThubPhoto(final String path){
		new Thread(){
			public void run() {
				super.run();
				Bitmap bitmap=BitmapUtil.getThumbilBitmap(path, 200);
				String thubPath=dir+myUser.getUsername()+"_opera_tmp"+".png";
				BitmapUtil.saveBitmapToSdcard(bitmap, thubPath);
				Message message=new Message();
				message.obj=thubPath;
//				message.arg1=position;
				handler.sendMessage(message);
			};
		}.start();
		
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			bmobFile=new MyBmobFile(new File((String) msg.obj));
			bmobFile.setLocalFilePath((String) msg.obj);
			albumView.addData(bmobFile);
		};
	};

}
