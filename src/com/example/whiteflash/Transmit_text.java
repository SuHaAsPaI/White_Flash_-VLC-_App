package com.example.whiteflash;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Transmit_text extends Activity implements View.OnClickListener{
	boolean closure=false;
	int mili=300;
	int nano=0;
	Thread toneplayer;
	Handler handler;
	ProgressBar prog,progbit;
	TextView status;
	EditText dataip;
	Button gotext;
	TextView display;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.text_tran);
		initialize();	
		gotext.setOnClickListener(this);	
		handler=new Handler();
	}//on create end here



	private void initialize() {
		// TODO Auto-generated method stub
		prog = (ProgressBar) findViewById(R.id.progressBar1);
		status =(TextView) findViewById(R.id.tv_status_of_text_tx);
		dataip =(EditText) findViewById(R.id.et_tx_data_input);
		gotext =(Button) findViewById(R.id.b_transmit_text_now);
		display=(TextView)findViewById(R.id.tv_breakuptext); 
		progbit=(ProgressBar) findViewById(R.id.progressBarFORBITS);
	}//initialize ends here

@Override
public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId())
		{
		
		case R.id.b_transmit_text_now:		
			toneplayer=new Thread(new mythread());// creates the tone thread		
			toneplayer.setPriority(10);
			toneplayer.start();			
			break;	
		}//switch ends here		
}//on click listener ends here

class mythread implements Runnable{
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// this is for the message stuff ..
			char b;
			String data;
			int len,i,l,arr[]=new int[8];	
			
			data = dataip.getText().toString();		
			len=data.length();

			Camera camera= Camera.open();
			final Parameters p = camera.getParameters();	
			
			for(i=0;i<len;i++)
			{   // start bit...
				
//start bits
//light high				
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			 camera.setParameters(p);
//wait	300ms		 
				try {
					Thread.sleep(mili,nano);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//light low				
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(p);
//wait 300ms
				try {
					Thread.sleep(mili,nano);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//release camera			
				
				
				// start bit ends here
				final int step = i+1;// because run needs a final variable**************
				prog.setMax(len);//set the size of the char progress bar dinamicaly depending on the char size
				handler.post(new Runnable(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					prog.setProgress(step);
				}
				}
				);//handler ends here*************************************************
				b=data.charAt(i);
				l=b;//conver char vale into its ascii int form
				
				for(int j=0;j<=7;j++)
				{	
					arr[j]=l%2;
					l=l/2;		
				}
		/*runOnUiThread(new Runnable(){
			  public void run()
			  {
						display.setText(" last char = "+temp_char 
						+" acii val ="+temp_ascii
						+ " binary equivalent :"+arr[7]+arr[6]+arr[5]+arr[4]+arr[3]+arr[2]+arr[1]+arr[0]
						+" i = "+ i);
			  }
			  }); */
				
				//audio tones depending on binary data
				for(int j=0;j<=7;j++)
				{		final int stepjj = j+1;// because run needs a final variable**************
						progbit.setMax(8);//set zize of the bit progress bar
						handler.post(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							progbit.setProgress(stepjj);
						}
						}
						);//handler ends here*************************************************
					
					if(arr[j]==1)
					{   //logic one
						
						
						p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					 camera.setParameters(p);
						
						//pause for 300ms
					   try {
							Thread.sleep(mili,nano);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//end pause 
					   
					  	
				         
					}
					else if(arr[j]==0)
					{  
						 
					
						
						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						camera.setParameters(p);
						//pause for 5 seconds
					try {
							 Thread.sleep(mili,nano);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}//end pause  
						     
					}
					//break with back button
					if(closure)
					{
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					camera.stopPreview();
					camera.release();
					return;
					
					}
					
					
				}
//stop bit 
				
//stop bits
//light high				
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			 camera.setParameters(p);
//wait	300ms		 
			/*	try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
//light low				
/*				p.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(p);*/	
				
				
				
			}
			try {
				Thread.sleep(mili,nano);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//put flash off and tx
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			camera.stopPreview();
			camera.release();
		}
		
		
}// my thread ends here

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	closure=true;
}


}
