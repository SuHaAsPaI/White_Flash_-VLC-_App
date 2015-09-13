package com.example.whiteflash;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {
	String menues[]={"flash text","text Receiver","Diagnostics","credits"};
	 //String classes[]={"Transmit_text","Reciever","transmitter","text_reciever","credits"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(Menu.this,android.R.layout.simple_list_item_1,menues));
	
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		String menu_clicked = null;
		
		switch(position)
		{
		case 0: menu_clicked="Transmit_text";
			break;
		case 1:menu_clicked="text_reciever";
		break;
		case 2 :menu_clicked="diag_menu";
		break;
		case 3 :menu_clicked="credits";
	
		}
		try{	
		Class ourclass = Class.forName("com.example.whiteflash." + menu_clicked);
		Intent ourintent = new Intent (Menu.this, ourclass);
		startActivity(ourintent);
		}catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		
		
	}

	
	
	
	
	
	
	
	
}
