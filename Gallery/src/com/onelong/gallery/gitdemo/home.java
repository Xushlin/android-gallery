/**
 * 
 */
package com.onelong.gallery.gitdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * @author vaiyanzi
 *
 */
public class home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Toast.makeText(this,"back", Toast.LENGTH_LONG).show();
			return false;
		}
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

}
