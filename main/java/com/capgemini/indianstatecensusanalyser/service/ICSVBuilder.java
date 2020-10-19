package com.capgemini.indianstatecensusanalyser.service;

import java.io.Reader;
import java.util.Iterator;

import com.capgemini.indianstatecensusanalyser.customexception.CensusAnalyserException;

public interface ICSVBuilder {
	public<E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusAnalyserException;
}
