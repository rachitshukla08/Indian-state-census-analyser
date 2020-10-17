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
import java.util.Iterator;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;
import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException.ExceptionType;
import com.capgemini.indianstatecensusanalyser.model.CSVStates;
import com.capgemini.indianstatecensusanalyser.model.IndiaStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * @author Rachit
 *
 */
public class StateCensusAnalyser {

	/**
	 * @param censusDataPath
	 * @return number of entries
	 * @throws CensusAnalyserException
	 */
	public int loadCensusData(String censusDataPath) throws CensusAnalyserException {
		try(Reader reader = Files.newBufferedReader(Paths.get(censusDataPath));) {
			CsvToBeanBuilder<IndiaStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<IndiaStateCensus>(reader);
			try {
			csvToBeanBuilder.withType(IndiaStateCensus.class);
			}catch(IllegalStateException e) {
				throw new CensusAnalyserException("Wrong class type", ExceptionType.INVALID_CLASS_TYPE);
			}
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<IndiaStateCensus> csvToBean = csvToBeanBuilder.build();
			
			BufferedReader br = new BufferedReader(new FileReader(censusDataPath)); 
			String line = "";
			int ctr = 0;
	            while ((line = br.readLine()) != null) {
	            	if(!line.contains(","))
	            		throw new CensusAnalyserException("Invalid delimiter", ExceptionType.INVALID_DELIMITER);
	            	if(ctr==0) {
	            		String[] headers = line.split(",");
	            		if(!(headers[0].equals("State")&&headers[1].equals("Population")&&headers[2].equals("AreaInSqKm")&&headers[3].equals("DensityPerSqKm")))
	            			throw new CensusAnalyserException("Invalid headers", ExceptionType.INVALID_HEADER);
	            		ctr++;
	            	}
	            }
			br.close();
			
			Iterator<IndiaStateCensus> censusIterator = csvToBean.iterator();
			int noOfEntries = 0;
			while (censusIterator.hasNext()) {
				noOfEntries++;
				IndiaStateCensus censusData = censusIterator.next();
				System.out.println(censusData);
			}
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid file location", ExceptionType.INVALID_FILE_PATH);
		} 
	}
	
	public int loadCodeData(String codeDataPath) {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(codeDataPath));
			CsvToBeanBuilder<CSVStates> csvToBeanBuilder = new CsvToBeanBuilder<CSVStates>(reader);
			csvToBeanBuilder.withType(CSVStates.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<CSVStates> csvToBean = csvToBeanBuilder.build();
			Iterator<CSVStates> censusIterator = csvToBean.iterator();
			int noOfEntries = 0;
			while (censusIterator.hasNext()) {
				noOfEntries++;
				CSVStates codeData = censusIterator.next();
				System.out.println(codeData);
			}
			return noOfEntries;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
