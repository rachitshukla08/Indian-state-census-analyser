/**
 * 
 */
package com.capgemini.indianstatecensusanalyser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.indianstatecensusanalyser.model.CSVStates;
import com.capgemini.indianstatecensusanalyser.model.IndiaStateCensus;
import com.capgemini.indianstatecensusanalyser.service.CSVBuilderFactory;
import com.capgemini.indianstatecensusanalyser.service.CensusAnalyserException;
import com.capgemini.indianstatecensusanalyser.service.CensusAnalyserException.ExceptionType;
import com.capgemini.indianstatecensusanalyser.service.ICSVBuilder;
import com.google.gson.Gson;
import com.opencsv.exceptions.CsvException;

/**
 * @author Rachit
 *
 */
public class StateCensusAnalyser {

	List<IndiaStateCensus> censusCSVList = null;
	List<CSVStates> codeCSVList = null;
	/**
	 * @param censusDataPath
	 * @return number of entries
	 * @throws CensusAnalyserException
	 */
	public int loadCensusData(String censusDataPath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(censusDataPath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			try {
				censusCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCensus.class);
			} catch (CsvException e) {
				throw new CensusAnalyserException("Invalid Class Type", ExceptionType.INVALID_CLASS_TYPE);
			}
			int noOfEntries = censusCSVList.size();
			BufferedReader br = new BufferedReader(new FileReader(censusDataPath));
			String line = "";
			int ctr = 0;
			while ((line = br.readLine()) != null) {
				if (!line.contains(","))
					throw new CensusAnalyserException("Invalid delimiter", ExceptionType.INVALID_DELIMITER);
				if (ctr == 0) {
					String[] headers = line.split(",");
					if (!(headers[0].equals("State") && headers[1].equals("Population")
							&& headers[2].equals("AreaInSqKm") && headers[3].equals("DensityPerSqKm")))
						throw new CensusAnalyserException("Invalid headers", ExceptionType.INVALID_HEADER);
					ctr++;
				}
			}
			br.close();
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid file location", ExceptionType.INVALID_FILE_PATH);
		}
	}

	/**
	 * @param codeDataPath
	 * @return number of entries
	 * @throws CensusAnalyserException
	 */
	public int loadCodeData(String codeDataPath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(codeDataPath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			try {
				codeCSVList = csvBuilder.getCSVFileList(reader, CSVStates.class);
			} catch (CsvException e) {
				throw new CensusAnalyserException("Invalid Class Type", ExceptionType.INVALID_CLASS_TYPE);
			}
			int noOfEntries = codeCSVList.size();
			BufferedReader br = new BufferedReader(new FileReader(codeDataPath));
			String line = "";
			int ctr = 0;
			while ((line = br.readLine()) != null) {
				if (!line.contains(","))
					throw new CensusAnalyserException("Invalid delimiter For Code Data",
							ExceptionType.INVALID_DELIMITER);
				if (ctr == 0) {
					String[] headers = line.split(",");
					if (!(headers[0].equals("SrNo") && headers[1].equals("State Name") && headers[2].equals("TIN")
							&& headers[3].equals("StateCode")))
						throw new CensusAnalyserException("Invalid header(s) For Code Data",
								ExceptionType.INVALID_HEADER);
					ctr++;
				}
			}
			br.close();
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid File Path For Code Data", ExceptionType.INVALID_FILE_PATH);
		}
	}

	/**
	 * @return sorted Json string
	 * @throws CensusAnalyserException
	 */
	public String getStateWiseSortedCensusData() throws CensusAnalyserException {
		if(censusCSVList==null||censusCSVList.size()==0)
			throw new CensusAnalyserException("No Census Data", ExceptionType.NO_CENSUS_DATA);
		List<IndiaStateCensus> sortedList = censusCSVList.stream()
				.sorted(Comparator.comparing(IndiaStateCensus::getStateName))
				.collect(Collectors.toList());
		String sortedCensusDataJson = new Gson().toJson(sortedList);
		return sortedCensusDataJson;
	}
	
	/**
	 * @return sorted Json string
	 * @throws CensusAnalyserException
	 */
	public String getCodeWiseSortedCodeData() throws CensusAnalyserException {
		if(codeCSVList==null||codeCSVList.size()==0)
			throw new CensusAnalyserException("No Code Data", ExceptionType.NO_CODE_DATA);
		List<CSVStates> sortedList = codeCSVList.stream()
				.sorted(Comparator.comparing(CSVStates::getStateCode))
				.collect(Collectors.toList());
		String sortedCodeDataJson = new Gson().toJson(sortedList);
		return sortedCodeDataJson;
	}
}
