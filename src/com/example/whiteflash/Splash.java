package com.example.whiteflash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread timer = new Thread(){
		public void run(){
			try{
				sleep(1000);
				
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				Intent startmenu = new Intent("com.example.whiteflash.Menu");
				startActivity(startmenu);
			}
			
		}	
			
			
		};
		timer.start();	
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
   
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	   finish();	
	}
	
}
