package com.tkaken.androidjudgetipcalc;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KeypadFragment extends Fragment
{
	
    private KeyboardView keyboardView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View theView = inflater.inflate(R.layout.fragment_number_keypad, container, false);
			
		
		return theView;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		initializeKeyboard();		

	}

	private void initializeKeyboard()
	{
		Activity mainActivity = getActivity();
		
		Keyboard mKeyboard= new Keyboard(mainActivity, R.xml.fun_number_keypad);
		
		keyboardView = (KeyboardView) mainActivity.findViewById(R.id.keyboardview);

	    // Attach the keyboard to the view
		keyboardView.setKeyboard( mKeyboard );
		
		keyboardView.setPreviewEnabled(false);
		
		keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);
	}
	
	private OnKeyboardActionListener onKeyboardActionListener = new OnKeyboardActionListener()	
	{

		@Override
		public void onKey(int primaryCode, int[] keyCodes)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPress(int primaryCode)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRelease(int primaryCode)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onText(CharSequence text)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeDown()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeLeft()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeRight()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void swipeUp()
		{
			// TODO Auto-generated method stub
			
		}
		
	};

}
