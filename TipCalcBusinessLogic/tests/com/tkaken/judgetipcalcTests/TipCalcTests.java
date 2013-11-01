package com.tkaken.judgetipcalcTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tkaken.tipCalc.TipCalcState;

public class TipCalcTests 
{
	private static final int MAX_NUM_GROUPS = 99;
	private static final double MAX_TIP_PERCENT = 1.00;
	private static final double MAX_BILL_AMOUNT = 9999.99;
	private static final double MAX_TIP_AMOUNT = MAX_BILL_AMOUNT*MAX_TIP_PERCENT;
	private static final double MAX_TOTAL_AMOUNT = MAX_BILL_AMOUNT + MAX_TIP_AMOUNT;
	private TipCalcState tipCalcStartsAllClear;
	private TipCalcState tipCalcStartsBill20Groups2;
	private TipCalcState tipCalcStartsGroups2;
	private TipCalcState tipCalcStartsGroups3;
	private static final double DOUBLE_DELTA = 0.01;

    @Before
	public void setUp() throws Exception
	{
		this.tipCalcStartsAllClear = new TipCalcState();
		
		this.tipCalcStartsGroups2 = new TipCalcState();
		this.tipCalcStartsGroups2.setNumOfGroups(2);
		
		this.tipCalcStartsGroups3 = new TipCalcState();
		this.tipCalcStartsGroups3.setNumOfGroups(3);

		this.tipCalcStartsBill20Groups2 = new TipCalcState();
		this.tipCalcStartsBill20Groups2.setBillAmount(20);
		this.tipCalcStartsBill20Groups2.setNumOfGroups(2);

	}

	@After
    public void tearDown() throws Exception
	{
	}

	@Test
	public void checkInitialState()
	{
		//check postconditions
		assertEquals(0.0, this.tipCalcStartsAllClear.getBillAmount(), DOUBLE_DELTA);
		assertEquals(0.15, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(0.0, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void setNonZeroBillAmountWhenAllClear()
	{
		double expectedBillAmount = 10.0;
		double expectedTotal = 11.5;
		this.tipCalcStartsGroups2.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups2.getBillAmount(), DOUBLE_DELTA);
		assertEquals(0.15, this.tipCalcStartsGroups2.getTipPercent(),DOUBLE_DELTA);
		assertEquals(1.5, this.tipCalcStartsGroups2.getTipAmount(),DOUBLE_DELTA);
		assertEquals(expectedTotal, this.tipCalcStartsGroups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(2, this.tipCalcStartsGroups2.getNumOfGroups());
		assertEquals(expectedTotal/2, this.tipCalcStartsGroups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setZeroBillAmountWhenAllClear()
	{
		double expectedBillAmount = 0.0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(0.15, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setNegativeBillAmountWhenAllClear()
	{
		double expectedBillAmount = 0.0;
		this.tipCalcStartsAllClear.setBillAmount(-5.5);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(0.15, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setZeroBillAmountWithPositiveTipPercent()
	{
		//set preconditions
		double expectedBillAmount = 0.0;
		double expectedTipPercent = 0.1;
		this.tipCalcStartsAllClear.setTipPercent(expectedTipPercent);
		
		//act
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	

	@Test
	public void setNonZeroBillWhenNonZeroTipAmountNonZeroBillNonZeroTipPercentAndTwoGroups()
	{
		//set preconditions
		double expectedPercentTip = .10;
		double expectedBillAmount = 20.5;
		double expectedTotalBill = 22.55;
		int numOfGroups = 2;
		this.tipCalcStartsAllClear.setNumOfGroups(numOfGroups);
		this.tipCalcStartsAllClear.setBillAmount(50.0);
		this.tipCalcStartsAllClear.setTipPercent(expectedPercentTip);

		//act
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTip, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(2.05, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(numOfGroups, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill/numOfGroups, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	
	@Test
	public void setNonZeroBillWhenNonZeroTipAmountZeroBillNonZeroTipPercentAndTwoGroups()
	{
		//set preconditions
		double expectedPercentTip = .10;
		double expectedBillAmount = 20.5;
		double expectedTotalBill = 22.55;
		int numOfGroups = 2;
		this.tipCalcStartsAllClear.setNumOfGroups(numOfGroups);
		this.tipCalcStartsAllClear.setBillAmount(0.0);
		this.tipCalcStartsAllClear.setTipPercent(expectedPercentTip);

		//act
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTip, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(2.05, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(numOfGroups, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill/numOfGroups, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}

	@Test
	public void setBillWhenTooHigh()
	{
		//set preconditions
		double expectedBillAmount = MAX_BILL_AMOUNT;

		//act
		this.tipCalcStartsAllClear.setBillAmount(MAX_BILL_AMOUNT + .01);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setMaxBill()
	{
		//set preconditions
		double expectedBillAmount = MAX_BILL_AMOUNT;

		//act
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setTipPercentWhenTooHigh()
	{
		//set preconditions
		double expectedTipPercent = MAX_TIP_PERCENT;

		//act
		this.tipCalcStartsAllClear.setTipPercent(MAX_TIP_PERCENT + .01);
		
		//check postconditions
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
	}

	
	@Test
	public void setMaxTipPercent()
	{
		//set preconditions
		double expectedTipPercent = MAX_TIP_PERCENT;

		//act
		this.tipCalcStartsAllClear.setTipPercent(MAX_TIP_PERCENT);
		
		//check postconditions
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
	}

	
	
	@Test
	public void setNonZeroTipPercentWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedPercentTipAmount = .10;
		double expectedBillAmount = 20.5;
		double expectedTotalBill = 22.55;
		this.tipCalcStartsGroups2.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups2.setTipPercent(expectedPercentTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups2.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTipAmount, this.tipCalcStartsGroups2.getTipPercent(),DOUBLE_DELTA);
		assertEquals(2.05, this.tipCalcStartsGroups2.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsGroups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(2, this.tipCalcStartsGroups2.getNumOfGroups());
		assertEquals(expectedTotalBill/2, this.tipCalcStartsGroups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
		
	@Test
	public void setNonZeroTipPercentWhenZeroBillAmount()
	{
		//set preconditions
		double expectedPercentTip = .10;
		double expectedBillAmount = 0.0;
		double expectedTotalBill = 0.0;

		//act
		this.tipCalcStartsAllClear.setTipPercent(expectedPercentTip);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTip, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}

	@Test
	public void setNonZeroTipPercentWhenNonZeroBillAmountManyGroups()
	{
		//set preconditions
		double expectedPercentTip = .10;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 22.0;
		int expectedNumGroups = 2;

		//act
		this.tipCalcStartsBill20Groups2.setTipPercent(expectedPercentTip);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsBill20Groups2.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTip, this.tipCalcStartsBill20Groups2.getTipPercent(),DOUBLE_DELTA);
		assertEquals(2.0, this.tipCalcStartsBill20Groups2.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsBill20Groups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsBill20Groups2.getNumOfGroups());
		assertEquals(expectedTotalBill/expectedNumGroups, this.tipCalcStartsBill20Groups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	
	@Test
	public void setZeroTipPercentWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedPercentTipAmount = 0.0;
		double expectedBillAmount = 20.5;
		double expectedTotalBill = expectedBillAmount;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipPercent(expectedPercentTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTipAmount, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void setZeroTipPercentWhenZeroTotalBill()
	{
		//set preconditions
		double expectedPercentTipAmount = 0.0;
		double expectedBillAmount = 0.0;
		double expectedTotalBill = 0.0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipPercent(expectedPercentTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTipAmount, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setNegativeTipPercentWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedPercentTipAmount = 0.0;
		double expectedBillAmount = 20.5;
		double expectedTotalBill = 20.5;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipPercent(-0.2);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedPercentTipAmount, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(0.0, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	
	@Test
	public void getTipPercentAsIntRoundUp()
	{
		//set preconditions
		this.tipCalcStartsAllClear.setTipPercent(.105);
		
		//act
		assertEquals(11, this.tipCalcStartsAllClear.getTipPercentAsInt());
	}
	

	@Test
	public void getTipPercentAsIntRoundDown()
	{
		//set preconditions
		this.tipCalcStartsAllClear.setTipPercent(.104);
		
		//act
		assertEquals(10, this.tipCalcStartsAllClear.getTipPercentAsInt());
	}
		
	
    @Test
    public void bumpUpTipPercentMultipleOf5()
    {
    	double expectedTipPercent = .20;
    	
    	//act
    	this.tipCalcStartsAllClear.bumpUpTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
    @Test
    public void bumpUpTipPercent0()
    {
    	double expectedTipPercent = .05;
    	this.tipCalcStartsAllClear.setTipPercent(0.0);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpUpTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
    @Test
    public void bumpUpTipPercentOneOverMultipleOf5()
    {
    	double expectedTipPercent = .15;
    	this.tipCalcStartsAllClear.setTipPercent(.11);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpUpTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
	
    @Test
    public void bumpUpTipPercentOneUnderMultipleOf5()
    {
    	double expectedTipPercent = .15;
    	this.tipCalcStartsAllClear.setTipPercent(.14);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpUpTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }

    @Test
    public void bumpDownTipPercentMultipleOf5()
    {
    	double expectedTipPercent = .10;
    	
    	//act
    	this.tipCalcStartsAllClear.bumpDownTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
    

    @Test
    public void bumpDownTipPercent0()
    {
    	double expectedTipPercent = 0.0;
    	this.tipCalcStartsAllClear.setTipPercent(0.0);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpDownTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }


    @Test
    public void bumpDownTipPercentOneOverMultipleOf5()
    {
    	double expectedTipPercent = 0.05;
    	this.tipCalcStartsAllClear.setTipPercent(0.06);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpDownTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
    @Test
    public void bumpDownTipPercentOneUnderMultipleOf5()
    {
    	double expectedTipPercent = .1;
    	this.tipCalcStartsAllClear.setTipPercent(.14);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpDownTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
    @Test
    public void bumpDownTipPercentAnotherOneOverMultipleOf5()
    {
    	double expectedTipPercent = .2;
    	this.tipCalcStartsAllClear.setTipPercent(.21);
    	
    	//act
    	this.tipCalcStartsAllClear.bumpDownTipPercent();
    	
    	//check postconditions
    	assertEquals(expectedTipPercent, tipCalcStartsAllClear.getTipPercent(), DOUBLE_DELTA); 
    }
	
	@Test
	public void setPositiveTipAmountWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 2.0;
		double expectedTipPercent = 0.10;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 22.0;
		this.tipCalcStartsGroups2.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups2.setTipAmount(expectedTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups2.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups2.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups2.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsGroups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(2, this.tipCalcStartsGroups2.getNumOfGroups());
		assertEquals(expectedTotalBill/2, this.tipCalcStartsGroups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setPositiveTipAmountWhenZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 0.0;
		double expectedTipPercent = 0.00;
		double expectedBillAmount = 0.0;
		double expectedTotalBill = 0.0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipAmount(expectedTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);

	}
	
	@Test
	public void setZeroTipAmountWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 0.0;
		double expectedTipPercent = 0.0;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 20.0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipAmount(expectedTipAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void setNegativeTipAmountWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 0.0;
		double expectedTipPercent = 0.0;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 20.0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipAmount(-0.20);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
	}

	@Test
	public void setTipAmountTooHigh()
	{
		//set preconditions
		double expectedTipAmount = MAX_TIP_AMOUNT;
		tipCalcStartsAllClear.setBillAmount(MAX_BILL_AMOUNT);

		//act
		this.tipCalcStartsAllClear.setTipAmount(MAX_TIP_AMOUNT + .01);
		
		//check postconditions
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setMaxTipAmount()
	{
		double expectedTipAmount = MAX_TIP_AMOUNT;
		tipCalcStartsAllClear.setBillAmount(MAX_BILL_AMOUNT);

		//act
		this.tipCalcStartsAllClear.setTipAmount(MAX_TIP_AMOUNT);
		
		//check postconditions
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);
	}
	
	
	@Test
	public void tipAmountCausesTipPercentGreaterThanMax()
	{
		//set preconditions
		double expectedBillAmount = 4.0;
		double expectedTipAmount = 4.0;
		double expectedTipPercent = MAX_TIP_PERCENT;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTipAmount(5.0);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(),DOUBLE_DELTA);		
	}
	

	@Test
	public void setPositiveNumberInGroupWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 2.0;
		double expectedTipPercent = 0.10;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 22.0;
		int expectedNumGroups = 2;
		double expectedAmountPerGroup = expectedTotalBill / expectedNumGroups;
		this.tipCalcStartsGroups2.setBillAmount(expectedBillAmount);
		this.tipCalcStartsGroups2.setTipAmount(expectedTipAmount);

		//act
		this.tipCalcStartsGroups2.setNumOfGroups(expectedNumGroups);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups2.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups2.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups2.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsGroups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsGroups2.getNumOfGroups());
		assertEquals(expectedAmountPerGroup, this.tipCalcStartsGroups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setZeroNumberInGroupWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 2.0;
		double expectedTipPercent = 0.10;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 22.0;
		int expectedNumGroups = 1;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		this.tipCalcStartsAllClear.setTipAmount(expectedTipAmount);

		//act
		this.tipCalcStartsAllClear.setNumOfGroups(0);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);

	}

	@Test
	public void setNegativeNumberInGroupWhenNonZeroTotalBill()
	{
		//set preconditions
		double expectedTipAmount = 2.0;
		double expectedTipPercent = 0.10;
		double expectedBillAmount = 20.0;
		double expectedTotalBill = 22.0;
		int expectedNumGroups = 1;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		this.tipCalcStartsAllClear.setTipAmount(expectedTipAmount);

		//act
		this.tipCalcStartsAllClear.setNumOfGroups(-2);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalBill, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);

	}
	
	@Test
	public void setNumGroupsTooHigh()
	{
		//set preconditions
		int expectedNumberOfGroups = MAX_NUM_GROUPS;
		
		//act
		this.tipCalcStartsAllClear.setNumOfGroups(MAX_NUM_GROUPS+1);
		
		//check postconditions
		assertEquals(expectedNumberOfGroups, tipCalcStartsAllClear.getNumOfGroups());
		
	}

	@Test
	public void setMaxNumGroups()
	{
		//set preconditions
		int expectedNumberOfGroups = MAX_NUM_GROUPS;
		
		//act
		this.tipCalcStartsAllClear.setNumOfGroups(MAX_NUM_GROUPS);
		
		//check postconditions
		assertEquals(expectedNumberOfGroups, tipCalcStartsAllClear.getNumOfGroups());
		
	}

	@Test
	public void setTotalAmountGreaterThanBillAmount()
	{
		//set preconditions
		double expectedTotalAmount = 15.00;
		double expectedBillAmount = 10.50;
		double expectedTipAmount = expectedTotalAmount - expectedBillAmount;
		int expectedTipPercent = 43;
		this.tipCalcStartsGroups2.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups2.setTotalAmount(expectedTotalAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups2.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups2.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups2.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsGroups2.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(2, this.tipCalcStartsGroups2.getNumOfGroups());
		assertEquals(expectedTotalAmount/2, this.tipCalcStartsGroups2.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void setTotalAmountWithZeroBillAmount()
	{
		//set preconditions
		double expectedTotalAmount = 0.0;
		double expectedBillAmount = 0.0;
		double expectedTipAmount = 0.0;
		int expectedTipPercent = 0;

		//act
		this.tipCalcStartsAllClear.setTotalAmount(expectedTotalAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
		
	}

 
	@Test
	public void setTotalAmountLessThanBillAmount()
	{
		//set preconditions
		double expectedTotalAmount = 15.50;
		double expectedBillAmount = 15.50;
		double expectedTipAmount = 0;
		int expectedTipPercent = 0;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTotalAmount(14.49);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsAllClear.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(1, this.tipCalcStartsAllClear.getNumOfGroups());
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);
		
	}


	@Test
	public void setTotalAmountWhenTooHigh()
	{
		//set preconditions
		double expectedTotalAmount = MAX_TOTAL_AMOUNT ;

		//act
		this.tipCalcStartsAllClear.setTotalAmount(MAX_TOTAL_AMOUNT + .01);
		
		//check postconditions
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
	}
	
	
	@Test
	public void setMaxTotalAmount()
	{
		//set preconditions
		double expectedTotalAmount = MAX_TOTAL_AMOUNT ;

		//act
		this.tipCalcStartsAllClear.setTotalAmount(MAX_TOTAL_AMOUNT);
		
		//check postconditions
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void totalAmountCausesTipPercentGreaterThanMax()
	{
		//set preconditions
		double expectedBillAmount = 2.0;
		double expectedTotalAmount = 4.0;
		double expectedTipPercent = MAX_TIP_PERCENT;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTotalAmount(9000.0);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);		
	}
	
	@Test
	public void totalAmountCausesTipPercentEqualToMax()
	{
		//set preconditions
		double expectedBillAmount = 2.0;
		double expectedTotalAmount = expectedBillAmount + (expectedBillAmount*MAX_TIP_PERCENT);
		double expectedTipPercent = MAX_TIP_PERCENT;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsAllClear.setTotalAmount(expectedBillAmount + (expectedBillAmount*MAX_TIP_PERCENT));
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsAllClear.getTotalAmount(),DOUBLE_DELTA);		
	}
	
	@Test
	public void setGroupPayMoreThanBillAmountAmount()
	{
		//set preconditions
		int expectedNumGroups = 3;
		double expectedGroupAmount = 10.25;
		double expectedTotalAmount = expectedGroupAmount * expectedNumGroups;
		double expectedBillAmount = 20.10;
		double expectedTipAmount = expectedTotalAmount - expectedBillAmount;
		int expectedTipPercent = 53;
		this.tipCalcStartsGroups3.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups3.setEachGroupPays(expectedGroupAmount);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups3.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups3.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups3.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsGroups3.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsGroups3.getNumOfGroups());
		assertEquals(expectedGroupAmount, this.tipCalcStartsGroups3.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	
	@Test
	public void setGroupPaysLessThanBillAmount()
	{
		//set preconditions
		double expectedBillAmount = 60.10;
		int expectedNumGroups = 3;
		double expectedGroupAmount = expectedBillAmount / expectedNumGroups;
		double expectedTotalAmount = expectedBillAmount;
		double expectedTipAmount = 0.0;
		int expectedTipPercent = 0;
		this.tipCalcStartsGroups3.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups3.setEachGroupPays(5.0);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups3.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups3.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups3.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsGroups3.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsGroups3.getNumOfGroups());
		assertEquals(expectedGroupAmount, this.tipCalcStartsGroups3.getGroupPaysAmount(),DOUBLE_DELTA);
	}
	

	@Test
	public void setGroupPaysWithZeroTotalAmountAmount()
	{
		//set preconditions
		int expectedNumGroups = 3;
		double expectedGroupAmount = 0.0;
		double expectedTotalAmount = 0.0;
		double expectedBillAmount = 0.0;
		double expectedTipAmount = 0.0;
		int expectedTipPercent = 0;
		this.tipCalcStartsGroups3.setBillAmount(expectedBillAmount);

		//act
		this.tipCalcStartsGroups3.setEachGroupPays(40);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsGroups3.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsGroups3.getTipPercentAsInt());
		assertEquals(expectedTipAmount, this.tipCalcStartsGroups3.getTipAmount(), DOUBLE_DELTA);
		assertEquals(expectedTotalAmount, this.tipCalcStartsGroups3.getTotalAmount(),DOUBLE_DELTA);
		assertEquals(expectedNumGroups, this.tipCalcStartsGroups3.getNumOfGroups());
		assertEquals(expectedGroupAmount, this.tipCalcStartsGroups3.getGroupPaysAmount(),DOUBLE_DELTA);
	}

	@Test
	public void groupPaysAmountCausesTipPercentGreaterThanMax()
	{
		//set preconditions
		double expectedBillAmount = 3.0;
		double expectedTipPercent = MAX_TIP_PERCENT;
		double expectedEachGroupPays = .2;
		this.tipCalcStartsAllClear.setBillAmount(expectedBillAmount);
		tipCalcStartsAllClear.setNumOfGroups(30);

		//act
		this.tipCalcStartsAllClear.setEachGroupPays(9000.0);
		
		//check postconditions
		assertEquals(expectedBillAmount, this.tipCalcStartsAllClear.getBillAmount(),DOUBLE_DELTA);
		assertEquals(expectedTipPercent, this.tipCalcStartsAllClear.getTipPercent(),DOUBLE_DELTA);
		assertEquals(expectedEachGroupPays, this.tipCalcStartsAllClear.getGroupPaysAmount(),DOUBLE_DELTA);		
	}
	

}
