package com.tkaken.androidjudgetipcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdBannerFragment extends Fragment
{
	
	//for ads
	private AdView adView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View theView = inflater.inflate(R.layout.fragment_ad_banner, container, false);
			
		
		return theView;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
	    initializeAd();


	}

	private void initializeAd()
	{
		
		// Look up the AdView as a resource and load a request.
	    adView = (AdView) getActivity().findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder()
	       .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	       .addTestDevice(getResources().getString(R.string.MY_PHONE_ID))   
	       .build();
	    adView.loadAd(adRequest);
	}

	
	@Override
	public void onPause()
	{
		if (adView != null)
		{
			adView.pause();
		}
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		if (adView != null)
		{
			adView.resume();
		}
	}

	@Override
	public void onDestroy()
	{
		if (adView != null)
		{
			adView.destroy();
		}
		super.onDestroy();
	}

	
}
