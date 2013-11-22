package com.tkaken.androidjudgetipcalc;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class UserSettingsActivity extends PreferenceActivity
{
	//Deprecated function to support earlier Android versions 
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.preferences);
    }
}
