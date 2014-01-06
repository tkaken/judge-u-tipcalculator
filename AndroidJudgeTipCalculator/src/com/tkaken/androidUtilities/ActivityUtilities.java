package com.tkaken.androidUtilities;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class ActivityUtilities
{
	static public boolean isCurrentFocusEditText(Activity activity)
	{
		View currentFocus = activity.getCurrentFocus();		
		return (currentFocus != null && currentFocus.getClass() == EditText.class);
	}

}
