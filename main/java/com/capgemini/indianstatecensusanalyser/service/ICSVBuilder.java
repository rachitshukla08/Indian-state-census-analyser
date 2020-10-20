package com.capgemini.indianstatecensusanalyser.service;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public interface ICSVBuilder<E> {
	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CsvException;
	public List<E> getCSVFileList(Reader reader, Class csvClass) throws CsvException;
}
