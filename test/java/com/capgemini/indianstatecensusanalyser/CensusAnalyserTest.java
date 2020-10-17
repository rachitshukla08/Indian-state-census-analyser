package com.capgemini.indianstatecensusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;

public class CensusAnalyserTest {
	private static final String CENSUS_DATA_PATH = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusData.csv";
	private static final String CENSUS_DATA_PATH_INCORRECT_DELIMITER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusDataIncorrectDelimiter.csv";
	private static final String CENSUS_DATA_PATH_INCORRECT_HEADER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusDataIncorrectHeader.csv";
	private static final String STATE_CODE_DATA_PATH = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCode.csv";
	private StateCensusAnalyser stateCensusAnalyser;

	@Before
	public void init() {
		stateCensusAnalyser = new StateCensusAnalyser();
	}

	@Test
	public void givenCensusCSVFile_ReturnsCorrectNoOfEntries() throws CensusAnalyserException {
		int noOfEntries = stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH);
		assertEquals(29, noOfEntries);
	}
	
	@Test
	public void givenIncorrectCSVFilePath_ThrowsCensusAnalyserExceptionOfTypeInvalidFilePath(){
		try {
			stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH+"123");
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_PATH, e.type);
		}
	}
	
	@Test
	public void givenIncorrectDelimiter_ThrowsCensusAnalyserExceptionOfTypeInalidDelimiter(){
		try {
			System.out.println(stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH_INCORRECT_DELIMITER));
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
		}
	}
	
	@Test
	public void givenIncorrectHeader_ThrowsCensusAnalyserExceptionOfTypeInvalidHeader(){
		try {
			System.out.println(stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH_INCORRECT_HEADER));
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_HEADER, e.type);
		}
	}
	
	@Test
	public void givenCodeCSVFile_ReturnsCorrectNoOfEntries() throws CensusAnalyserException {
		int noOfEntries = stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH);
		assertEquals(37, noOfEntries);
	}
	
	@Test
	public void givenIncorrectStateCodeCSVFilePath_ThrowsCodeAnalyserExceptionOfTypeInvalidFilePath(){
		try {
			stateCensusAnalyser.loadCensusData(STATE_CODE_DATA_PATH+"123");
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_PATH, e.type);
		}
	}
}
