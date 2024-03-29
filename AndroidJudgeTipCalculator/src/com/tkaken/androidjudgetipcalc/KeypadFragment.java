package com.tkaken.androidjudgetipcalc;

import com.tkaken.androidUtilities.ActivityUtilities;
import com.tkaken.androidUtilities.ViewUtilities;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class KeypadFragment extends Fragment
{
	
    private static final int KEY_CODE_DO_NOTHING = 5000;
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
		
		keyboardView = (KeyboardView) mainActivity.findViewById(R.id.keyboardview);

		attachKeyboard(mainActivity, keyboardView);
		
		keyboardView.setPreviewEnabled(false);
		
		keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);
	}

	private void attachKeyboard(Activity mainActivity, KeyboardView keyboardView)
	{
		Keyboard mKeyboard = new Keyboard(mainActivity, R.xml.fun_decimal_number_keypad);		
		keyboardView.setKeyboard( mKeyboard );
	}
	

	private OnKeyboardActionListener onKeyboardActionListener = new OnKeyboardActionListener()	
	{

		@Override
		public void onKey(int primaryCode, int[] keyCodes)
		{
			if (!ActivityUtilities.isCurrentFocusEditText(getActivity())) return;
			
			if (primaryCode == KEY_CODE_DO_NOTHING) return;
			
			EditText editText = (EditText) getActivity().getCurrentFocus();
			
			if (editText.hasSelection())
			{
				ViewUtilities.clearEditTextSelection(editText);
			}
			
			
			Editable textToEdit = editText.getText();
			int positionOfCursorInText = editText.getSelectionStart();	
			
			if( primaryCode==Keyboard.KEYCODE_DELETE ) 
			{
		        processDeleteKey(textToEdit, positionOfCursorInText);
			}
			else
			{
		        textToEdit.insert(positionOfCursorInText, getStringFromPrimaryCode(primaryCode));
			}
		}
		
		private String getStringFromPrimaryCode(int primaryCode)
		{
			return Character.toString((char) primaryCode);
		}
		
		private void processDeleteKey(Editable editable, int start)
		{
			if( editable!=null && start>0 ) editable.delete(start - 1, start);
		}

		@Override
		public void onPress(int primaryCode)
		{
		}

		@Override
		public void onRelease(int primaryCode)
		{

			
		}

		@Override
		public void onText(CharSequence text)
		{

			
		}

		@Override
		public void swipeDown()
		{

			
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
