package com.tkaken.tipCalc;

import java.math.BigDecimal;

import com.tkaken.utilities.CalcMath;

public class TipCalcState
{
	private static final double MAX_TIP_PERCENT = 1.00;
	private static final double MAX_BILL_AMOUNT = 9999.99;
	private static final double MAX_TIP_AMOUNT = MAX_TIP_PERCENT * MAX_BILL_AMOUNT;
	private static final double MAX_TOTAL_AMOUNT = MAX_BILL_AMOUNT + MAX_TIP_AMOUNT;
	private static final int MAX_NUMBER_GROUPS = 99;


	private static final int TIP_BUMP_AMOUNT = 5;
	
	// hold values received from data entry fields
	private double billAmount;
	private double tipPercent;
	private double tipAmount;
	private double totalAmount;
	private int numOfGroups;
	private double groupPaysAmount;
	private double defaultTipPercent;

	public TipCalcState()
	{
		defaultTipPercent = .15;
		initializeState();
	}

	public double getBillAmount()
	{
		return this.billAmount;
	}

	public void setBillAmount(double inValue)
	{
	    inValue = CalcMath.getDoubleBelowCeiling(inValue, MAX_BILL_AMOUNT);
		
		if (CalcMath.isDoublePositive(inValue))
		{
			this.billAmount = inValue;			
			this.tipAmount = this.tipPercent * this.billAmount;
		}
		else
		{
			this.billAmount = 0.0;
			this.tipAmount = 0.0;
		}
				
		calculateTotalAmount();
		calculateGroupPaysAmount();		
	}

	public double getTipPercent()
	{
		return this.tipPercent;
	}

	public int getTipPercentAsInt()
	{
		return (int) Math.round(this.tipPercent * 100);
	}

	public void setTipPercent(double inValue)
	{
	    inValue = CalcMath.getDoubleBelowCeiling(inValue, MAX_TIP_PERCENT);
		
		if (CalcMath.isDoublePositive(inValue))
		{
			this.tipPercent = inValue;	
		}
		else
		{
			this.tipPercent = 0.0;
		}
	
		this.tipAmount = this.billAmount * this.tipPercent;
		
	    
		calculateTotalAmount();		
		calculateGroupPaysAmount();	
	}

	public double getTotalAmount()
	{
		return this.totalAmount;
	}

	public void setTotalAmount(double inTotalAmount)
	{
		
		if (!CalcMath.isDoublePositive(billAmount))
		{
		    totalAmount = 0.0;
		}
		else
		{
			totalAmount = inTotalAmount;			
		}
		
		
		if ( totalAmountForcesTipPercentOverMax(totalAmount) )
		{
			totalAmount = billAmount + (billAmount*MAX_TIP_PERCENT);
		}
		
		totalAmount = CalcMath.getDoubleBelowCeiling(totalAmount, MAX_TOTAL_AMOUNT);
	    
		if (CalcMath.compareDouble(totalAmount, billAmount) < 0) 
		{
			totalAmount = billAmount;
		}
	
		calculateTipAmount();
		calculateTipPercent();
		calculateGroupPaysAmount();
	}

	public double getTipAmount()
	{
		return this.tipAmount;
	}

	public void setTipAmount(double inValue)
	{
		tipAmount = inValue;
		
		if ( tipAmountForcesTipPercentOverMax(tipAmount) )
		{
			tipAmount = billAmount*MAX_TIP_PERCENT;
		}
		
		tipAmount = CalcMath.getDoubleBelowCeiling(tipAmount, MAX_TIP_AMOUNT);
	
		
		calculateTipPercent();
		
		calculateTotalAmount();
		calculateGroupPaysAmount();
	}

	public int getNumOfGroups()
	{
		return this.numOfGroups;
	}

	public void setNumOfGroups(int inValue)
	{
		if (inValue > MAX_NUMBER_GROUPS)
		{
			inValue = MAX_NUMBER_GROUPS;
		}
		
		if (inValue > 0)
		{
			this.numOfGroups = inValue;
		}
		else if (0 == inValue)
		{
			this.numOfGroups = 1;
		}
		
		calculateGroupPaysAmount();
		
	}

	public double getGroupPaysAmount()
	{
		return this.groupPaysAmount;
	}

	public void setEachGroupPays(double inGroupAmount)
	{
		groupPaysAmount = inGroupAmount;
		
		if ( totalAmountForcesTipPercentOverMax(groupPaysAmount*numOfGroups) )
		{
			double tempTotalAmount = billAmount + (billAmount*MAX_TIP_PERCENT);
			groupPaysAmount = tempTotalAmount / numOfGroups;
		}
		
		if (CalcMath.compareDouble(groupPaysAmount*numOfGroups, billAmount) < 0)
		{
			groupPaysAmount = billAmount / numOfGroups;
			totalAmount = billAmount;
		}
		else if ( CalcMath.isDoublePositive(billAmount) )
		{
			totalAmount = groupPaysAmount * numOfGroups;
		}
		else 
		{
			groupPaysAmount = 0.0;
		}
		
		calculateTipAmount();
		calculateTipPercent();
		
	}

	public void bumpUpTipPercent()
	{
		int tipAsInt =  CalcMath.roundUp(getTipPercentAsInt(), TIP_BUMP_AMOUNT);
		setTipPercent( tipAsInt * .01);	
	}

	public void bumpDownTipPercent()
	{
		int tipAsInt =  CalcMath.roundDown(getTipPercentAsInt(), TIP_BUMP_AMOUNT);
		setTipPercent( tipAsInt * .01);	
	}

	private void calculateTipPercent()
	{
		if (!CalcMath.isDoublePositive(billAmount) ||
			!CalcMath.isDoublePositive(tipAmount))
		{
			this.tipAmount = 0.0;
			this.tipPercent = 0.0;
		}
		else
		{
		   this.tipPercent = this.tipAmount / this.billAmount;
		}
	}

	private void calculateTipAmount()
	{
		tipAmount = totalAmount - billAmount;
	}

	private void calculateTotalAmount()
	{
		this.totalAmount = this.billAmount + this.tipAmount;
	}

	private void calculateGroupPaysAmount()
	{
		this.groupPaysAmount = this.totalAmount / this.numOfGroups;
	}


	private boolean totalAmountForcesTipPercentOverMax(double proposedTotal)
	{
		if (BigDecimal.valueOf(billAmount).compareTo(BigDecimal.ZERO) == 0) return false;
		
		double newTipPercent = (proposedTotal-billAmount)/billAmount;
		return CalcMath.compareDouble(newTipPercent, MAX_TIP_PERCENT) > 0;
	}

	private boolean tipAmountForcesTipPercentOverMax(double proposedTipAmount)
	{
		if (BigDecimal.valueOf(billAmount).compareTo(BigDecimal.ZERO) == 0) return false;
		
		double newTipPercent = (proposedTipAmount/billAmount);
		return CalcMath.compareDouble(newTipPercent, MAX_TIP_PERCENT) > 0;
	}


	public void initializeState()
	{
		this.setBillAmount(0.0);
		this.setTipPercent(defaultTipPercent);
		this.setNumOfGroups(1);		
	}
	

	public void setDefaultTipPercent(double defaultPercent)
	{
		this.defaultTipPercent = defaultPercent;
		setTipPercent(defaultTipPercent);		
	}


}
