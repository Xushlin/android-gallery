package com.onelong.gallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class SDCardService {

	/**
	 * ����Ƿ���sdcard
	 * @return Boolean
	 */
	public static Boolean hasSdCard(){
		final String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED) || 
				state.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
			return true;
		return false;
	}
	
	/**
	 * ��ȡ·��������ͼƬ�ļ�
	 * @param path
	 * 	�ļ��е�·��
	 * @return List<String>
	 */
	public static List<String> getImageList(String path)
    {
		if(path == null)
			throw new NullPointerException();
		List<String> imageList=new ArrayList<String>();                 
		File f=new File(path);  
		File[] files=f.listFiles();
		if(files != null){
			for(int i=0;i<files.length;i++)
			{
				if(isImageFile(files[i].getPath()))
					imageList.add(files[i].getPath());
			}
		}
		return imageList;
    }

	/**
	 * �ж��ļ��Ƿ�ΪͼƬ��ʽ
	 * @param fileName
	 * 		�ļ���
	 * @return boolean
	 */
	private static boolean isImageFile(String fileName)
    {
		if(fileName == null)
			throw new NullPointerException();
		boolean isImg;
		String end = fileName.substring(fileName.lastIndexOf(".")+1,
    		  fileName.length()).toLowerCase(); 
		if(end.equals("jpg")||end.equals("gif")||
    		  end.equals("png")||end.equals("jpeg")||
    		  end.equals("bmp"))
		{
    	  isImg=true;
		}else{
    	  isImg=false;
		}
		return isImg; 
    }
	
}
