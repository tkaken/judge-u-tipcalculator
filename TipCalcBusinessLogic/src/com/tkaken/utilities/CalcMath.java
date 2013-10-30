package com.tkaken.utilities;

import java.math.BigDecimal;

public final class CalcMath
{
	public static int roundUp(double numberToRound, int round_multiple)
	{
		double rounded;
		
		if ( (numberToRound % round_multiple) == 0 )
		{
			rounded = numberToRound + round_multiple;
		}
		else 
		{
			rounded = Math.ceil(numberToRound/round_multiple) * round_multiple;
		}
		
		return (int) rounded;
	}
	
	public static int roundDown(double numberToRound, int round_multiple)
	{
		double rounded;
		
		if ( (numberToRound % round_multiple) == 0 )
		{
			rounded = numberToRound - round_multiple;
		}
		else
		{
			rounded = Math.floor(numberToRound/round_multiple) * round_multiple;
		}
		
		return (int) rounded;
	}

	public static boolean isDoublePositive(double inValue)
	{
		return BigDecimal.valueOf(inValue).compareTo(BigDecimal.ZERO) > 0;
	}

	public static int compareDouble(double double1, double double2)
	{
		return BigDecimal.valueOf(double1).compareTo(BigDecimal.valueOf(double2));
	}

	public static double getDoubleBelowCeiling(double inValue, double ceiling)
	{
		if (CalcMath.compareDouble(inValue, ceiling) > 0)
        {
        	inValue = ceiling;
        }
		return inValue;
	}


}
