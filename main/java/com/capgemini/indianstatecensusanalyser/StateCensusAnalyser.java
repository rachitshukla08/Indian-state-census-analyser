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
import com.capgemini.indianstatecensusanalyser.model.IndiaStateCensus;
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
		try {
			Reader reader = Files.newBufferedReader(Paths.get(censusDataPath));
			CsvToBeanBuilder<IndiaStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<IndiaStateCensus>(reader);
			csvToBeanBuilder.withType(IndiaStateCensus.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<IndiaStateCensus> csvToBean = csvToBeanBuilder.build();
			
			BufferedReader br = new BufferedReader(new FileReader(censusDataPath)); 
			String line = "";
	            while ((line = br.readLine()) != null) {
	            	if(!line.contains(","))
	            		throw new CensusAnalyserException("Invalid delimiter", ExceptionType.INVALID_DELIMITER);
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
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException("Incorrect class type", ExceptionType.INVALID_CLASS_TYPE);
		}

	}
}
