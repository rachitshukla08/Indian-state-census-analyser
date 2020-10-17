package com.capgemini.indianstatecensusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;

public class CensusAnalyserTest {
	private static final String censusDataPath = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusData.csv";
	private static final String censusDataPathIncorrectDelimiter = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusDataIncorrectDelimiter.csv";
	private StateCensusAnalyser stateCensusAnalyser;

	@Before
	public void init() {
		stateCensusAnalyser = new StateCensusAnalyser();
	}

	@Test
	public void givenCensusCSVFile_ReturnsCorrectNoOfEntries() throws CensusAnalyserException {
		int noOfEntries = stateCensusAnalyser.loadCensusData(censusDataPath);
		assertEquals(29, noOfEntries);
	}
	
	@Test
	public void givenIncorrectCSVFilePath_ThrowsCustomException(){
		try {
			stateCensusAnalyser.loadCensusData(censusDataPath+"123");
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_PATH, e.type);
		}
	}
	
	@Test
	public void givenIncorrectDelimiter_ThrowsCustomException(){
		try {
			System.out.println(stateCensusAnalyser.loadCensusData(censusDataPathIncorrectDelimiter));
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
		}
	}
	
}
