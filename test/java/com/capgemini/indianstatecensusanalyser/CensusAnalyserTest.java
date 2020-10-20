package com.capgemini.indianstatecensusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.indianstatecensusanalyser.model.CSVStates;
import com.capgemini.indianstatecensusanalyser.model.IndiaStateCensus;
import com.capgemini.indianstatecensusanalyser.service.CensusAnalyserException;
import com.google.gson.Gson;

public class CensusAnalyserTest {
	private static final String CENSUS_DATA_PATH = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusData.csv";
	private static final String CENSUS_DATA_PATH_INCORRECT_DELIMITER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusDataIncorrectDelimiter.csv";
	private static final String CENSUS_DATA_PATH_INCORRECT_HEADER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCensusDataIncorrectHeader.csv";
	private static final String STATE_CODE_DATA_PATH = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCode.csv";
	private static final String STATE_CODE_DATA_PATH_INCORRECT_DELIMITER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCodeIncorrectDelimiter.csv";
	private static final String STATE_CODE_DATA_PATH_INCORRECT_HEADER = ".\\src\\main\\java\\com\\capgemini\\indianstatecensusanalyser\\resources\\IndiaStateCodeIncorrectHeader.csv";

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
	public void givenIncorrectCSVFilePath_ThrowsCensusAnalyserExceptionOfTypeInvalidFilePath() {
		try {
			stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH + "123");
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_PATH, e.type);
		}
	}

	@Test
	public void givenIncorrectCSVClassType_ThrowsCensusAnalyserExceptionOfTypeInvalidClassType() {
		try {
			stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH);
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_CLASS_TYPE, e.type);
		}
	}

	@Test
	public void givenIncorrectDelimiter_ThrowsCensusAnalyserExceptionOfTypeInvalidDelimiter() {
		try {
			System.out.println(stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH_INCORRECT_DELIMITER));
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
		}
	}

	@Test
	public void givenIncorrectHeader_ThrowsCensusAnalyserExceptionOfTypeInvalidHeader() {
		try {
			System.out.println(stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH_INCORRECT_HEADER));
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_HEADER, e.type);
		}
	}

	@Test
	public void givenCodeCSVFile_ReturnsCorrectNoOfEntries() throws CensusAnalyserException {
		int noOfEntries = stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH);
		assertEquals(37, noOfEntries);
	}

	@Test
	public void givenIncorrectStateCodeCSVFilePath_ThrowsCodeAnalyserExceptionOfTypeInvalidFilePath() {
		try {
			stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH + "123");
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_PATH, e.type);
		}
	}

	@Test
	public void givenIncorrectStateCodeCSVClassType_ThrowsCodeAnalyserExceptionOfTypeInvalidClassType() {
		try {
			stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH);
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_CLASS_TYPE, e.type);
		}
	}

	@Test
	public void givenIncorrectStateCodeCSVDelimiter_ThrowsCodeAnalyserExceptionOfTypeInvalidDelimiter() {
		try {
			stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH_INCORRECT_DELIMITER);
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMITER, e.type);
		}
	}

	@Test
	public void givenIncorrectStateCodeCSVHeader_ThrowsCodeAnalyserExceptionOfTypeInvalidHeader() {
		try {
			System.out.println(stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH_INCORRECT_HEADER));
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.INVALID_HEADER, e.type);
		}
	}

	@Test
	public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CensusAnalyserException {
		String sortedCensusData = "";
		stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH);
		sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
		IndiaStateCensus[] censusData = new Gson().fromJson(sortedCensusData, IndiaStateCensus[].class);
		assertEquals("Andhra Pradesh", censusData[0].getStateName());
	}
	
	@Test
	public void givenIndianCensusData_WhenListIsNull_ShouldThrowExceptionOfTypeNoCensusData() {
		String sortedCensusData = "";
		try {
			sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
			IndiaStateCensus[] censusData = new Gson().fromJson(sortedCensusData, IndiaStateCensus[].class);
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
		}
	}
	
	@Test
	public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAnalyserException {
		String sortedCodeData = "";
		stateCensusAnalyser.loadCodeData(STATE_CODE_DATA_PATH);
		sortedCodeData = stateCensusAnalyser.getCodeWiseSortedCodeData();
		CSVStates[] censusData = new Gson().fromJson(sortedCodeData, CSVStates[].class);
		assertEquals("AD", censusData[0].getStateCode());
	}
	
	@Test
	public void givenIndianStateCodeData_WhenListIsNull_ShouldThrowExceptionOfTypeNoCodeData() {
		String sortedCodeData = "";
		try {
			sortedCodeData = stateCensusAnalyser.getCodeWiseSortedCodeData();
			CSVStates[] censusData = new Gson().fromJson(sortedCodeData, CSVStates[].class);
		} catch (CensusAnalyserException e) {
			assertEquals(CensusAnalyserException.ExceptionType.NO_CODE_DATA,e.type);
		}
	}
	
	@Test
	public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() throws CensusAnalyserException {
		String sortedCensusData = "";
		stateCensusAnalyser.loadCensusData(CENSUS_DATA_PATH);
		sortedCensusData = stateCensusAnalyser.getPopulationWiseSortedCensusData();
		IndiaStateCensus[] censusData = new Gson().fromJson(sortedCensusData, IndiaStateCensus[].class);
		assertEquals(199812341, censusData[0].getPopulation());
	}
}
