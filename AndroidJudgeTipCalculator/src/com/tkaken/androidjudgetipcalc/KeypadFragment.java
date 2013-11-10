package com.tkaken.androidjudgetipcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class KeypadFragment extends Fragment
{
	
	//Buttons
	private Button one_btn;
	private Button two_btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View theView = inflater.inflate(R.layout.fragment_number_keypad, container, false);
		
		
		one_btn = (Button) theView.findViewById(R.id.button1);
		two_btn = (Button) theView.findViewById(R.id.button2);
		//TODO setup keypad button listeners
	
		
		return theView;
		
	}
	

}
