package com.tkaken.judgetipcalcTests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tkaken.tipRules.Judgement;
import com.tkaken.tipRules.JudgementFactory;
import com.tkaken.tipRules.TipJudgementRules;


public class TipJudgementRulesTests
{

	private TipJudgementRules tipJudger;

	@Mock private JudgementFactory cheapoFactory;
	@Mock private JudgementFactory saintFactory;
	@Mock private JudgementFactory normalFactory;
	
	private static Judgement cheapJudgement = new Judgement(1, 2, "cheap");
	private static Judgement saintJudgement = new Judgement(3, 4, "saint");
	private static Judgement normalJudgement = new Judgement(5, 6, "normal");	

	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
	    tipJudger = new TipJudgementRules(cheapoFactory, saintFactory, normalFactory);
	    
		when(cheapoFactory.getJudgement()).thenReturn(cheapJudgement);
		when(saintFactory.getJudgement()).thenReturn(saintJudgement);
		when(normalFactory.getJudgement()).thenReturn(normalJudgement);
	    
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testGetCheapoJudgement()
	{
		Judgement actualJudgement = this.tipJudger.getJudgement(10);
		assertThat(actualJudgement, is(equalTo(cheapJudgement)));
	}

	
	@Test
	public void testGetCheapoZeroJudgement()
	{
		Judgement actualJudgement = this.tipJudger.getJudgement(0);
		assertThat(actualJudgement, is(equalTo(cheapJudgement)));
	}

	
	@Test
	public void testNormalLowJudgement()
	{
		Judgement actualJudgement = this.tipJudger.getJudgement(11);
		assertThat(actualJudgement, is(equalTo(normalJudgement)));
	}

	@Test
	public void testNormalHighJudgement()
	{
		Judgement actualJudgement = this.tipJudger.getJudgement(19);
		assertThat(actualJudgement, is(equalTo(normalJudgement)));
	}
	
	@Test
	public void testSaintJudgement()
	{
		Judgement actualJudgement = this.tipJudger.getJudgement(20);
		assertThat(actualJudgement, is(equalTo(saintJudgement)));
	}


}
