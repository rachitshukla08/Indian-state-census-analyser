package com.capgemini.indianstatecensusanalyser.service;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class OpenCSVBuilder<E> implements ICSVBuilder{

	
	/**
	 *@param reader
	 *@param csvClass
	 *@return Iterator
	 */
	@Override
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CsvException {
			return this.getCSVBean(reader, csvClass).iterator();
	}

	/**
	 *@param reader
	 *@param csvClass
	 *@return list
	 */
	@Override
	public List<E> getCSVFileList(Reader reader, Class csvClass) throws CsvException {
			return this.getCSVBean(reader, csvClass).parse();
	}	
	
	/**
	 * @param reader
	 * @param csvClass
	 * @return CsvToBean 
	 * @throws CsvException 
	 */
	private CsvToBean<E> getCSVBean(Reader reader,Class csvClass) throws CsvException{
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			return csvToBeanBuilder.build();
		} catch (IllegalStateException e) {
			throw new CsvException();
		}
	}

}
