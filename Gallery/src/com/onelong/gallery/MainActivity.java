package com.onelong.gallery;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * @author HL
 * homepage http://ways2y.com
 * ��ʵ�ֵĹ��� 
 * 1�����������
 * 2����ҳ���ļ������ţ���ת
 * 3���û��Զ���Ĭ�ϵ�ͼƬ�ļ���·�����ļ��������
 * 4��������ͼƬ�����浽����
 * 
 * ��ʵ�ֵĹ���
 * 1����������ϵͳ�����ͼƬ
 * 2��
 */
public class MainActivity extends Activity {
	private List<String> imageList = null;
	private ProgressDialog loadDialog = null;//����ֱ���������ʼ��
	private GalleryFlow galleryFlow = null;
	private ImageAdapter adapter = null;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			loadDialog.cancel();
		}
		
	};
	private TextView name = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.layout_gallery);
        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage(getResources().getString(R.string.loader));
        loadDialog.show();
        
        if(!SDCardService.hasSdCard())
        	alertDialog();
        if(getPath()== null){
        	String path = Environment.getExternalStorageDirectory().toString()+"\\Pictures\\Camera";
        	Log.i("path",path);
        	setPath(path);
        	imageList = SDCardService.getImageList(path);
        }else
        	imageList = SDCardService.getImageList(getPath());
        
        name = (TextView)findViewById(R.id.image_name);
        galleryFlow = (GalleryFlow) findViewById(R.id.image_show);
        
        galleryFlow.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent openImageFile = new Intent(Intent.ACTION_VIEW);
				openImageFile.setDataAndType(Uri.parse("file://"+imageList.get(position)),"image/*");
				openImageFile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					/*���Ե���ϵͳĬ�ϵ������*/
					MainActivity.this.startActivity(openImageFile);
				} catch (Exception e) {}
				return true;
			}
		});
        
        handler.post(new Runnable() {
			@Override
			public void run() {
				adapter = new ImageAdapter(MainActivity.this, imageList, name);
		        adapter.createReflectedImages();
		        galleryFlow.setAdapter(adapter);
		        handler.sendEmptyMessage(0);
			}
		});
	}

	/**
	 * û��sd������ʾ�Ի��� 
	 */
	private void alertDialog()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.nosdcard_title)
		.setMessage(R.string.nosdcard_msg)
		.setNeutralButton(R.string.nosdcard_btn, null)
		.show();
	}
	
	/**
	 * �ӹ�������л�ȡͼƬ��Ĭ���ļ���
	 * @return ͼƬ��Ĭ��·��
	 */
	private String getPath(){
		SharedPreferences spf = getSharedPreferences("imageFilePath", Context.MODE_PRIVATE);
		return spf.getString("path", null);
	}
		
	/**
	 * @param dirPath
	 * �趨ͼƬ�ļ��е�Ĭ��·��
	 */
	private void setPath(String dirPath){
		SharedPreferences spf = getSharedPreferences("imageFilePath", Context.MODE_PRIVATE);
		Editor edit = spf.edit();
		edit.putString("path", dirPath);
		edit.commit();
	}
}