package com.example.whiteflash;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class diag_menu extends ListActivity {
	String menues[]={"Check receiver","Check transmitter"};
	//String classes[]={"Transmit_text","Reciever","transmitter","text_reciever","credits"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(diag_menu.this,android.R.layout.simple_list_item_1,menues));
	
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String menu_clicked = null;
		
		switch(position)
		{
		case 0: menu_clicked="Reciever";
			break;
		case 1:menu_clicked="transmitter";
		
		
		}
		
		try{	
		Class ourclass = Class.forName("com.example.whiteflash." + menu_clicked);
		Intent ourintent = new Intent (diag_menu.this, ourclass);
		startActivity(ourintent);
		}catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		
		
	}

	
	
	
	
	
	
	
	
}
