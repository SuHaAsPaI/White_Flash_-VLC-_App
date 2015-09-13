package com.example.whiteflash;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Reciever extends Activity implements SensorEventListener{
     
	TextView rec,brec,brecstat;
	SensorManager sm ;
	Sensor prox;
	boolean take_reading=true;

	double arr[]=new double[10],aveg=0,max=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reciever);
		rec= (TextView) findViewById(R.id.textView1);		
		brec= (TextView) findViewById(R.id.binarytext);
		brecstat=(TextView) findViewById(R.id.recstatus);
		
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		prox= sm.getDefaultSensor(Sensor.TYPE_LIGHT);
		
		
		sm.registerListener(this,prox,SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		
		rec.setText("light intensity = "+event.values[0]+" lumens");
		//calculate max	value of counter	      
		
		if((max<event.values[0])&&(take_reading))
		{   
			max=event.values[0];
			brecstat.setText("max light intensity chosen= "+event.values[0]+" lumens");
			take_reading=false;
		}
	
		
		//mean
		
		
		
		
	}
	
	

}
