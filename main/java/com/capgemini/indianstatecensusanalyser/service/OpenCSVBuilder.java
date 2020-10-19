package com.capgemini.indianstatecensusanalyser.service;

import java.io.Reader;
import java.util.Iterator;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder {

	/**
	 * @param <E>
	 * @param reader
	 * @param csvClass
	 * @return iterator
	 * @throws CensusAnalyserException
	 */
	public<E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.build();
			return csvToBean.iterator();
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException("Wrong class type",
					CensusAnalyserException.ExceptionType.INVALID_CLASS_TYPE);
		}
	}

	/**
	 * @param <E>
	 * @param iterator
	 * @return count
	 */
	public <E> int getCount(Iterator<E> iterator) {
		int noOfEntries = 0;
		while (iterator.hasNext()) {
			noOfEntries++;
			E censusData = iterator.next();
			System.out.println(censusData);
		}
		return noOfEntries;
	}

}
