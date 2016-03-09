package com.digiwes.frameworx.engagedparty.party.bean;

import com.digiwes.frameworx.common.basetype.TimePeriod;
 
 import java.io.Serializable;

public class DefaultIndividualName extends IndividualName implements Serializable{

	private Individual _individual;

	public Individual get_individual() {
		return this._individual;
	}

	public void set_individual(Individual _individual) {
		this._individual = _individual;
	}


}