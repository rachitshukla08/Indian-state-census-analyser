package com.capgemini.indianstatecensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IllegalIndiaStateCensus {
	private String state;
	
	private long pop;

	@Override
	public String toString() {
		return "IllegalIndiaStateCensus [stateName=" + state + ", population=" + pop + "]";
	}
	
}
