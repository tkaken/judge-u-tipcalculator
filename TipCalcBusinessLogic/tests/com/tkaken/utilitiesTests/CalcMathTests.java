package com.tkaken.utilitiesTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tkaken.utilities.CalcMath;

public class CalcMathTests
{

	private static final double DOUBLE_DELTA = 0.01;
	public static final int round_increment = 5;
	
	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testRoundUpOneBeforeLimit()
	{
		assertEquals(15, CalcMath.roundUp(14.09, round_increment));
	}

	@Test
	public void testRoundUpOneAfterLimit()
	{
		assertEquals(20, CalcMath.roundUp(15.09, round_increment));
	}

	@Test
	public void testRoundUpAtLimit()
	{
		assertEquals(20, CalcMath.roundUp(15.0,round_increment));
	}

	@Test
	public void testRoundUpFromZero()
	{
		assertEquals(5, CalcMath.roundUp(0.0,round_increment));
	}

	@Test
	public void testRoundUpFromNegative()
	{
		assertEquals(0, CalcMath.roundUp(-0.1,round_increment));
	}

	@Test
	public void testRoundDownOneBeforeLimit()
	{
		assertEquals(15, CalcMath.roundDown(15.01, round_increment));
	}

	@Test
	public void testRoundDownOneAfterLimit()
	{
		assertEquals(10, CalcMath.roundDown(14.09, round_increment));
	}

	@Test
	public void testRoundDownAtLimit()
	{
		assertEquals(10, CalcMath.roundDown(15.0,round_increment));
	}

	@Test
	public void testRoundDownFromZero()
	{
		assertEquals(-5, CalcMath.roundDown(0.0,round_increment));
	}

	@Test
	public void testRoundDownFromNegative()
	{
		assertEquals(-5, CalcMath.roundDown(-0.1,round_increment));
	}

	@Test
	public void testAboveCeiling()
	{
		assertEquals(5.0, CalcMath.getDoubleBelowCeiling(5.01, 5.0), DOUBLE_DELTA);
	}

	@Test
	public void testAtCeiling()
	{
		assertEquals(5.0, CalcMath.getDoubleBelowCeiling(5.00, 5.00), DOUBLE_DELTA);
	}

	@Test
	public void testBelowCeiling()
	{
		assertEquals(5.0, CalcMath.getDoubleBelowCeiling(5.00, 5.01), DOUBLE_DELTA);
	}

}
