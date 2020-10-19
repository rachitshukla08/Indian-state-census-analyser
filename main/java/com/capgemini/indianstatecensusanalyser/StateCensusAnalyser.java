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
		try (Reader reader = Files.newBufferedReader(Paths.get(censusDataPath));) {
			Iterator<IndiaStateCensus> censusIterator = this.getCSVFileIterator(reader, IndiaStateCensus.class);
			int noOfEntries = 0;
			while (censusIterator.hasNext()) {
				noOfEntries++;
				IndiaStateCensus censusData = censusIterator.next();
			}
			BufferedReader br = new BufferedReader(new FileReader(censusDataPath));
			String line = "";
			int ctr = 0;
			while ((line = br.readLine()) != null) {
				if (!line.contains(","))
					throw new CensusAnalyserException("Invalid delimiter",
							CensusAnalyserException.ExceptionType.INVALID_DELIMITER);
				if (ctr == 0) {
					String[] headers = line.split(",");
					if (!(headers[0].equals("State") && headers[1].equals("Population")
							&& headers[2].equals("AreaInSqKm") && headers[3].equals("DensityPerSqKm")))
						throw new CensusAnalyserException("Invalid headers",
								CensusAnalyserException.ExceptionType.INVALID_HEADER);
					ctr++;
				}
			}
			br.close();
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid file location",
					CensusAnalyserException.ExceptionType.INVALID_FILE_PATH);
		}
	}

	/**
	 * @param codeDataPath
	 * @return number of entries
	 * @throws CodeAnalyserException
	 * @throws CensusAnalyserException 
	 */
	public int loadCodeData(String codeDataPath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(codeDataPath));) {
			Iterator<CSVStates> censusIterator = this.getCSVFileIterator(reader, CSVStates.class);
			int noOfEntries = 0;
			while (censusIterator.hasNext()) {
				noOfEntries++;
				CSVStates codeData = censusIterator.next();
			}
			BufferedReader br = new BufferedReader(new FileReader(codeDataPath));
			String line = "";
			int ctr = 0;
			while ((line = br.readLine()) != null) {
				if (!line.contains(","))
					throw new CensusAnalyserException("Invalid delimiter For Code Data",
							CensusAnalyserException.ExceptionType.INVALID_DELIMITER);
				if (ctr == 0) {
					String[] headers = line.split(",");
					if (!(headers[0].equals("SrNo") && headers[1].equals("State Name") && headers[2].equals("TIN")
							&& headers[3].equals("StateCode")))
						throw new CensusAnalyserException("Invalid header(s) For Code Data",
								CensusAnalyserException.ExceptionType.INVALID_HEADER);
					ctr++;
				}
			}
			br.close();
			return noOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("Invalid File Path For Code Data",
					CensusAnalyserException.ExceptionType.INVALID_FILE_PATH);
		}
	}

	/**
	 * @param reader
	 * @param csvClass
	 * @return CSV Census File iterator
	 * @throws CensusAnalyserException
	 */
	private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass)
			throws CensusAnalyserException {
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
