package com.capgemini.indianstatecensusanalyser.service;

import java.io.Reader;
import java.util.Iterator;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder<E> implements ICSVBuilder{

	/**
	 * @param <E>
	 * @param reader
	 * @param csvClass
	 * @return iterator
	 * @throws CensusAnalyserException
	 */
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException {
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

}
