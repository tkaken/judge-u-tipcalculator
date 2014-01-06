package com.tkaken.androidjudgetipcalc;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tkaken.androidUtilities.ActivityUtilities;
import com.tkaken.tipCalc.TipCalcState;
import com.tkaken.tipRules.Judgement;
import com.tkaken.tipRules.JudgementValues;
import com.tkaken.tipRules.TipJudgementRules;
import com.tkaken.tipRules.TipJudgementRulesEngineFactory;




/**
 * @author Ken
 *
 */
public class JudgeTipCalcMainActivity extends FragmentActivity
{
	private static final int RESULT_SETTINGS = 1001;
	private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1002;

	private boolean userRequestedDataUpdate;
	private boolean isDestroyed = false; 


	// look up keys for activity's persistent data
	private static String BILL_AMOUNT_KEY = "BILL_AMOUNT";
	private static String TIP_PERCENT_KEY = "TIP_PERCENT";
	private static String TIP_AMOUNT_KEY = "TIP_AMOUNT";
	private static String TOTAL_AMOUNT_KEY = "TOTAL_AMOUNT";
	private static String NUM_OF_GROUPS_KEY = "NUMBER_OF_GROUPS";
	private static String GROUP_PAYS_KEY = "GROUP_PAYS";
	//private static String JUDGEMENT_MSG_KEY = "JUDGEMENT_MSG";


	// hold text data for data entry fields
	private EditText billAmount_ET;
	private EditText tipPercent_ET;
	private EditText tipAmount_ET;
	private EditText totalAmount_ET;
	private EditText numberOfGroups_ET;
	private EditText groupPaysAmount_ET;
	private TextView judgementMessage_TV;
	
	
	
	//Buttons
	private Button thumbsUp_btn;
	private Button thumbsDown_btn;

	private TipCalcState tipCalcState;
	private TipJudgementRules tipJudger;


	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		if (checkPlayServices())
		{
			Log.d(this.getClass().getSimpleName(), "checkPlayServices returned true.");
		}
		else
		{
			Log.d(this.getClass().getSimpleName(), "checkPlayServices returned false.");
		}
		
		if (!ActivityUtilities.isCurrentFocusEditText(this))
		{
		   setFirstFocusOnFirstField();
		}
	}

	
	private boolean checkPlayServices()
	{
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS)
		{
			if (GooglePlayServicesUtil.isUserRecoverableError(status))
			{
				showErrorDialog(status);
				return false;
			} 
			else
			{
				Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		
		return true;
	}

	private void showErrorDialog(int code)
	{
		GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putDouble(BILL_AMOUNT_KEY, this.tipCalcState.getBillAmount() );
		outState.putDouble(TIP_PERCENT_KEY, this.tipCalcState.getTipPercent() );
		outState.putDouble(TIP_AMOUNT_KEY, this.tipCalcState.getTipAmount() );
		outState.putDouble(TOTAL_AMOUNT_KEY, this.tipCalcState.getTotalAmount() );
		outState.putDouble(NUM_OF_GROUPS_KEY, this.tipCalcState.getNumOfGroups() );
		outState.putDouble(GROUP_PAYS_KEY, this.tipCalcState.getGroupPaysAmount() );

	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_judge_tip_calc_main);

		this.tipJudger = getTipJudgementRules();
		
		this.tipCalcState = new TipCalcState();
		setCalcToDefaultTip();
		
	    initScreenFields();
		
		initScreenButtons();

		initializeScreenTextData();
		
		initializeTextListeners();
		
		initializeButtonListeners();
		
		if (null == savedInstanceState)
		{
			//TODO is this needed?
			//initFieldsHoldingScreenState();
		} 
		else
		{
			restoreFieldsHoldingScreenState(savedInstanceState);
		}	
		
		setUserRequestedDataUpdate(true);
		
		createFragmentIfDoesNotExist(KeypadFragment.class.getName());	
		createFragmentIfDoesNotExist(AdBannerFragment.class.getName());
		
	}


	//TODO consider doing an extract class for a fragment utility
	private void createFragmentIfDoesNotExist(String fragmentClassName)
	{
		FragmentManager fragManager = getSupportFragmentManager();
		
		Fragment theFragment = fragManager.findFragmentByTag(fragmentClassName);
		
		if (null == theFragment)
		{
						
			theFragment = createFragment(fragmentClassName);
			
			fragManager.beginTransaction()
				.add(R.id.layoutContainer, theFragment, fragmentClassName)
				.commit();
			
		}
	}


	private Fragment createFragment(String fragmentClassName)
	{
		Fragment theFragment = null;
		
		try
		{
			theFragment = (Fragment) Class.forName(fragmentClassName).newInstance();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return theFragment;
	}


	private void initScreenButtons()
	{
		thumbsUp_btn = (Button) findViewById(R.id.thumbs_up_btn);
		thumbsDown_btn = (Button) findViewById(R.id.thumbs_down_btn);
	}


	private void initScreenFields()
	{

		billAmount_ET = createEditTextNoKeypad(R.id.billAmountEditText);
		tipPercent_ET = createEditTextNoKeypad(R.id.tipPercenttEditText);		
		tipAmount_ET = createEditTextNoKeypad(R.id.tipAmountEditText);
		totalAmount_ET = createEditTextNoKeypad(R.id.totalAmountEditText);
		numberOfGroups_ET = createEditTextNoKeypad(R.id.numPeopleEditText);	
		groupPaysAmount_ET = createEditTextNoKeypad(R.id.perPersonAmountEditText);
		
		judgementMessage_TV = (TextView) findViewById(R.id.judgementTextView);
	}
	
	private OnTouchListener otl = new OnTouchListener()
	{
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			EditText editText = (EditText) v;
	
			disableDefaultKeyboard(editText);
	
			View focusCurrent = getWindow().getCurrentFocus();
	
			if (editText.equals(focusCurrent))
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
										
					setInsertionPoint(event, editText);
					break;
					
				}
				
			} 
			
			else
			{
				editText.requestFocus();
			}
	
			return true; // consumes the onTouch event
		}
	
		private void setInsertionPoint(MotionEvent touchPoint, EditText editText)
		{
			Layout layout = editText.getLayout();
			float x = touchPoint.getX() + editText.getScrollX();
			int offset = layout.getOffsetForHorizontal(0, x);
			if (offset > 0)
				if (x > layout.getLineMax(0))
					editText.setSelection(offset); // touch was at end
													// of
													// text
				else
					editText.setSelection(offset - 1);
		}
	};

	private EditText createEditTextNoKeypad(int viewId)
	{
		EditText editText;
		
		editText = (EditText) findViewById(viewId);
		editText.setOnTouchListener(otl);
		
		return editText;
	}

	private void attachNumberKeyboard()
	{
		KeyboardView keyboardView = (KeyboardView) findViewById(R.id.keyboardview);
		Keyboard mKeyboard = new Keyboard(this, R.xml.fun_number_keypad);		
		keyboardView.setKeyboard( mKeyboard );
	}


	private void attachKeyboard(EditText editText)
	{
		
	   if (isInputTypeDecimalNumber(editText))
	   {
		   attachDecimalNumberKeyboard();
	   }
	   else if (isInputTypeNumber(editText))
	   {
		   attachNumberKeyboard();
	   }
	}


	private boolean isInputTypeNumber(EditText editText)
	{
		return editText.getInputType() == InputType.TYPE_CLASS_NUMBER;
	}


	private boolean isInputTypeDecimalNumber(EditText editText)
	{
		return editText.getInputType() == (InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}


	private void attachDecimalNumberKeyboard()
	{
		KeyboardView keyboardView = (KeyboardView) findViewById(R.id.keyboardview);
		Keyboard mKeyboard = new Keyboard(this, R.xml.fun_decimal_number_keypad);		
		keyboardView.setKeyboard( mKeyboard );
	}


	/**
	 * The first EditText field that gets focus needs to has its onFocus function call this.
	 * Necessary since requestFocus does not use select on focus at startup.
	 */
	private void setUpFirstFieldsFocusState(EditText firstFocusField)
	{
		firstFocusField.selectAll();
	}

	private TipJudgementRules getTipJudgementRules()
	{
		Resources res = getResources();

		List<String> cheapJudgementList = Arrays.asList(res.getStringArray(R.array.cheap_judgements));		
		JudgementValues cheapValues = new JudgementValues(res.getColor(R.color.android_dk_red), res.getColor(R.color.white), cheapJudgementList);

		List<String> normalJudgementList = Arrays.asList(res.getStringArray(R.array.normal_judgements));		
		JudgementValues normalValues = new JudgementValues(res.getColor(R.color.android_lt_blue), res.getColor(R.color.black), normalJudgementList);

		List<String> saintJudgementList = Arrays.asList(res.getStringArray(R.array.saint_judgements));
		JudgementValues saintValues = new JudgementValues(res.getColor(R.color.india_green), res.getColor(R.color.white), saintJudgementList);
		
		TipJudgementRulesEngineFactory tipRulesEngineFactory = new TipJudgementRulesEngineFactory();
		
		return tipRulesEngineFactory.getRulesEngine(cheapValues, normalValues, saintValues);
	}


	private void initializeButtonListeners()
	{
		thumbsUp_btn.setOnClickListener(bumpUpButtonListener);
		thumbsDown_btn.setOnClickListener(bumpDownButtonListener);
		
	}


	private void initializeTextListeners()
	{
		billAmount_ET.addTextChangedListener(billAmountListener);
		billAmount_ET.setOnFocusChangeListener(billAmountFocusChangeListener);
		
		totalAmount_ET.addTextChangedListener(totalAmountListener);
		totalAmount_ET.setOnFocusChangeListener(totalAmountFocusChangeListener);
		
		tipPercent_ET.addTextChangedListener(tipPercentListener);
		tipPercent_ET.setOnFocusChangeListener(tipPercentFocusChangeListener);

		tipAmount_ET.addTextChangedListener(tipAmountListener);
		tipAmount_ET.setOnFocusChangeListener(tipAmountFocusChangeListener);

		numberOfGroups_ET.addTextChangedListener(numGroupsListener);
		numberOfGroups_ET.setOnFocusChangeListener(numGroupsFocusChangeListener);

		groupPaysAmount_ET.addTextChangedListener(groupPaysAmountAmountListener);
		groupPaysAmount_ET.setOnFocusChangeListener(groupPaysAmountFocusChangeListener);
		
	}


	private void initializeScreenTextData()
	{
		updateBillAmountOnScreen();			
		updateTipPercentOnScreen();			
		updateTipAmountOnScreen();			
		updateTotalAmountOnScreen();
		updateNumGroupsOnScreen();			
		updateGroupPaysAmountOnScreen();
	}



	private void restoreFieldsHoldingScreenState(Bundle savedInstanceState)
	{
		this.tipCalcState.setBillAmount(savedInstanceState.getDouble(BILL_AMOUNT_KEY));
		this.tipCalcState.setTipPercent(savedInstanceState.getDouble(TIP_PERCENT_KEY));
		this.tipCalcState.setNumOfGroups(savedInstanceState.getInt(NUM_OF_GROUPS_KEY));
		updateJudgementMsg();		
	}

	private OnClickListener bumpUpButtonListener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			tipCalcState.bumpUpTipPercent();			
			updateTipPercentOnScreen();
			updateJudgementMsg();
		}
	};	
	
	
	private OnClickListener bumpDownButtonListener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			tipCalcState.bumpDownTipPercent();
			updateTipPercentOnScreen();
			updateJudgementMsg();
		}
	};	
	

	private void handleEditTextFocusChange(View v)
	{
		if (!platformIndependentIsDestroyed())
		{
		   attachKeyboard((EditText) v);
		}
	}

	
	
	
	private TextWatcher billAmountListener = new TextWatcher()
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setBillAmount(Double.parseDouble(enteredText.toString()));
				} catch (NumberFormatException e)
				{
					tipCalcState.setBillAmount(0.0);
				}

				setUserRequestedDataUpdate(false);

				updateTipAmountOnScreen();			
				updateTotalAmountOnScreen();
				updateGroupPaysAmountOnScreen();

				updateJudgementMsg();

				setUserRequestedDataUpdate(true);
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{

		}

	};

	private OnFocusChangeListener billAmountFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				setUpFirstFieldsFocusState(billAmount_ET);
				
				setUserRequestedDataUpdate(false);
				updateDecimalTextView(billAmount_ET, tipCalcState.getBillAmount());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}

	}; 
	

	private TextWatcher tipPercentListener = new TextWatcher()
	{

		@Override
		public void afterTextChanged(Editable s)
		{			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setTipPercent(Integer.parseInt(enteredText.toString()) * 0.01);
				} catch (NumberFormatException e)
				{
					tipCalcState.setTipPercent(0.0);
				}

				setUserRequestedDataUpdate(false);
				
				updateTipAmountOnScreen();
				updateTotalAmountOnScreen();
				updateGroupPaysAmountOnScreen();

				updateJudgementMsg();
				
				setUserRequestedDataUpdate(true);

			}
		}

	};

	private OnFocusChangeListener tipPercentFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				setUserRequestedDataUpdate(false);
				updateIntTextView(tipPercent_ET, tipCalcState.getTipPercentAsInt());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}
	}; 
	
	
	private TextWatcher tipAmountListener = new TextWatcher()
	{

		@Override
		public void afterTextChanged(Editable s)
		{
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setTipAmount(Double.parseDouble(enteredText.toString()));
				} catch (NumberFormatException e)
				{
					tipCalcState.setTipAmount(0.0);
				}

				setUserRequestedDataUpdate(false);
				updateTipPercentOnScreen();


				updateTotalAmountOnScreen();
				updateGroupPaysAmountOnScreen();
				updateJudgementMsg();
				
				setUserRequestedDataUpdate(true);
			}

		}

	};

	private OnFocusChangeListener tipAmountFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				setUserRequestedDataUpdate(false);
				updateDecimalTextView(tipAmount_ET, tipCalcState.getTipAmount());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}
	}; 
	

	private TextWatcher totalAmountListener = new TextWatcher()
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setTotalAmount(Double.parseDouble(enteredText.toString()));
				} catch (NumberFormatException e)
				{
					tipCalcState.setTotalAmount(0.0);
				}

				setUserRequestedDataUpdate(false);

				updateTipAmountOnScreen();	
				updateTipPercentOnScreen();
				updateGroupPaysAmountOnScreen();

				updateJudgementMsg();

				setUserRequestedDataUpdate(true);
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			
		}

	};

	private OnFocusChangeListener totalAmountFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				setUserRequestedDataUpdate(false);
				updateDecimalTextView(totalAmount_ET, tipCalcState.getTotalAmount());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}
	}; 
	
	
	

	private TextWatcher numGroupsListener = new TextWatcher()
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setNumOfGroups(Integer.parseInt(enteredText.toString()));
				} catch (NumberFormatException e)
				{
					tipCalcState.setNumOfGroups(1);
				}

				updateGroupPaysAmountOnScreen();
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{
		}

	};

	private OnFocusChangeListener numGroupsFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				attachNumberKeyboard();
				setUserRequestedDataUpdate(false);
				updateIntTextView(numberOfGroups_ET, tipCalcState.getNumOfGroups());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}
	}; 
	
	private TextWatcher groupPaysAmountAmountListener = new TextWatcher()
	{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence enteredText, int start,
				int before, int count)
		{
			if (isUserRequestedDataUpdate())
			{
				try
				{
					tipCalcState.setEachGroupPays(Double.parseDouble(enteredText.toString()));
				} catch (NumberFormatException e)
				{
					tipCalcState.setEachGroupPays(0.0);
				}

				setUserRequestedDataUpdate(false);

				updateTipAmountOnScreen();	
				updateTipPercentOnScreen();
				updateTotalAmountOnScreen();			
				updateJudgementMsg();

				setUserRequestedDataUpdate(true);
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			
		}

	};

	private OnFocusChangeListener groupPaysAmountFocusChangeListener = new OnFocusChangeListener()
	{
		
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			if (!hasFocus)
			{
				setUserRequestedDataUpdate(false);
				updateDecimalTextView(groupPaysAmount_ET, tipCalcState.getGroupPaysAmount());
				setUserRequestedDataUpdate(true);
			}
			else
			{
                handleEditTextFocusChange(v);
			}
		}
	};
	
	private void setUserRequestedDataUpdate(boolean isUserRequest)
	{
		this.userRequestedDataUpdate = isUserRequest;
	}
	
	
	
    private void updateJudgementMsg()
	{
		Judgement judgement = tipJudger.getJudgement(tipCalcState.getTipPercentAsInt());

		judgementMessage_TV.setText(judgement.getText());
		judgementMessage_TV.setTextColor(judgement.getTextColor());
		judgementMessage_TV.setBackgroundColor(judgement.getFillColor());
	}


	private void updateDecimalTextView(TextView textView, double value)
    {
		textView.setText(String.format("%.02f", value));    	
    }
    
    private void updateIntTextView(TextView textView, int value)
    {
		textView.setText(Integer.toString(value));    	
    }
    
	private boolean isUserRequestedDataUpdate()
	{
		return this.userRequestedDataUpdate;
	}
	
	private void updateBillAmountOnScreen()
	{
		updateDecimalTextView(billAmount_ET, tipCalcState.getBillAmount());		
	}

	
	private void updateTipPercentOnScreen()
	{
		updateIntTextView(tipPercent_ET, tipCalcState.getTipPercentAsInt());		
	}


	private void updateTipAmountOnScreen()
	{
		updateDecimalTextView(tipAmount_ET, tipCalcState.getTipAmount());		
	}

	private void updateTotalAmountOnScreen()
	{
		updateDecimalTextView(totalAmount_ET, tipCalcState.getTotalAmount());
	}


	private void updateNumGroupsOnScreen()
	{
		updateIntTextView(numberOfGroups_ET, tipCalcState.getNumOfGroups());		
	}


	private void updateGroupPaysAmountOnScreen()
	{
		updateDecimalTextView(groupPaysAmount_ET, tipCalcState.getGroupPaysAmount());
	}



	private void disableDefaultKeyboard(EditText editText)
	{
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.judge_tip_calc_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
		switch (item.getItemId())
		{
		case R.id.action_refresh:
			performRefreshAction();
			return true;

		case R.id.tipping_history:
			performTippingHistoryAction();
			return true;

		case R.id.action_settings:
			performSettingsAction();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void performRefreshAction()
	{
		tipCalcState.initializeState();
		updateAllScreenFields();
		setFirstFocusOnFirstField();
	}


	private void performSettingsAction()
	{
		Intent settingsIntent = new Intent(this, UserSettingsActivity.class);
		startActivityForResult(settingsIntent, RESULT_SETTINGS);
	}

	private void performTippingHistoryAction()
	{
		Intent intent = new Intent(this, DisplayScrollableTextActivity.class);
		startActivity(intent);		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)
		{
		case RESULT_SETTINGS:
			getUserSettings();
			break;

	    case REQUEST_CODE_RECOVER_PLAY_SERVICES:
		    if (resultCode == RESULT_CANCELED) 
		    {
		       Toast.makeText(this, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
		       finish();
		    }
		default:
			Log.e(this.getClass().getSimpleName(), "Unexpected activity result:" + requestCode);
		}		
	}

	private void getUserSettings()
	{
		setCalcToDefaultTip();
        updateAllScreenFields();
	}


	private void setCalcToDefaultTip()
	{
		double defaultTipPercent = getDefaultTip();
		tipCalcState.setDefaultTipPercent(defaultTipPercent);
	}


	private double getDefaultTip()
	{
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //TODO Use constants
		int defaultTipPercentAsInt = Integer.parseInt(sharedPrefs.getString("pref_default_tip", "15"));
		return defaultTipPercentAsInt * .01;
	}   
	
	private void setFirstFocusOnFirstField()
	{
		billAmount_ET.requestFocus();
		setUpFirstFieldsFocusState(billAmount_ET);
	}


	private void updateAllScreenFields()
	{
		setUserRequestedDataUpdate(false);
		updateBillAmountOnScreen();
		updateTipPercentOnScreen();			
		updateTipAmountOnScreen();			
		updateTotalAmountOnScreen();
		updateNumGroupsOnScreen();
		updateGroupPaysAmountOnScreen();
		
		updateJudgementMsg();
		
		setUserRequestedDataUpdate(true);
		
	}
	
	@Override
	protected void onDestroy()
	{
		isDestroyed = true;
		super.onDestroy();
	}
	
    //android's isDestroyed is not available pre API 17
	private boolean platformIndependentIsDestroyed()
	{
		return isDestroyed;
	}
}
