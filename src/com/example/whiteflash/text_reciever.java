package com.example.whiteflash;

import android.app.Activity;
import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class text_reciever extends Activity implements SensorEventListener, Runnable{
	int temp=0,baud=300;
	//double senred=0;
	int data[];
	Thread m ;
	double thresh=500;
	boolean carry_on=true,line_high=false,startbit=false,stopbit=false;
	boolean once=true;
	public static TextView tv;
	Button start_rec,stop_rec;	
	double sen_val=0,prev_sen_val=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recieve_text_layout);
	
		SensorManager sm1 ;
		Sensor proxy;
		sm1 = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		proxy= sm1.getDefaultSensor(Sensor.TYPE_LIGHT);
		sm1.registerListener(this,proxy,SensorManager.SENSOR_DELAY_FASTEST);
		
		start_rec=(Button)findViewById(R.id.bstart_rec);
		stop_rec=(Button)findViewById(R.id.bstop_rec);
		
		 tv=(TextView)findViewById(R.id.tvnotify);
		ScrollView scrol = (ScrollView)findViewById(R.id.scrollView1);
		
		/*for(int i=0;i<=100;i++){
		tv.append(i+ System.getProperty ("line.separator"));
		}*/
		
		//update_tv(true);
		//update_tv(false);
		start_rec.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(once){	
				temp=0;
				startbit=false;
				stopbit=false;
				data=new int [8];
				m=new Thread(text_reciever.this,"reciever thread");
				m.start();
				//tv.setText("waiting...."+ System.getProperty ("line.separator"));
				carry_on=true;
				once=false;
				Toast toasta = Toast.makeText( text_reciever.this,"receiver started ",Toast.LENGTH_SHORT);
				toasta.show();
			}
				
			}
		});
		stop_rec.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			try{
				m.interrupt();//stops sleeping thread
				carry_on=false;
				once=true; //re-enable flash
			}catch(NullPointerException e){}
				Toast toast = Toast.makeText(text_reciever.this,"Receiver turned OFF ",Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	
	}//on create ends here
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(carry_on)
			{    
				// this finds the start bit 
				/*if(sen_val>=thresh)
				{
					line_high=true;
					runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("sen "+sen_val+ System.getProperty ("line.separator"));
					    }
					});
				}*/
				//Thread.sleep(150);//100 ms 
				if(prev_sen_val>=thresh&&sen_val<thresh)
				{
					//start bit detected
					/*runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("start bit detected"+ System.getProperty ("line.separator"));
					    }
					});*/
					//line_high=false;//reset line high
					//startbit=true;
				}
				else
				{
					continue;//wait for start bit
				}
				
				
				//once start bit found start recieving 8 bits every 200 ms
				
				Thread.sleep(baud/2);
				if(sen_val<thresh)
				{
					//start bit detected
					/*runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("start bit detected"+ System.getProperty ("line.separator"));
					    }
					});*/
					//line_high=false;//reset line high
					startbit=true;
				}
				else
				{
					continue;//wait for start bit
				}
				Thread.sleep(baud);
				/*runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 0  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});*/
				for(int k=0;k<8;k++)
				{    
					if(sen_val>=thresh)
					{data[k]=1;
					/*runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("bit = "+sen_val+ System.getProperty ("line.separator"));
					    }
					});*/
					}
					else
					{
					 data[k]=0;
					/* runOnUiThread(new Runnable() {
						    public void run() {
						    	tv.append("bit = "+sen_val+ System.getProperty ("line.separator"));
						    }
						});*/
					}
					Thread.sleep(baud);
				}
				
				
		/**		
				
				if(sen_val>=thresh)
				{data[0]=1;
				}
				else
				{
				 data[0]=0;
				}
				
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 1  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[1]=1;
				}
				else
				{
				 data[1]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 2  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[2]=1;
				}
				else
				{
				 data[2]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 3  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[3]=1;
				}
				else
				{
				 data[3]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 4  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[4]=1;
				}
				else
				{
				 data[4]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 5  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[5]=1;
				}
				else
				{
				 data[5]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 6  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[6]=1;
				}
				else
				{
				 data[6]=0;
				}
				Thread.sleep(300);
				runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("bit - 7  "+sen_val+ System.getProperty ("line.separator"));
				    }
				});
				if(sen_val>=thresh)
				{data[7]=1;
				}
				else
				{
				 data[7]=0;
				}
			*/	
				//now look for a stop bit
				
				//Thread.sleep(300);
				
				if(sen_val>=thresh){
				/*runOnUiThread(new Runnable() {
				    public void run() {
				    	tv.append("stop bit detected "+ System.getProperty ("line.separator"));
				    }
				});*/
				 stopbit=true;
				 temp=0;
				}
				
				/*
				System.out.println("thread in progress");
			
				if((prev_sen_val>thresh)&&(sen_val<thresh))
				{
					runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("start bit detected"+ System.getProperty ("line.separator"));
					    }
					});
				}
				else
				{
					runOnUiThread(new Runnable() {
					    public void run() {
					    	tv.append("p "+prev_sen_val+"c "+sen_val+ System.getProperty ("line.separator"));
					    }
					});
				}
				*/
				
	//reconstruct char
				if((startbit)&&(stopbit))
				{ //gets back decimal number
					
					for(int i=0;i<8;i++)
					{
						temp=temp+(data[i])*(int) (Math.pow(2,i));
					}
					runOnUiThread(new Runnable() {
					    public void run() {
					    	//tv.append("int recovered "+temp+ System.getProperty ("line.separator"));
					    	tv.append(" "+(char)temp+ System.getProperty ("line.separator"));
					    }
					});
					
					startbit=false;
					stopbit=false;
					
				}
				
				
				
			}
		}catch(InterruptedException e){  
			e.printStackTrace();
			
		}
	
	}//run ends here
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		carry_on=false;
		try{
		m.interrupt();
		}catch(NullPointerException e){}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub	
		prev_sen_val=sen_val;
		sen_val=arg0.values[0];
		//tv.append("sen val"+sen_val+ System.getProperty ("line.separator"));
		
	
	
	
	}


}
