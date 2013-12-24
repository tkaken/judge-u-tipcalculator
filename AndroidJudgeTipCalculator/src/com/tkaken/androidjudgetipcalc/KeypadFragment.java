package com.tkaken.androidjudgetipcalc;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class KeypadFragment extends Fragment
{
	
    private static final int KEY_CODE_DO_NOTHING = 5000;
	private KeyboardView keyboardView;
	
	//for ads
	private static final String GALAXY_S4_TEST_PHONE_ID = "1318D8C8CCFD241741913D62A280F81E";
	private AdView adView;
	
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
	    initializeAd();


	}

	private void initializeAd()
	{
		// Look up the AdView as a resource and load a request.
	    adView = (AdView) getActivity().findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	       .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	       .addTestDevice(GALAXY_S4_TEST_PHONE_ID)   
	       .build();
	    adView.loadAd(adRequest);
	}

	
	@Override
	public void onPause()
	{
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		adView.resume();
	}
	
	@Override
	public void onDestroy()
	{
		adView.destroy();
		super.onDestroy();
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
			if (!isCurrentFocusEditText()) return;
			
			if (primaryCode == KEY_CODE_DO_NOTHING) return;
			
			EditText editText = (EditText) getCurrentFocus();
			
			if (editText.hasSelection())
			{
				clearEditTextSelection(editText);
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
	
	//TODO utility class function?
	private boolean isCurrentFocusEditText()
	{
		View currentFocus = getCurrentFocus();		
		return (currentFocus != null && currentFocus.getClass() == EditText.class);
	}

	//TODO utility class function?
	private View getCurrentFocus()
	{
		return getActivity().getCurrentFocus();
	}

	//TODO utility class function?
	private void clearEditTextSelection(EditText editText)
	{
		editText.setSelected(false);
		editText.setText("");
	}

}
