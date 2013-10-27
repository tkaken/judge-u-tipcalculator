package com.tkaken.utilities;

public final class CalcMath
{
	static public int roundUp(double numberToRound, int round_multiple)
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
	
	static public int roundDown(double numberToRound, int round_multiple)
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

	//TODO implement floating compare
/*	private boolean doubleEquals(double other) {
	    return (Math.abs(this.mAmount - convertedAmount(other)) < EPSILON);
	}*/
}
