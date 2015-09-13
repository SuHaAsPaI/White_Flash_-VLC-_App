package com.example.whiteflash;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


class newthread implements Runnable{
	Camera camera= Camera.open();
	final Parameters p = camera.getParameters();
	Thread t;
	int delay=0;
	boolean status=true;
	
	public void run()
	{
		try{
		while(status)
		{
				
			//turn on code
			 p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			 camera.setParameters(p);
             //myseekbar.getProgress();

			 //delay
			
				Thread.sleep(delay+300);
			
			
		//turn off code	
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
		
		//sleep
			
				Thread.sleep(delay+300);
			 
			
			//blink code
			
			
			
				
		}
		}catch(InterruptedException e){
			e.printStackTrace();	
			p.setFlashMode(Parameters.FLASH_MODE_OFF); // if interupted in sleep turn                             
			camera.setParameters(p);//off flash and exit thread
			System.out.println("sleep interrupt trigerred when on");
			return;
		}
		camera.stopPreview();
		camera.release();
	}
	void setdelay(int a)
	{
		delay=a;
	}
	void startrunning()
	{
	    //status=true;
	    t=new Thread(this,"flasher");
		t.start();

		
		
	}
	void stoprunning()
	{
		//status=false;
		try{
		
		t.interrupt();
		}catch(NullPointerException e){}
		//turn off code
	}
}

public class transmitter extends Activity implements OnSeekBarChangeListener{
    boolean once=true;
	SeekBar myseekbar;
	TextView tv;
	newthread mythread=new newthread();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tx);
		
		tv = (TextView) findViewById(R.id.tadjustflikrate);
		myseekbar = (SeekBar) findViewById(R.id.sbchoice);
		Button fon = (Button) findViewById(R.id.bflashon);
		Button foff = (Button) findViewById(R.id.bflashoff);
	
		myseekbar.setOnSeekBarChangeListener(this);
		
		
		PackageManager pm = this.getPackageManager();
		
		if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			
			Toast.makeText(getApplicationContext(),
			"Your device doesn't have camera!",Toast.LENGTH_SHORT).show();
			//Return from the method, do nothing after this code block
			return;
			}
		
		fon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(once){
				/**/
			  mythread.startrunning();//starts off the new thread from the class
              once=false;
              Toast toasta = Toast.makeText(transmitter.this,"flash turned ON ",Toast.LENGTH_SHORT);
				toasta.show();
				}
			}
		});
		
		foff.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			/*	
			*/
				mythread.stoprunning();
				once=true; //re-enable flash
				Toast toast = Toast.makeText(transmitter.this,"flash turned OFF ",Toast.LENGTH_SHORT);
				toast.show();
			}
		});
		
	
	}//on create ends here

	
	

	
	





	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		mythread.stoprunning();
		
		//finish();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mythread.camera.release();
		 //finish();
	}



	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		tv.setText("Fliker rate = "+progress);
		mythread.setdelay(progress);
		
	}





	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
