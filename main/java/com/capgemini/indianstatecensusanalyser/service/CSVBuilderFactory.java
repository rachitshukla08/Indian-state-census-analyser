package com.capgemini.indianstatecensusanalyser.service;

public class CSVBuilderFactory {

	public static ICSVBuilder createCSVBuilder() {
		return new OpenCSVBuilder();
	}
	
}
