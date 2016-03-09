package com.digiwes.frameworx.engagedparty.party.interfaces;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;

public interface IndividualFactory{
	
	public Individual fromString(String individualParam);
	
	public String toString(Individual individual);
	
}