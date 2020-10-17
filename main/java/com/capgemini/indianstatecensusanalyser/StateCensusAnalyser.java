/**
 * 
 */
package com.capgemini.indianstatecensusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;
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
			Iterator<IndiaStateCensus> censusIterator = csvToBean.iterator();
			int noOfEntries = 0;
			while (censusIterator.hasNext()) {
				noOfEntries++;
				IndiaStateCensus censusData = censusIterator.next();
			}
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid file location");
		}
	}
}
