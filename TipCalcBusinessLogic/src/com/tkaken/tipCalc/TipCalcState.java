package com.tkaken.tipCalc;

import java.math.BigDecimal;

import com.tkaken.utilities.CalcMath;

public class TipCalcState
{
	private static final int TIP_BUMP_AMOUNT = 5;
	
	// hold values received from data entry fields
	private double billAmount;
	private double tipPercent;
	private double tipAmount;
	private double totalAmount;
	private int numOfGroups;
	private double groupPaysAmount;

	public TipCalcState()
	{
		this.numOfGroups = 1;
		this.tipPercent = .15;
	}

	public double getBillAmount()
	{
		return this.billAmount;
	}

	public double getTipPercent()
	{
		return this.tipPercent;
	}

	public double getTotalAmount()
	{
		return this.totalAmount;
	}

	public double getTipAmount()
	{
		return this.tipAmount;
	}

	public int getNumOfGroups()
	{
		return this.numOfGroups;
	}

	public double getGroupPaysAmount()
	{
		return this.groupPaysAmount;
	}

	public void setBillAmount(double inValue)
	{
		if (BigDecimal.valueOf(inValue).compareTo(BigDecimal.ZERO) > 0)
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

	public void setTipPercent(double inValue)
	{
		if (BigDecimal.valueOf(inValue).compareTo(BigDecimal.ZERO) > 0)
		{
			this.tipPercent = inValue;	
		}
		else
		{
			this.tipPercent = 0.0;
		}

		this.tipAmount = this.billAmount * this.tipPercent;
		
        
		calculateTotalAmount();
		
		this.groupPaysAmount = this.totalAmount/this.numOfGroups;

		
	}

	public void setTipAmount(double inValue)
	{
		this.tipAmount = inValue;
		
		if (BigDecimal.valueOf(this.billAmount).compareTo(BigDecimal.ZERO) == 0 ||
				BigDecimal.valueOf(inValue).compareTo(BigDecimal.ZERO) <= 0	)
		{
			this.tipAmount = 0.0;
			this.tipPercent = 0.0;
		}
		else
		{
		   this.tipPercent = this.tipAmount / this.billAmount;
		}
		
		calculateTotalAmount();
		this.groupPaysAmount = this.totalAmount;
	}

	private void calculateTotalAmount()
	{
		this.totalAmount = this.billAmount + this.tipAmount;
	}

	public void setNumOfGroups(int inValue)
	{
		if (inValue > 0)
		{
			this.numOfGroups = inValue;
		}
		else
		{
			this.numOfGroups = 1;
		}
		
		calculateGroupPaysAmount();
		
	}

	private void calculateGroupPaysAmount()
	{
		this.groupPaysAmount = this.totalAmount / this.numOfGroups;
	}


	public int getTipPercentAsInt()
	{
		return (int) Math.round(this.tipPercent * 100);
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

}
