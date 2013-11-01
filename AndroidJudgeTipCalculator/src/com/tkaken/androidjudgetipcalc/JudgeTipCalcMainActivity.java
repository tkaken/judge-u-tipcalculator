package com.tkaken.androidjudgetipcalc;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.tkaken.tipCalc.TipCalcState;
import com.tkaken.tipRules.Judgement;
import com.tkaken.tipRules.JudgementValues;
import com.tkaken.tipRules.TipJudgementRules;
import com.tkaken.tipRules.TipJudgementRulesEngineFactory;

/**
 * @author Ken
 *
 */
public class JudgeTipCalcMainActivity extends Activity
{
	private boolean userRequestedDataUpdate;

	// look up keys for activity's persistent data
	private static String BILL_AMOUNT_KEY = "BILL_AMOUNT";
	private static String TIP_PERCENT_KEY = "TIP_PERCENT";
	private static String TIP_AMOUNT_KEY = "TIP_AMOUNT";
	private static String TOTAL_AMOUNT_KEY = "TOTAL_AMOUNT";
	private static String NUM_OF_GROUPS_KEY = "NUMBER_OF_GROUPS";
	private static String GROUP_PAYS_KEY = "GROUP_PAYS";
	private static String JUDGEMENT_MSG_KEY = "JUDGEMENT_MSG";

	

	// hold text data for data entry fields
	private EditText billAmount_ET;
	private EditText tipPercent_ET;
	private EditText tipAmount_ET;
	private EditText totalAmount_ET;
	private EditText numberOfGroups_ET;
	private EditText groupPaysAmount_ET;
	private TextView judgementMessage_TV;
	
	//table rows
	private TableRow judgementMessage_TR;
	
	
	//Buttons
	private Button thumbsUp_btn;
	private Button thumbsDown_btn;

	private TipCalcState tipCalcState;
	private TipJudgementRules tipJudger;

	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putDouble(BILL_AMOUNT_KEY, this.tipCalcState.getBillAmount() );
		outState.putDouble(TIP_PERCENT_KEY, this.tipCalcState.getTipPercent() );
		outState.putDouble(TIP_AMOUNT_KEY, this.tipCalcState.getTipAmount() );
		outState.putDouble(TOTAL_AMOUNT_KEY, this.tipCalcState.getTotalAmount() );
		outState.putDouble(NUM_OF_GROUPS_KEY, this.tipCalcState.getNumOfGroups() );
		outState.putDouble(GROUP_PAYS_KEY, this.tipCalcState.getGroupPaysAmount() );
		//TODO put call to rules engine here
		//outState.putString(JUDGEMENT_MSG_KEY, judgementMessage_TV.getText().toString() );
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_judge_tip_calc_main);

		this.tipJudger = getTipJudgementRules();
		
		this.tipCalcState = new TipCalcState();
		
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
		
	}


	private void initScreenButtons()
	{
		thumbsUp_btn = (Button) findViewById(R.id.thumbs_up_btn);
		thumbsDown_btn = (Button) findViewById(R.id.thumbs_down_btn);
	}


	private void initScreenFields()
	{
		billAmount_ET = (EditText) findViewById(R.id.billAmountEditText);		
		tipPercent_ET = (EditText) findViewById(R.id.tipPercenttEditText);		
		tipAmount_ET = (EditText) findViewById(R.id.tipAmountEditText);
		totalAmount_ET = (EditText) findViewById(R.id.totalAmountEditText);		
		numberOfGroups_ET = (EditText) findViewById(R.id.numPeopleEditText);		
		groupPaysAmount_ET = (EditText) findViewById(R.id.perPersonAmountEditText);
		judgementMessage_TV = (TextView) findViewById(R.id.judgementTextView);
		judgementMessage_TR = (TableRow) findViewById(R.id.messageRow);
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
		
		//TODO use rules engine here
		//this.tipCalcState.setJudgementMessage(savedInstanceState.getString(JUDGEMENT_MSG_KEY));
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
		}

	}; 
	
	
	/**
	 * The first EditText field that gets focus needs to has its onFocus function call this.
	 * Necessary since requestFocus does not use select on focus at startup.
	 */
	private void setUpFirstFieldsFocusState(EditText firstFocusField)
	{
		firstFocusField.selectAll();
	}
	
	
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
				setUserRequestedDataUpdate(false);
				updateIntTextView(numberOfGroups_ET, tipCalcState.getNumOfGroups());
				setUserRequestedDataUpdate(true);
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
		judgementMessage_TR.setBackgroundColor(judgement.getFillColor());
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


	/*		
	protected void processBillAmountUpdate()
	{
		if (0.0 == this.billAmount)
		{
			
		}
		
	}


	private void updateTipPercent()
	{
		if (0.0 == billAmount)
		{
		  this.tipPercent = Double.NaN;
		}
		else
		{
			this.tipPercent = this.totalAmount / this.billAmount;
		}
		
		tipPercent_ET.setText(String.format("%.02f", tipPercent));
		
	}
		
	private void updateTotalAmount()
	{
		totalAmount = billAmount + tipAmount;
		totalAmount_ET.setText(String.format("%.02f", totalAmount));
	}

	private void updateJudgementMsg()
	{
		judgementMessage = "Thou art a cheap douchebag";
		TextFormat textFormat = this.tipJudger.getFormatColors(tipAmount);
		this.judgementMessage_TV.setBackgroundColor(textFormat.getFillColor());
		this.judgementMessage_TV.setTextColor(textFormat.getTextColor());
	}

*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.judge_tip_calc_main, menu);
		return true;
	}

}
