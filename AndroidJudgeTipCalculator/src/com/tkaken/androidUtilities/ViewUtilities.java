package com.tkaken.androidUtilities;

import android.widget.EditText;

public class ViewUtilities
{

	static public void clearEditTextSelection(EditText editText)
	{
		editText.setSelected(false);
		editText.setText("");
	}
}
