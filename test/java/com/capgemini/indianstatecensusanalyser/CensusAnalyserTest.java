package com.capgemini.indianstatecensusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CensusAnalyserTest {
	private static final String censusDataPath = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusData.csv";
	private StateCensusAnalyser stateCensusAnalyser;

	@Before
	public void init() {
		stateCensusAnalyser = new StateCensusAnalyser();
	}

	@Test
	public void givenCensusCSVFile_ReturnsCorrectNoOfEntries() {
		int noOfEntries = stateCensusAnalyser.loadCensusData(censusDataPath);
		assertEquals(29, noOfEntries);
	}
}
