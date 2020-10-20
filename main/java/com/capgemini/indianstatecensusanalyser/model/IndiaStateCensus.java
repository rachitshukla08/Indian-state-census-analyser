package com.capgemini.indianstatecensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensus {
	
	@CsvBindByName (column = "State")
	private String stateName;
	
	@CsvBindByName (column = "Population")
	private long population;
	
	@CsvBindByName (column = "AreaInSqKm")
	private int area;
	
	@CsvBindByName (column = "DensityPerSqKm")
	private int density;
	
	@Override
	public String toString() {
		return "IndiaStateCensus [stateName=" + stateName + ", population=" + population + ", area=" + area
				+ ", density=" + density + "]";
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}
}
