package com.sebkst.salut;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

	TextView tvX,tvY,tvZ, tvTitre, tvPrecis;
	SensorManager m_sensormgr;
	List<Sensor> m_sensorlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvX = (TextView) this.findViewById(R.id.textViewX);       
        tvY = (TextView) this.findViewById(R.id.textViewY);       
        tvZ = (TextView) this.findViewById(R.id.textViewZ);
        
        tvX.setText("X_");tvY.setText("Y_");tvZ.setText("Z_");
        
        tvTitre= (TextView) this.findViewById(R.id.textView1);
        tvTitre.setText("Accelerometer");
        tvPrecis= (TextView) this.findViewById(R.id.textViewAccuracy);
        
        m_sensormgr  = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        m_sensorlist =  m_sensormgr.getSensorList(Sensor.TYPE_ALL);
        
        connectSensors();        
    }

    SensorEventListener senseventListener = new SensorEventListener(){
    
		@Override
		public void onSensorChanged(SensorEvent event) {
			String accuracy;
			
			switch(event.accuracy){
			  case SensorManager.SENSOR_STATUS_ACCURACY_HIGH: accuracy="acc_HIGH";break;
			  case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM: accuracy="acc_MEDIUM";break;
			  case SensorManager.SENSOR_STATUS_ACCURACY_LOW: accuracy="acc_LOW";break;
			  case SensorManager.SENSOR_STATUS_UNRELIABLE: accuracy="acc_UNRELIABLE";break;
			  default: accuracy="acc_UNKNOWN";
			}
		
 		   tvPrecis.setText(accuracy);
			if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
				   tvX.setText(String.format("X_%.2f",event.values[0]));
				   tvY.setText(String.format("Y_%.2f",event.values[1]));
				   tvZ.setText(String.format("Z_%.2f",event.values[2]));
				}
			
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			String saccuracy;
			switch(accuracy){
			  case SensorManager.SENSOR_STATUS_ACCURACY_HIGH: saccuracy="acc_HIGH";break;
			  case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM: saccuracy="acc_MEDIUM";break;
			  case SensorManager.SENSOR_STATUS_ACCURACY_LOW: saccuracy="acc_LOW";break;
			  case SensorManager.SENSOR_STATUS_UNRELIABLE: saccuracy="acc_UNRELIABLE";break;
			  default: saccuracy="acc_UNKNOWN";
			}
		   tvPrecis.setText(saccuracy);
		}	
    };

    
	protected void connectSensors(){
		m_sensormgr.unregisterListener(senseventListener);
		if(!m_sensorlist.isEmpty()){
        	Sensor snsr;
        	for(int i=0;i<m_sensorlist.size();i++){
        		snsr=m_sensorlist.get(i);        		
        		if(snsr.getType()==Sensor.TYPE_ACCELEROMETER){ // && mTabHost.getCurrentTab()== 1){
        			m_sensormgr.registerListener(senseventListener, snsr, SensorManager.SENSOR_DELAY_NORMAL);
        		}
        	}
        }
	}

    
    
    
    
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
    
}
